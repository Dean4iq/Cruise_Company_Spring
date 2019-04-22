<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title><spring:message code="main.user.descr"/></title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
        <base>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>
        <h1><spring:message code="main.user.descr"/></h1>
        <h2 align="center"><spring:message code="main.news"/></h2>
    </body>
</html>