<%@ page import="ru.javawebinar.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
    <script src="js/editSection.js"></script>
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
        <c:set var="section" value="${resume.getSection(secType)}"/>
        <jsp:useBean id="section" type="ru.javawebinar.model.Section"/>
        <c:choose>
            <c:when test="${secType=='PERSONAL' || secType=='OBJECTIVE'}">
                <div id="${secType.name()}div">
                    <dl>
                        <dt><b>${secType.title}</b></dt>

                        <c:if test='<%=((TextSection) section).getText().equals("")%>'>
                            <button type="button" id="${secType.name()}addButton"
                                    onclick="addSection('${secType.name()}')">
                                Добавить
                            </button>
                        </c:if>

                        <c:if test='<%=!((TextSection) section).getText().equals("")%>'>
                            <dd><input type="text" id="${secType.name()}" name="${secType.name()}" size=70
                                       value="<%=section%>"
                                       required/></dd>
                            <button type="button" id="${secType.name()}deleteButton"
                                    onclick="deleteSection('${secType.name()}')">
                                Удалить
                            </button>
                        </c:if>
                    </dl>
                </div>
            </c:when>
            <c:when test="${secType=='ACHIEVEMENT' || secType=='QUALIFICATIONS'}">
                <div id="${secType.name()}div">
                    <dl>
                        <dt><b>${secType.title}</b></dt>

                        <c:if test='<%=((ListSection) section).getDescriptionList().size() == 0%>'>
                            <button type="button" id="${secType.name()}addButton"
                                    onclick="addSection('${secType.name()}')">
                                Добавить
                            </button>
                        </c:if>

                        <c:if test='<%=((ListSection) section).getDescriptionList().size() != 0%>'>
                            <dd><textarea id="${secType.name()}" name="${secType.name()}" rows="4" cols="70"
                                          style="resize:none;"
                                          required><%=String.join("\n", ((ListSection) section).getDescriptionList())%></textarea>
                            </dd>
                            <button type="button" id="${secType.name()}deleteButton"
                                    onclick="deleteSection('${secType.name()}')">
                                Удалить
                            </button>
                        </c:if>
                    </dl>
                </div>
            </c:when>
            <c:when test="${secType=='EXPERIENCE' || secType=='EDUCATION'}">
                <div id="${secType.name()}div">
                    <dl>
                        <dt><b>${secType.title}</b></dt>
                        <c:set var="OrgList" value="<%=((OrganizationsSection) section).getOrganizations()%>"/>

                        <c:if test="${OrgList.size() == 0}">
                            <button type="button" id="${secType.name()}addButton"
                                    onclick="addSection('${secType.name()}')">
                                Добавить организацию
                            </button>
                        </c:if>

                        <c:forEach items="${OrgList}" var="org" varStatus="orgStat"><br>
                            <div id="${secType.name()}${orgStat.index}div" class="${secType.name()}orgs">
                                <c:if test="${OrgList.size() != 0}">
                                    <dl>
                                        <dt>Название организации</dt>
                                        <dd><input type="text" id="${secType.name()}" name="${secType.name()}" size=20
                                                   value="${org.link.title}" class="${secType.name()}${orgStat.index}"
                                                   required/>
                                        </dd>
                                        <dt>Ссылка</dt>
                                        <dd><input type="text" name="${secType.name()}urlAdr" size=20
                                                   value="${org.link.urlAdr}" class="${secType.name()}${orgStat.index}"
                                                   required/></dd>
                                        <c:forEach items="${org.positions}" var="pos" varStatus="posStat">
                                            <div class="${secType.name()}pos">
                                                <dl>
                                                    <dt>Начало</dt>
                                                    <dd><input type="date"
                                                               name="${secType.name()}${orgStat.index}startPeriod"
                                                               value="${pos.startPeriod}"
                                                               class="${secType.name()}${orgStat.index}" required></dd>
                                                    <dt>Окончание</dt>
                                                    <dd><input type="date"
                                                               name="${secType.name()}${orgStat.index}endPeriod"
                                                               value="${pos.endPeriod}"
                                                               class="${secType.name()}${orgStat.index}" required></dd>
                                                    <dt>Позиция</dt>
                                                    <dd><input type="text"
                                                               name="${secType.name()}${orgStat.index}position"
                                                               size=40
                                                               value="${pos.position}"
                                                               class="${secType.name()}${orgStat.index}" required/></dd>
                                                    <dt>Описание</dt>
                                                    <dd><textarea name="${secType.name()}${orgStat.index}desc" rows="4"
                                                                  cols="70"
                                                                  style="resize:none;" style="text-align:left"
                                                                  class="${secType.name()}${orgStat.index}"
                                                                  required>${pos.description}</textarea></dd>
                                                </dl>
                                            </div>
                                        </c:forEach>
                                        <button type="button" id="${secType.name()}"
                                                onclick="deleteOrganization('${secType.name()}${orgStat.index}div')"
                                                class="${secType.name()}${orgStat.index}">
                                            Удалить организацию
                                        </button>
                                    </dl>
                                </c:if>
                            </div>
                        </c:forEach>
                    </dl>
                </div>

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
