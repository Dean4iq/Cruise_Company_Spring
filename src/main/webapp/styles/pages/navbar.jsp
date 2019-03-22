<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:#00802b;margin:10px">
            <a class="navbar-brand" href=""></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}">
                            ${sessionLocalization['menu.main']}
                        </a>
                    </li>
                    <c:forEach items="${Role.menuBarLinks}" var="keyValue">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/${keyValue.key}">
                                <c:out value="${sessionLocalization[keyValue.value]}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
                <ul class="navbar-nav">
                    <c:if test="${Role.type == 'USER'}">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/cart">
                            ${sessionLocalization['cart.head']}
                            <c:if test="${not empty sessionCart}">
                                (1)
                            </c:if>
                            <c:if test="${empty sessionCart}">
                                (0)
                            </c:if>
                        </a>
                    </c:if>
                    <li class="nav-item active">
                        <h5 style="padding:8px; color:#ffff00;">${User.login}</h5>
                    </li>
                    <li class="nav-item active" style="margin-right:35px;">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                            ${sessionLocalization['menu.logout']}
                        </a>
                    </li>
                    <form name="langForm" method="post" align="right">
                        <select name="preferredLanguage" onchange="document.langForm.submit();">
                            <option ${sessionLanguage=="en"?"selected":""} value="en">English</option>
                            <option ${sessionLanguage=="uk"?"selected":""} value="uk">Українська</option>
                        </select>
                    </form>
                </ul>
            </div>
        </nav>