package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.SqlStorage;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        ServletContext sc = session.getServletContext();
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
                getTableRows() +
                "</table>" +
                "</body>" +
                "</html>");
    }

    private String getTableRows() {
        Config CONFIG = Config.getInstance();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SqlStorage storage = new SqlStorage(
                CONFIG.getDbUrl(),
                CONFIG.getDbUser(),
                CONFIG.getDbPassword()
        );

        StringBuilder writer = new StringBuilder();
        for (Resume r : storage.getAllSorted()) {
            writer.append("<tr>");
            writer.append("<td>").append(r.getUuid()).append("</td>");
            writer.append("<td>").append(r.getFullName()).append("</td>");
            writer.append("</tr>");
        }
        return writer.toString();
    }
}
