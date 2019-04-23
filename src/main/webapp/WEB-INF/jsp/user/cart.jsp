<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
    <head>
        <title><spring:message code="cart.head"/></title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>

        <c:if test="${paymentAccepted}">
            <div class="alert alert-success" role="alert">
                <spring:message code="cart.msg.paymentAccepted"/>
            </div>
        </c:if>
        <c:if test="${paymentDeclined}">
            <div class="alert alert-warning" role="alert">
                <spring:message code="cart.msg.purchasingDeclined"/>
            </div>
        </c:if>
        <c:if test="${alreadyTaken}">
            <div class="alert alert-danger" role="alert">
                <spring:message code="cart.msg.alreadyTaken"/>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty sessionCart.ticket}">
                <fmt:setLocale value="${sessionLanguage}" scope="session"/>
                <table class="table" style="margin-left:20px;width:95%">
                    <tr>
                        <td scope="row"><spring:message code="cart.cruise_name"/>:</td>
                        <td>${sessionCart.ticket.cruise.name}</td>
                    </tr>
                    <tr>
                        <td scope="row"><spring:message code="cart.ship_name"/>:</td>
                        <td>${sessionCart.ticket.cruise.ship.name}</td>
                    </tr>
                    <tr>
                        <td scope="row"><spring:message code="cart.room_number"/>:</td>
                        <td>${shipRoomNumber}</td>
                    </tr>
                    <tr>
                        <td scope="row"><spring:message code="cart.room_type"/>:</td>
                        <td>
                            <c:set var="roomType" value="room_type.${sessionCart.ticket.room.roomType.name}"/>
                            <spring:message code="${roomType}"/>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row"><spring:message code="cart.departure_date"/>:</td>
                        <td><fmt:formatDate value="${sessionCart.ticket.cruise.date}" type="date"/></td>
                    </tr>
                    <tr>
                        <td scope="row"><spring:message code="cart.number_price"/>:</td>
                        <td><fmt:formatNumber minFractionDigits="2" value="${sessionCart.ticket.room.price}" type="currency" currencySymbol="₴"/></td>
                    </tr>
                    <tr>
                        <td scope="row"><spring:message code="cart.total_price"/>:</td>
                        <td><fmt:formatNumber minFractionDigits="2" value="${sessionCart.ticket.price}" type="currency" currencySymbol="₴"/></td>
                    </tr>
                </table>

                <div style="margin-bottom:25px;">
                    <form action="${pageContext.request.contextPath}/user/cart/accept-payment" method="post" style="margin-left: 100px; display:inline;">
                        <button type="submit" class="btn btn-success"><spring:message code='cart.purchasing.accept'/></button>
                    </form>
                    <form action="${pageContext.request.contextPath}/user/cart/decline-payment" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-danger"><spring:message code='cart.purchasing.decline'/></button>
                    </form>
                </div>

                <p style="margin:0 30px; background:#EFEFEF;">
                    <spring:message code="cart.excursion.selected"/>:
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col" width="50%"><spring:message code="cart.excursion.col.descr"/></th>
                                <th scope="col" width="30%"><spring:message code="cart.excursion.col.harbor"/></th>
                                <th scope="col" width="10%"><spring:message code="cart.excursion.col.price"/></th>
                                <th width="10%"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="excursion" items="${sessionCart.excursionList}">
                                <tr>
                                    <form action="${pageContext.request.contextPath}/user/cart/remove-excursion" method="post">
                                        <td>${localeTourInfo[excursion.information]}</td>
                                        <td>
                                            <c:set var="HarborName" value="${excursion.harbor.country.name}.${excursion.harbor.name}"/>
                                            ${harborMap[HarborName]}
                                            (${countryMap[excursion.harbor.country.name]})
                                        </td>
                                        <td><fmt:formatNumber value="${excursion.price}" minFractionDigits="2" type="currency" currencySymbol="₴"/></td>
                                        <td>
                                            <input type="hidden" name="excursionId" value="${excursion.id}"/>
                                            <input type="submit" name="removeExcursion" value="<spring:message code='cart.excursion.remove'/>"/>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </p>
                <p style="margin:0 30px; background:#EFEFEF;">
                    <spring:message code="cart.excursion.available"/>:
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col" width="50%"><spring:message code="cart.excursion.col.descr"/></th>
                                <th scope="col" width="30%"><spring:message code="cart.excursion.col.harbor"/></th>
                                <th scope="col" width="10%"><spring:message code="cart.excursion.col.price"/></th>
                                <th width="10%"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="excursion" items="${excursionList}">
                                <tr>
                                    <form action="${pageContext.request.contextPath}/user/cart/add-excursion" method="post">
                                        <td>${localeTourInfo[excursion.information]}</td>
                                        <td>
                                            <c:set var="HarborName" value="${excursion.harbor.country.name}.${excursion.harbor.name}"/>
                                            ${harborMap[HarborName]}
                                            (${countryMap[excursion.harbor.country.name]})
                                        </td>
                                        <td><fmt:formatNumber value="${excursion.price}" minFractionDigits="2" type="currency" currencySymbol="₴"/></td>
                                        <td>
                                            <input type="hidden" name="excursionId" value="${excursion.id}"/>
                                            <input type="submit" name="addNewExcursion" value="<spring:message code='cart.excursion.add'/>"/>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </p>
            </c:when>
            <c:otherwise>
                <h2><spring:message code="cart.empty"/></h2>
            </c:otherwise>
        </c:choose>
    </body>
</html>