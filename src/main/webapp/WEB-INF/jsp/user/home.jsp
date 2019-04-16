<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>${sessionLocalization['main.user.descr']}</title>

        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "js/bootstrap.min.js" ></script>
        <base>
    </head>

    <body>
        <jsp:include page="../../pages/navbar.jsp"/>
        <h1>${sessionLocalization['main.user.descr']}</h1>
        <h2 align="center">${sessionLocalization['main.news']}</h2>
    </body>
</html>