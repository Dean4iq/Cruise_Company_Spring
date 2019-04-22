<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">

<!DOCTYPE html>
<html lang="en">
    <head>
        <title><spring:message code="login.sign_in"/></title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/signin.css"/>
    </head>

    <body class="text-center" onload='document.loginForm.username.focus();'>
        <c:url value="/j_spring_security_check" var="loginUrl" />
        <div class="form-signin">
            <form method="get">
                <button type="submit" name="preferredLanguage" value="en"><img src="${pageContext.request.contextPath}/styles/img/icon_US.png"/></button>
                <button type="submit" name="preferredLanguage" value="uk"><img src="${pageContext.request.contextPath}/styles/img/icon_UA.png"/></button>
            </form>
            <form name="loginForm" action="${loginUrl}" method="POST" autocomplete="off">
                <h1 class="h3 mb-3 font-weight-normal"><spring:message code="login.login_info"/></h1>
                <label for="inputLogin" class="sr-only">Login</label>
                <input type="text" id="inputLogin" name="loginUser" class="form-control" placeholder="Login" required oninvalid="this.setCustomValidity('<spring:message code="form.empty_field"/>')">
                <label for="inputPassword" class="sr-only"><spring:message code="login.password" text="default"/><spring:message code="form.password"/></label>
                <input type="password" name="passwordUser" id="inputPassword" class="form-control" placeholder='<spring:message code="form.password"/>' required oninvalid="this.setCustomValidity('<spring:message code="form.empty_field"/>')">
                <c:if test="${invalidLoginOrPassword}">
                    <div class="alert alert-danger" role="alert">
                        <spring:message code="login.invalid_login_pass"/>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        <spring:message code="login.invalid_login_pass"/>
                    </div>
                </c:if>
                <c:if test="${noSuchId}">
                    <div class="alert alert-danger" role="alert">
                        <spring:message code="login.account_not_exists"/>
                    </div>
                </c:if>
                <c:if test="${alreadyLoggedIn}">
                    <div class="alert alert-danger" role="alert">
                        <spring:message code="login.account_already_in_system"/>
                    </div>
                </c:if>
                <input name="submit" class="btn btn-lg btn-primary btn-block" type="submit" value="<spring:message code="login.sign_in"/>"/>
                <a href="${pageContext.request.contextPath}/register"><spring:message code="login.register_hint"/></a>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </div>
    </body>
</html>