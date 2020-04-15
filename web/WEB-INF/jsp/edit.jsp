<%@ page import="ru.javawebinar.model.ContactType" %>
<%@ page import="ru.javawebinar.model.ListSection" %>
<%@ page import="ru.javawebinar.model.OrganizationsSection" %>
<%@ page import="ru.javawebinar.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form method="POST" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>
        <c:forEach var="secType" items="<%=SectionType.values()%>">
        <dl>
            <dt><b>${secType.title}</b></dt>
                <c:set var="section" value="${resume.getSection(secType)}"/>
                <jsp:useBean id="section" type="ru.javawebinar.model.Section"/>
            <c:choose>
            <c:when test="${secType=='PERSONAL' || secType=='OBJECTIVE'}">
            <dd><input type="text" name="${secType.name()}" size=70 value="<%=section%>" required /></dd>
            </c:when>
            <c:when test="${secType=='ACHIEVEMENT' || secType=='QUALIFICATIONS'}">
            <dd><textarea name="${secType.name()}" rows="4" cols="70" style="resize:none;"
                          required ><%=String.join("\n",((ListSection) section).getDescriptionList())%></textarea></dd>
            </c:when>
            <c:when test="${secType=='EXPERIENCE' || secType=='EDUCATION'}">
                <c:forEach items="<%=((OrganizationsSection) section).getOrganizations()%>" var="org" varStatus="orgStat">
                    <br>
                    <dt>Название организации</dt>
                    <dd><input type="text" name="${secType.name()}" size=20 value="${org.link.title}" required/></dd>
                    <br>
                    <dt>Ссылка</dt>
                    <dd><input type="text" name="${secType.name()}${orgStat.index}urlAdr" size=20 value="${org.link.urlAdr}" required/></dd>
                    <br>
                        <c:forEach items="${org.positions}" var="pos" varStatus="posStat">
                            <dt>Начало</dt>
                            <dd><input type="date" name="${secType.name()}${orgStat.index}startPeriod" value="${pos.startPeriod}" required></dd>
                            <br>
                            <dt>Окончание</dt>
                            <dd><input type="date" name="${secType.name()}${orgStat.index}endPeriod" value="${pos.endPeriod}" required></dd>
                            <br>
                            <dt>Позиция</dt>
                            <dd><input type="text" name="${secType.name()}${orgStat.index}position" size=40 value="${pos.position}" required/></dd>
                            <br>
                            <dt>Описание</dt>
                            <dd><textarea name="${secType.name()}${orgStat.index}desc" rows="4" cols="70" style="resize:none;" style="text-align:left"
                                          required>${pos.description}</textarea></dd>
                            <br>
                        </c:forEach>
                </c:forEach>
            </c:when>
            </c:choose>
            <dl>
                </c:forEach>
                <hr>
                <button type="submit">Сохранить</button>
                <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
