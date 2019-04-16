<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>${sessionLocalization['main.user.descr']}</title>

        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "js/bootstrap.min.js" ></script>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>

        <div style="margin:10px 20px;">
            ${sessionLocalization['admin.search.hint']}
            <form method="post">
                <input type="text" required name="ticketId"/>
                <input type="submit" name="searchSubmit" value="${sessionLocalization['admin.search.submit']}"/>
            </form>
        </div>
        <c:if test="${ticketNotFound}">
            <div class="alert alert-danger" role="alert">
                ${sessionLocalization['admin.search.noTicketFound']}
            </div>
        </c:if>
        <c:if test="${not empty foundedTicket}">
            <fmt:setLocale value="${sessionLanguage}"/>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">${sessionLocalization['admin.search.th.user']}</th>
                        <th scope="col">${sessionLocalization['admin.search.th.cruise']}</th>
                        <th scope="col">${sessionLocalization['admin.search.th.ship']}</th>
                        <th scope="col">${sessionLocalization['admin.search.th.room']}</th>
                        <th scope="col">${sessionLocalization['admin.search.th.room_type']}</th>
                        <th scope="col">${sessionLocalization['admin.search.th.bonuses']}</th>
                        <th scope="col">${sessionLocalization['admin.search.th.purchased']}</th>
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
                            ${sessionLocalization[room_type]}
                        </td>
                        <td>
                            <c:forEach var="bonuses" items="${foundedTicket.room.roomType.bonuses}">
                                <c:set var="bonuses_type" value="bonuse.${bonuses.name}"/>
                                ${sessionLocalization[bonuses_type]}<br>
                            </c:forEach>
                        </td>
                        <td><fmt:formatDate value="${foundedTicket.purchaseDate}" type="date"/></td>
                    </tr>
                </tbody>
            </table>
        </c:if>
    </body>
</html>