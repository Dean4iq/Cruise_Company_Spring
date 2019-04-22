<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<html>
    <body>
        <h1><spring:message code="error.text"/></h1>
        <a href="${pageContext.request.contextPath}"><spring:message code="error.link.main"/></a>
    </body>
</html>
