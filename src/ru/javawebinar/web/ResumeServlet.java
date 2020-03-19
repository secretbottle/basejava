package ru.javawebinar.web;

import ru.javawebinar.model.Resume;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //String name = request.getParameter("name");
        //response.getWriter().write(name == null ? "Hello Servlet!" : "Hello " + name + "!");

        response.getWriter().write("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Resumes table</title>" +
                "</head>" +
                "<body>" +
                "<table border=\"1\">" +
                getRows() +
                "</table>" +
                "</body>" +
                "</html>");
    }

    private String getRows() {
        List<Resume> resumes = new ArrayList<>();
        resumes.add(new Resume("uuid_1", "fullname 1"));
        resumes.add(new Resume("uuid_2", "fullname 2"));
        resumes.add(new Resume("uuid_3", "fullname 3"));
        resumes.add(new Resume("uuid_4", "fullname 4"));
        StringBuilder writer = new StringBuilder();
        for (Resume r : resumes) {
            writer.append("<tr>");
            writer.append("<td>").append(r.getUuid()).append("</td>");
            writer.append("<td>").append(r.getFullName()).append("</td>");
            writer.append("</tr>");
        }
        return writer.toString();
    }

}
