<%@ page import="ru.javawebinar.model.ContactType" %>
<%@ page import="ru.javawebinar.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <c:choose>
                <c:when test="${secType=='PERSONAL' || secType=='OBJECTIVE'}">
                    <dl>
                        <dt>${secType.title}</dt>
                        <dd><input type="text" name="${secType.name()}" size=30 value="1"></dd>
                    </dl>
                </c:when>
                <c:when test="${secType=='ACHIEVEMENT' || secType=='QUALIFICATIONS'}">
                    <dl>
                        <dt>${secType.title}</dt>
                    </dl>
                </c:when>
                <c:when test="${secType=='EXPERIENCE' || secType=='EDUCATION'}">
                    <dl>
                        <dt>${secType.title}</dt>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
