package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.*;
import ru.javawebinar.storage.SqlStorage;

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
                resume.putContact(type, value);
            } else {
                resume.getContactMap().remove(type);
            }
        }

        String[] testpar = req.getParameterValues("EXPERIENCE" + "title");
        String[] testpar2 = req.getParameterValues("EDUCATION" + "title");

        for (SectionType secType : SectionType.values()) {
            String value = req.getParameter(secType.name());
            if (value != null && value.trim().length() != 0) {
                switch (secType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.putSectionMap(secType, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> descList = new ArrayList<>(Arrays.asList(value.split("\n")));
                        resume.putSectionMap(secType, new ListSection(descList));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> orgs = new ArrayList<>();
                        for (int i = 0; i < req.getIntHeader("counterOrgs"); i++) {
                            Link link = new Link(req.getParameter(value + "title"), req.getParameter(value + "urlAdr"));
                            List<Organization.Position> positionList = new ArrayList<>();
                            for (int j = 0; j < req.getIntHeader("counterPos"); j++) {
                                positionList.add(
                                        new Organization.Position(
                                                LocalDate.parse(req.getParameter(value + "startPeriod")),
                                                LocalDate.parse(req.getParameter(value + "endPeriod")),
                                                req.getParameter(value + "position"),
                                                req.getParameter(value + "desc")
                                        ));
                            }
                            orgs.add(new Organization(link, positionList));
                        }
                        resume.putSectionMap(secType, new OrganizationsSection(orgs));
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
                for (SectionType secType : SectionType.values()) {
                    switch (secType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            resume.putSectionMap(secType, new TextSection(""));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            resume.putSectionMap(secType, new ListSection(new ArrayList<>()));
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            resume.putSectionMap(secType, new OrganizationsSection(new ArrayList<>()));
                            break;
                    }
                }
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

}
