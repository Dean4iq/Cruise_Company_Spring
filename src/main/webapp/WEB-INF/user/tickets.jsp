<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>${sessionLocalization['tickets.head']}</title>

        <style>
            <jsp:directive.include file="/styles/css/bootstrap.min.css"/>
        </style>

        <script>
            <jsp:directive.include file="/styles/js/bootstrap.min.js"/>
        </script>
    </head>

    <body>
        <jsp:include page="/styles/pages/navbar.jsp"/>

        <table align="center" style="width:80%" class="table">
            <caption style="caption-side:top;"><b>${room.ship.name}</b></caption>
            <thead class="thead-light">
                <tr>
                    <th scope="col">${sessionLocalization['tickets.place_number']}</th>
                    <th scope="col">${sessionLocalization['tickets.place_type']}</th>
                    <th scope="col">${sessionLocalization['tickets.price']}</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="room" items="${roomList}" varStatus="counter">
                    <tr>
                        <th scope="row">${counter.count}.</td>
                        <td>
                            <c:set var="room_type" value="room_type.${room.roomType.name}"/>
                            ${sessionLocalization[room_type]}
                        </td>
                        <td>
                            <fmt:formatNumber value="${room.price}" minFractionDigits="2" type="currency" currencySymbol="₴"/>
                        </td>
                        <td>
                            <c:if test="${room.available}">
                                <form method="get">
                                    <input type="hidden" name="roomId" value="${room.id}"/>
                                    <input type="hidden" name="shipRoomId" value="${counter.count}"/>
                                    <input type="submit" value="${sessionLocalization['tickets.select_ticket']}"/>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>