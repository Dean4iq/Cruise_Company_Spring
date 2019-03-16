<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<html>
    <head>
        <title>${sessionLocalization['login.sign_in']}</title>
        <style>
            <jsp:directive.include file="/styles/css/bootstrap.min.css" />
            <jsp:directive.include file="/styles/css/signin.css" />
        </style>
    </head>

    <body class="text-center">
        <form class="form-signin" name="signInForm" method="post">
            <select style="position:fixed; top:5px; left:5px;" name="preferredLanguage" onchange="document.signInForm.submit();">
                <option ${sessionLanguage=="en"?"selected":""} value="en">English</option>
                <option ${sessionLanguage=="uk"?"selected":""} value="uk">Українська</option>
            </select>
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
            <button name="login" class="btn btn-lg btn-primary btn-block" type="submit">${sessionLocalization['login.sign_in']}</button>
            <a href="${pageContext.request.contextPath}/register">${sessionLocalization['login.register_hint']}</a>
        </form>
    </body>
</html>