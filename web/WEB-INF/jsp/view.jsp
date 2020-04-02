<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ru.javawebinar.model.ListSection" %>
<%@ page import="ru.javawebinar.model.OrganizationsSection" %>
<%@ page import="ru.javawebinar.model.TextSection" %>
<%@ page import="java.util.Date" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contactMap}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sectionMap}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.SectionType, ru.javawebinar.model.Section>"/>
            <c:set var="sectionType" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
        <c:choose>
        <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
    <h2>${sectionType.title}</h2>
    <%
        TextSection textSections = (TextSection) sectionEntry.getValue();
        request.setAttribute("textSections", textSections);
    %>
    <c:out value="${textSections.text}"/>
    </c:when>
    <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
        <h2>${sectionType.title}</h2>
        <ul>
            <%
                ListSection listSection = (ListSection) sectionEntry.getValue();
                request.setAttribute("listSection", listSection);
            %>
            <c:forEach items="${listSection.descriptionList}" var="s">
                <li><c:out value="${s}"/></li>
            </c:forEach>
        </ul>
    </c:when>
    <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
        <h2>${sectionType.title}</h2>
        <%
            OrganizationsSection orgSection = (OrganizationsSection) sectionEntry.getValue();
            Date dateFormat = new Date();
            request.setAttribute("orgSection", orgSection);
        %>
        <c:forEach items="${orgSection.organizations}" var="org">
            <td colspan="2">
                <h3><a href=${org.link.urlAdr}>${org.link.title}</a></h3>
                <table>
                    <c:forEach items="${org.positions}" var="pos">
                        <tr>
                            <td width="15%" style="vertical-align: top">
                                <fmt:parseDate value="${pos.startPeriod}" pattern="dd/MM/yyyy" var="startDate" type="date"/>
                                <fmt:formatDate value="${startDate}" pattern="dd/MM/yyyy"/>
                                <%--- <fmt:formatDate value="${pos.endPeriod}" type="date" pattern="dd/MM/yyyy"/>--%>

                               <%-- <c:out value="${pos.startPeriod} - ${pos.endPeriod}"/>--%>
                            </td>
                            <td><b>${pos.position}</b>.<br>${pos.description}</td>
                        </tr>
                    </c:forEach>
                </table>

            </td>
        </c:forEach>
    </c:when>

    </c:choose>
    <br/>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
