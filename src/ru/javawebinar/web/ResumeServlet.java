package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.SqlStorage;
import ru.javawebinar.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Config CONFIG = Config.getInstance();
    private SqlStorage storage = new SqlStorage(
            CONFIG.getDbUrl(),
            CONFIG.getDbUser(),
            CONFIG.getDbPassword()
    );

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        String fullName = req.getParameter("fullName");
        Resume resume;

        if (uuid.equals("")) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.setContact(type, value);
            } else {
                resume.getContactMap().remove(type);
            }
        }

        for (SectionType secType : SectionType.values()) {
            String secName = req.getParameter(secType.name());
            if (secName != null && secName.trim().length() != 0) {
                switch (secType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(secType, new TextSection(secName));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> descList = new ArrayList<>(Arrays.asList(secName.split("\n")));
                        resume.setSection(secType, new ListSection(descList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] orgNames = req.getParameterValues(secType.name());
                        String[] url = req.getParameterValues(secType.name() + "urlAdr");
                        List<Organization> orgs = new ArrayList<>();
                        for (int i = 0; i < orgNames.length; i++) {
                            Link link = new Link(orgNames[i], url[i]);
                            List<Organization.Position> positionList = new ArrayList<>();

                            String count = secType.name() + i;
                            String[] startPeriods = req.getParameterValues(count + "startPeriod");
                            String[] endPeriods = req.getParameterValues(count + "endPeriod");
                            String[] positions = req.getParameterValues(count + "position");
                            String[] descs = req.getParameterValues(count + "desc");

                            if(startPeriods == null){
                                positionList.add(Organization.Position.EMPTY);
                                continue;
                            }

                            for (int j = 0; j < positions.length; j++) {
                                String checkBoxNow = req.getParameter(count + "checkNow");
                                positionList.add(
                                        new Organization.Position(
                                                LocalDate.parse(startPeriods[j]),
                                                checkBoxNow == null ? LocalDate.parse(endPeriods[j]) : DateUtil.NOW,
                                                positions[j],
                                                descs[j]
                                        ));
                            }

                            orgs.add(new Organization(link, positionList));
                        }
                        resume.setSection(secType, new OrganizationsSection(orgs));
                        break;
                }
            } else {
                resume.getSectionMap().remove(secType);
            }
        }

        if (uuid.equals("")) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }

        resp.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/jList.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "add":
                resume = new Resume();
                newSections(resume);
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                newSections(resume);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private Resume newSections(Resume resume) {
        for (SectionType secType : SectionType.values()) {
            if (resume.getSectionMap().get(secType) != null)
                continue;
            switch (secType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.setSection(secType, TextSection.EMPTY);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.setSection(secType, ListSection.EMPTY);
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    OrganizationsSection section = (OrganizationsSection) resume.getSection(secType);
                    List<Organization> emptyFirstOrganizations = new ArrayList<>();
                    emptyFirstOrganizations.add(Organization.EMPTY);
                    if (section != null) {
                        for (Organization org : section.getOrganizations()) {
                            List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(Organization.Position.EMPTY);
                            emptyFirstPositions.addAll(org.getPositions());
                            emptyFirstOrganizations.add(new Organization(org.getLink(), emptyFirstPositions));
                        }
                    }
                    resume.setSection(secType, new OrganizationsSection(emptyFirstOrganizations));
                    break;
            }
        }
        return resume;
    }

}
