<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<html>
    <head>
        <title><spring:message code="register.sign_up"/></title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
    </head>

    <body>
        <form method="GET">
            <button type="submit" name="preferredLanguage" value="en"><img src="${pageContext.request.contextPath}/styles/img/icon_US.png"/></button>
            <button type="submit" name="preferredLanguage" value="uk"><img src="${pageContext.request.contextPath}/styles/img/icon_UA.png"/></button>
        </form>
        <div class="container" style="margin:20px 100px 0 100px;">
            <h4 class="mb-3"><spring:message code="register.sign_up"/></h4>
            <form class="needs-validation" method="post">
                <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="name"><spring:message code="form.name"/></label>
                            <input path="name" type="text" class="form-control" name="name" placeholder value required>
                            <div class="invalid-feedback">
                                <spring:message code="form.empty_field"/>
                            </div>
                            <c:if test="${nameInvalid}">
                                <div class="alert alert-danger" role="alert">
                                    <spring:message code="register.hint.name"/>
                                </div>
                            </c:if>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="surname"><spring:message code="form.surname"/></label>
                            <input type="text" class="form-control" id="surname" name="surname" placeholder value required>
                            <div class="invalid-feedback">
                                <spring:message code="form.empty_field"/>
                            </div>
                            <c:if test="${surnameInvalid}">
                                <div class="alert alert-danger" role="alert">
                                    <spring:message code="register.hint.surname"/>
                                </div>
                            </c:if>
                        </div>
                </div>
                    <div class="mb-3">
                        <label for="email">Email</label>
                        <input type="text" class="form-control" id="email" name="email" placeholder value required>
                        <div class="invalid-feedback">
                            <spring:message code="form.empty_field"/>
                        </div>
                        <c:if test="${emailInvalid}">
                            <div class="alert alert-danger" role="alert">
                                <spring:message code="register.hint.email"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="login">Login</label>
                        <input type="text" class="form-control" id="login" name="login" placeholder value required>
                        <div class="invalid-feedback">
                            <spring:message code="form.empty_field"/>
                        </div>
                        <c:if test="${notUniqueLogin}">
                            <div class="alert alert-danger" role="alert">
                                <spring:message code="register.account_already_exists"/>
                            </div>
                        </c:if>
                        <c:if test="${loginInvalid}">
                            <div class="alert alert-danger" role="alert">
                                <spring:message code="register.hint.login"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="pass"><spring:message code="form.password"/></label>
                        <input type="password" class="form-control" id="pass" name="password" placeholder value required>
                        <div class="invalid-feedback">
                            <spring:message code="form.empty_field"/>
                        </div>
                        <c:if test="${passwordInvalid}">
                            <div class="alert alert-danger" role="alert">
                                <spring:message code="register.hint.password"/>
                            </div>
                        </c:if>
                    </div>
                <button class="btn btn-primary btn-lg btn-block" type="submit" name="register"><spring:message code="register.sign_up"/></button>
            </form>
            <a href="${pageContext.request.contextPath}/login"><spring:message code="register.already_have_an_acc"/></a>
        </div>
    </body>
</html>