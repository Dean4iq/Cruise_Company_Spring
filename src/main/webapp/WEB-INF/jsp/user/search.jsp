<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
    <head>
        <title><spring:message code="search.head"/></title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>

        <form style="margin-left:100px;" method="post">
            <table>
                <tbody>
                    <tr>
                        <td><spring:message code="search.land_option"/></td>
                        <td>
                            <select name="countryToVisit">
                                <c:forEach var="country" items="${countryMap}">
                                    <option value="${country.key}">${country.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="submit" name="searchCruise" value="<spring:message code='search.submit'/>"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>

        <c:choose>
            <c:when test="${searchCommitted && (empty cruiseList)}">
                <h2 align="center"><spring:message code="search.empty_result"/></h2>
            </c:when>
            <c:when test="${searchCommitted && (not empty cruiseList)}">
                <fmt:setLocale value="${sessionLanguage}" scope="session"/>
                <table class="table table-striped" align="center" style="width:90%;">
                    <caption style="caption-side:top;">${sessionLocalization['search.result']}</caption>
                    <thead>
                        <tr>
                            <th scope="col"><spring:message code="search.th.cruise_name"/></th>
                            <th scope="col"><spring:message code="search.th.from"/></th>
                            <th scope="col"><spring:message code="search.th.to"/></th>
                            <th scope="col"><spring:message code="search.th.date"/></th>
                            <th scope="col"><spring:message code="search.th.duration"/></th>
                            <th scope="col"><spring:message code="search.th.price"/></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                            <c:forEach var="cruise" items="${cruiseList}">
                                <tr>
                                    <th scope="row">${cruise.name}</th>
                                    <td>
                                        <c:set var="HarborName" value="${cruise.routeList[0].harbor.country.name}.${cruise.routeList[0].harbor.name}"/>
                                        ${harborMap[HarborName]}
                                        (${countryMap[cruise.routeList[0].harbor.country.name]})
                                    </td>
                                    <td>
                                        <c:set var="HarborName" value="${cruise.routeList[fn:length(cruise.routeList)-1].harbor.country.name}.${cruise.routeList[fn:length(cruise.routeList)-1].harbor.name}"/>
                                        ${harborMap[HarborName]}
                                        (${countryMap[cruise.routeList[fn:length(cruise.routeList)-1].harbor.country.name]})
                                    </td>
                                    <td><fmt:formatDate value="${cruise.date}" type="date"/></td>
                                    <td>${cruise.daysInRoute} <spring:message code="search.days"/></td>
                                    <td><fmt:formatNumber value="${cruise.price}" minFractionDigits="2" type="currency" currencySymbol="₴"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/user/search/commit-search" method="post">
                                            <input type="hidden" name="cruiseId" value="${cruise.id}"/>
                                            <input type="submit" name="cruise" value="<spring:message code='form.look_ticket'/>"/>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <details>
                                            <summary><spring:message code="search.th.details"/></summary>
                                            <p style="font-weight: bold;font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"; display:inline;">
                                            <spring:message code="search.th.ship"/>: '${cruise.ship.name}'
                                            </p>
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th><spring:message code="search.th.route"/></th>
                                                        <th><spring:message code="search.th.arrival"/></th>
                                                        <th><spring:message code="search.th.departure"/></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="route" items="${cruise.routeList}" varStatus="counter">
                                                        <tr>
                                                            <td>
                                                                <b>${counter.count}.</b>
                                                                <c:set var="HarborName" value="${route.harbor.country.name}.${route.harbor.name}"/>
                                                                ${harborMap[HarborName]}
                                                                (${countryMap[route.harbor.country.name]})
                                                            </td>
                                                            <td><fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${route.arrival}"/></td>
                                                            <td><fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${route.departure}"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </details>
                                    </td>
                                </tr>
                            </c:forEach>
                    </tbody>
                </table>
            </c:when>
        </c:choose>
    </body>
</html>