<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pag" uri="/WEB-INF/custom taglibs/tld/paginate.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
    <head>
        <title><spring:message code="tickets.head"/></title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>

        <table align="center" style="width:80%" class="table">
            <caption style="caption-side:top;"><b>${room.ship.name}</b></caption>
            <thead class="thead-light">
                <tr>
                    <th scope="col"><spring:message code="tickets.place_number"/></th>
                    <th scope="col"><spring:message code="tickets.place_type"/></th>
                    <th scope="col"><spring:message code="tickets.price"/></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="room" items="${roomList}" varStatus="counter">
                    <c:set var="count" value="${counter.count + countModifier}" scope="page"/>
                    <tr>
                        <th scope="row">${count}.</td>
                        <td>
                            <c:set var="room_type" value="room_type.${room.roomType.name}"/>
                            <spring:message code="${room_type}"/>
                        </td>
                        <td>
                            <fmt:formatNumber value="${room.price}" minFractionDigits="2" type="currency" currencySymbol="₴"/>
                        </td>
                        <td>
                            <c:if test="${room.available}">
                                <form method="get">
                                    <input type="hidden" name="roomId" value="${room.id}"/>
                                    <input type="hidden" name="shipRoomId" value="${count}"/>
                                    <input type="submit" value="<spring:message code='tickets.select_ticket'/>"/>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <pag:pagination pageNumber="${pageNumber}" currentPage="${currentPage}"/>
    </body>
</html>