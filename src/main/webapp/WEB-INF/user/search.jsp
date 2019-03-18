<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>${sessionLocalization['search.head']}</title>

        <style>
            <jsp:directive.include file="/styles/css/bootstrap.min.css"/>
        </style>

        <script>
            <jsp:directive.include file="/styles/js/bootstrap.min.js"/>
        </script>
    </head>

    <body>
        <jsp:include page="/styles/pages/navbar.jsp"/>

        <form style="margin-left:100px;" method="post">
            <table>
                <tbody>
                    <tr>
                        <td>${sessionLocalization['search.land_option']}</td>
                        <td>
                            <select name="countryToVisit">
                                <c:forEach var="country" items="${countryMap}">
                                    <option value="${country.key}">${country.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="submit" name="searchCruise" value="${sessionLocalization['search.submit']}"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>

        <c:choose>
            <c:when test="${searchCommitted && (empty cruiseList)}">
                <h2 align="center">${sessionLocalization['search.empty_result']}</h2>
            </c:when>
            <c:when test="${searchCommitted && (not empty cruiseList)}">
                <fmt:setLocale value="${sessionLanguage}"/>
                <table class="table table-striped" align="center" style="width:90%;">
                    <caption style="caption-side:top;">${sessionLocalization['search.result']}</caption>
                    <thead>
                        <tr>
                            <th scope="col">${sessionLocalization['search.th.cruise_name']}</th>
                            <th scope="col">${sessionLocalization['search.th.from']}</th>
                            <th scope="col">${sessionLocalization['search.th.to']}</th>
                            <th scope="col">${sessionLocalization['search.th.date']}</th>
                            <th scope="col">${sessionLocalization['search.th.price']}</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <form method="get">
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
                                    <td><fmt:formatNumber value="${cruise.price}" type="currency" currencySymbol="₴"/></td>
                                    <td>
                                        <input type="hidden" name="cruiseId" value="${cruise.id}"/>
                                        <input type="submit" name="cruise" value="${sessionLocalization['form.look_ticket']}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <details>
                                            <summary>${sessionLocalization['search.th.details']}</summary>
                                            <p style="font-weight: bold;font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"; display:inline;">
                                            ${sessionLocalization['search.th.ship']}: '${cruise.ship.name}'
                                            </p>
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>${sessionLocalization['search.th.route']}</th>
                                                        <th>${sessionLocalization['search.th.arrival']}</th>
                                                        <th>${sessionLocalization['search.th.departure']}</th>
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
                        </form>
                    </tbody>
                </table>
            </c:when>
        </c:choose>
    </body>
</html>