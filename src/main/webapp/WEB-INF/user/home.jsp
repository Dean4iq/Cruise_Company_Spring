<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>${sessionLocalization['main.user.descr']}</title>

        <style>
            <jsp:directive.include file="/styles/css/bootstrap.min.css"/>
        </style>

        <script>
            <jsp:directive.include file="/styles/js/bootstrap.min.js"/>
        </script>
    </head>

    <body>
        <jsp:include page="/styles/pages/navbar.jsp"/>
        <h1>${sessionLocalization['main.user.descr']}</h1>
        <h2 align="center">${sessionLocalization['main.news']}</h2>
        <div style="margin:0 50px;border:1px solid;min-height:70%;background:grey;">
            <div style="margin: 5px;padding:5px;background:white;">
                <h1 style="text-decoration:underline">
                    Ціни на свята знизились!
                </h1>
                Ціни на всі поїзди Південно-Західної залізниці було знижено в 0.9 разів!
                <br>
                Бажаємо Вам приємних свят!
            </div>
        </div>
    </body>
</html>