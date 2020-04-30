<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ru.javawebinar.model.ListSection" %>
<%@ page import="ru.javawebinar.model.OrganizationsSection" %>
<%@ page import="ru.javawebinar.model.TextSection" %>
<%@ page import="ru.javawebinar.util.HtmlUtil" %>
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
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sectionMap}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.model.SectionType, ru.javawebinar.model.Section>"/>
        <c:set var="sectionType" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <c:choose>
            <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
                <tr>
                    <td colspan="2">
                        <h2>${sectionType.title}</h2>
                        <c:out value="<%=((TextSection) sectionEntry.getValue()).getText()%>"/>
                    </td>
                </tr>
            </c:when>
            <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                <tr>
                    <td colspan="2">
                        <h2>${sectionType.title}</h2>
                        <ul>
                            <c:forEach items="<%=((ListSection) sectionEntry.getValue()).getDescriptionList()%>"
                                       var="s">
                                <li><c:out value="${s}"/></li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:when>
            <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                <h2>${sectionType.title}</h2>
                <c:forEach items="<%=((OrganizationsSection) sectionEntry.getValue()).getOrganizations()%>" var="org">
                    <td colspan="2">
                        <h3><a href=${org.link.urlAdr}>${org.link.title}</a></h3>
                        <table>
                            <c:forEach items="${org.positions}" var="pos">
                                <jsp:useBean id="pos" type="ru.javawebinar.model.Organization.Position"/>
                                <tr>
                                    <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(pos)%>
                                    </td>
                                    <td><b>${pos.position}</b><br>${pos.description}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </c:forEach>
            </c:when>

        </c:choose>
    </table>
    <br/>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
