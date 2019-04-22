<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
    <head>
        <title><spring:message code="main.user.descr"/></title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>

        <div style="margin:10px 20px;">
            <spring:message code="admin.search.hint"/>
            <form method="post">
                <input type="text" required name="ticketId"/>
                <input type="submit" name="searchSubmit" value="<spring:message code='admin.search.submit'/>"/>
            </form>
        </div>
        <c:if test="${ticketNotFound}">
            <div class="alert alert-danger" role="alert">
                <spring:message code="admin.search.noTicketFound"/>
            </div>
        </c:if>
        <c:if test="${not empty foundedTicket}">
            <fmt:setLocale value="${sessionLanguage}" scope="session"/>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col"><spring:message code="admin.search.th.user"/></th>
                        <th scope="col"><spring:message code="admin.search.th.cruise"/></th>
                        <th scope="col"><spring:message code="admin.search.th.ship"/></th>
                        <th scope="col"><spring:message code="admin.search.th.room"/></th>
                        <th scope="col"><spring:message code="admin.search.th.room_type"/></th>
                        <th scope="col"><spring:message code="admin.search.th.bonuses"/></th>
                        <th scope="col"><spring:message code="admin.search.th.purchased"/></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <b>${foundedTicket.user.login}</b>
                            <br>
                            ${foundedTicket.user.name} ${foundedTicket.user.surname}
                            <br>
                            ${foundedTicket.user.email}
                        </td>
                        <td>
                            ${foundedTicket.cruise.name}
                            (<fmt:formatDate value="${foundedTicket.cruise.date}" type="date"/>)
                        </td>
                        <td>${foundedTicket.cruise.ship.name}</td>
                        <td>${foundedTicket.room.id}</td>
                        <td>
                            <c:set var="room_type" value="room_type.${foundedTicket.room.roomType.name}"/>
                            <spring:message code="${room_type}"/>
                        </td>
                        <td>
                            <c:forEach var="bonuses" items="${foundedTicket.room.roomType.bonuses}">
                                <c:set var="bonuses_type" value="bonuse.${bonuses.name}"/>
                                <spring:message code="${bonuses_type}"/><br>
                            </c:forEach>
                        </td>
                        <td><fmt:formatDate value="${foundedTicket.purchaseDate}" type="date"/></td>
                    </tr>
                </tbody>
            </table>
        </c:if>
    </body>
</html>