<%@ page import="ru.javawebinar.model.*" %>
<%@ page import="ru.javawebinar.util.DateUtil" %>
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
                                    onclick="sectionSelector('${secType.name()}')">Добавить
                            </button>
                        </c:if>
                        <c:if test='<%=!((TextSection) section).getText().equals("")%>'>
                            <dd><input type="text" id="${secType.name()}" name="${secType.name()}" size=70
                                       value="<%=section%>"
                                       required/></dd>
                            <button type="button" id="${secType.name()}deleteButton"
                                    onclick="deleteSection('${secType.name()}')">Удалить
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
                                    onclick="sectionSelector('${secType.name()}')">Добавить
                            </button>
                        </c:if>
                        <c:if test='<%=((ListSection) section).getDescriptionList().size() != 0%>'>
                            <dd><textarea id="${secType.name()}" name="${secType.name()}" rows="4" cols="70"
                                          style="resize:none;"
                                          required><%=String.join("\n", ((ListSection) section).getDescriptionList())%></textarea>
                            </dd>
                            <button type="button" id="${secType.name()}deleteButton"
                                    onclick="deleteSection('${secType.name()}')">Удалить
                            </button>
                        </c:if>
                    </dl>
                </div>
            </c:when>
            <c:when test="${secType=='EXPERIENCE' || secType=='EDUCATION'}">
                <div id="${secType.name()}div">
                <dl>
                <dt><b>${secType.title}</b></dt>
                <dd>
                <c:set var="OrgList" value="<%=((OrganizationsSection) section).getOrganizations()%>"/>

                <button type="button" id="${secType.name()}addButton"
                        onclick="sectionSelector('${secType.name()}')">Добавить организацию
                </button>

                <c:forEach items="${OrgList}" var="org" varStatus="orgStat"><br>
                    <div id="${secType.name()}${orgStat.index}div" class="${secType.name()}orgs">
                    <c:if test="${OrgList.size() != 0}">
                        <dl>
                            <dt>Название организации</dt>
                            <dd><input type="text" id="${secType.name()}" name="${secType.name()}" size=20
                                       value="${org.link.title}" required/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>Ссылка</dt>
                            <dd><input type="text" name="${secType.name()}urlAdr" size=20
                                       value="${org.link.urlAdr}" required/></dd>
                        </dl>

                        <button type="button" id="${secType.name()}${orgStat.index}addButton"
                                onclick="addPosition('${secType.name()}${orgStat.index}')">Добавить должность
                        </button>
                        <button type="button" id="${secType.name()}"
                                onclick="deleteOrgPos('${secType.name()}${orgStat.index}div')">Удалить организацию
                        </button>
                        <c:forEach items="${org.positions}" var="pos" varStatus="posStat">
                            <jsp:useBean id="pos" type="ru.javawebinar.model.Organization.Position"/>
                            <div id="${secType.name()}${orgStat.index}pos${posStat.index}"
                                 class="${secType.name()}${orgStat.index}pos">
                                <dl>
                                    <dt>Начало</dt>
                                    <dd><input type="date" name="${secType.name()}${orgStat.index}startPeriod"
                                               value="${pos.startPeriod}" required></dd>
                                </dl>
                                <dl>
                                    <dt>Окончание</dt>
                                    <c:if test="<%=pos.getEndPeriod().equals(DateUtil.NOW)%>">
                                        <dd><input type="date"
                                                   id="${secType.name()}${orgStat.index}endPeriod${posStat.index}"
                                                   name="${secType.name()}${orgStat.index}endPeriod"
                                                   value="${pos.endPeriod}" disabled required>
                                            <input type="checkbox"
                                                   id="${secType.name()}${orgStat.index}checkNow${posStat.index}"
                                                   name="${secType.name()}${orgStat.index}checkNow" checked
                                                   onclick="checkNow('${secType.name()}${orgStat.index}','${posStat.index}')">
                                            <label for="${secType.name()}${orgStat.index}checkNow">Сейчас</label>
                                        </dd>
                                    </c:if>

                                    <c:if test="<%=!pos.getEndPeriod().equals(DateUtil.NOW)%>">
                                        <dd><input type="date"
                                                   id="${secType.name()}${orgStat.index}endPeriod${posStat.index}"
                                                   name="${secType.name()}${orgStat.index}endPeriod"
                                                   value="${pos.endPeriod}" required>
                                            <input type="checkbox"
                                                   id="${secType.name()}${orgStat.index}checkNow${posStat.index}"
                                                   name="${secType.name()}${orgStat.index}checkNow"
                                                   onclick="checkNow('${secType.name()}${orgStat.index}', '${posStat.index}')">
                                            <label for="${secType.name()}${orgStat.index}checkNow">Сейчас</label>
                                        </dd>
                                    </c:if>

                                </dl>
                                <dl>
                                    <dt>Позиция</dt>
                                    <dd><input type="text" name="${secType.name()}${orgStat.index}position"
                                               size=40 value="${pos.position}" required/></dd>
                                </dl>
                                <dl>
                                    <dt>Описание</dt>
                                    <dd><textarea name="${secType.name()}${orgStat.index}desc" rows="4" cols="70"
                                                  style="resize:none;" style="text-align:left"
                                                  required>${pos.description}</textarea></dd>
                                </dl>
                            <dd>
                                <button type="button" id="${secType.name()}"
                                        onclick="deleteOrgPos('${secType.name()}${orgStat.index}pos${posStat.index}')">
                                    Удалить должность
                                </button>
                            </dd>
                            </div>
                        </c:forEach>

                        </dl>
                    </c:if>
                    </div>
                </c:forEach>
                </dd>
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
