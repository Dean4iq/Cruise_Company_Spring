<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>${sessionLocalization['login.sign_in']}</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/signin.css"/>
    </head>

    <body class="text-center" onload='document.loginForm.username.focus();'>
        <c:url value="/j_spring_security_check" var="loginUrl" />
        <div class="form-signin">
            <form method="POST">
                <button type="submit" name="preferredLanguage" value="en"><img src="${pageContext.request.contextPath}/styles/img/icon_US.png"/></button>
                <button type="submit" name="preferredLanguage" value="uk"><img src="${pageContext.request.contextPath}/styles/img/icon_UA.png"/></button>
            </form>
            <form name="loginForm" action="${loginUrl}" method="POST" autocomplete="off">
                <h1 class="h3 mb-3 font-weight-normal">${sessionLocalization['login.login_info']}</h1>
                <label for="inputLogin" class="sr-only">Login</label>
                <input type="text" id="inputLogin" name="loginUser" class="form-control" placeholder="Login" required oninvalid="this.setCustomValidity('${sessionLocalization['form.empty_field']}')">
                <label for="inputPassword" class="sr-only">${sessionLocalization['login.password']}</label>
                <input type="password" name="passwordUser" id="inputPassword" class="form-control" placeholder="${sessionLocalization['form.password']}" required oninvalid="this.setCustomValidity('${sessionLocalization['form.empty_field']}')">
                <c:if test="${invalidLoginOrPassword}">
                    <div class="alert alert-danger" role="alert">
                        ${sessionLocalization['login.invalid_login_pass']}
                    </div>
                </c:if>
                <c:if test="${noSuchId}">
                    <div class="alert alert-danger" role="alert">
                        ${sessionLocalization['login.account_not_exists']}
                    </div>
                </c:if>
                <c:if test="${alreadyLoggedIn}">
                    <div class="alert alert-danger" role="alert">
                        ${sessionLocalization['login.account_already_in_system']}
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
                <input name="submit" class="btn btn-lg btn-primary btn-block" type="submit" value="${sessionLocalization['login.sign_in']}"/>
                <a href="${pageContext.request.contextPath}/register">${sessionLocalization['login.register_hint']}</a>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
        </div>
    </body>
</html>