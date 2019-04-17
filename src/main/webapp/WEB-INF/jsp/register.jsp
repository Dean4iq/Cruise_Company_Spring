<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<html>
    <head>
        <title>${sessionLocalization['register.sign_up']}</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/bootstrap.min.css"/>
        <script type = "text/javascript" src = "${pageContext.request.contextPath}/styles/js/bootstrap.min.js" ></script>
    </head>

    <body>
        <form method="POST">
            <button type="submit" name="preferredLanguage" value="en"><img src="${pageContext.request.contextPath}/styles/img/icon_US.png"/></button>
            <button type="submit" name="preferredLanguage" value="uk"><img src="${pageContext.request.contextPath}/styles/img/icon_UA.png"/></button>
        </form>
        <div class="container" style="margin:20px 100px 0 100px;">
            <h4 class="mb-3">${sessionLocalization['register.sign_up']}</h4>
            <form class="needs-validation" method="post">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="name">${sessionLocalization['form.name']}</label>
                        <input type="text" class="form-control" id="name" name="userName" placeholder value required>
                        <div class="invalid-feedback">
                            ${sessionLocalization['form.empty_field']}
                        </div>
                        <c:if test="${nameInvalid}">
                            <div class="alert alert-danger" role="alert">
                                ${sessionLocalization['register.hint.name']}
                            </div>
                        </c:if>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="surname">${sessionLocalization['form.surname']}</label>
                        <input type="text" class="form-control" id="surname" name="userSurname" placeholder value required>
                        <div class="invalid-feedback">
                            ${sessionLocalization['form.empty_field']}
                        </div>
                        <c:if test="${surnameInvalid}">
                            <div class="alert alert-danger" role="alert">
                                ${sessionLocalization['register.hint.surname']}
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" id="email" name="userEmail" placeholder value required>
                    <div class="invalid-feedback">
                        ${sessionLocalization['form.empty_field']}
                    </div>
                    <c:if test="${emailInvalid}">
                        <div class="alert alert-danger" role="alert">
                            ${sessionLocalization['register.hint.email']}
                        </div>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="login">Login</label>
                    <input type="text" class="form-control" id="login" name="userLogin" placeholder value required>
                    <div class="invalid-feedback">
                        ${sessionLocalization['form.empty_field']}
                    </div>
                    <c:if test="${notUniqueLogin}">
                        <div class="alert alert-danger" role="alert">
                            ${sessionLocalization['register.account_already_exists']}
                        </div>
                    </c:if>
                    <c:if test="${loginInvalid}">
                        <div class="alert alert-danger" role="alert">
                            ${sessionLocalization['register.hint.login']}
                        </div>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="pass">${sessionLocalization['form.password']}</label>
                    <input type="password" class="form-control" id="pass" name="userPassword" placeholder value required>
                    <div class="invalid-feedback">
                        ${sessionLocalization['form.empty_field']}
                    </div>
                    <c:if test="${passwordInvalid}">
                        <div class="alert alert-danger" role="alert">
                            ${sessionLocalization['register.hint.password']}
                        </div>
                    </c:if>
                </div>
                <button class="btn btn-primary btn-lg btn-block" type="submit" name="register">${sessionLocalization['register.sign_up']}</button>
            </form>
            <a href="${pageContext.request.contextPath}/login">${sessionLocalization['register.already_have_an_acc']}</a>
        </div>
    </body>
</html>