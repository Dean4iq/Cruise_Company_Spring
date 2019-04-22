<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:#00802b;margin:10px">
            <a class="navbar-brand" href=""></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}">
                            <spring:message code="menu.main"/>
                        </a>
                    </li>
                    <c:forEach items="${Role.menuBarLinks}" var="keyValue">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/${keyValue.key}">
                                <spring:message code="${keyValue.value}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
                <ul class="navbar-nav">
                    <c:if test="${Role.type == 'USER'}">
                        <c:if test="${not empty sessionCart}">
                            <a class="nav-link" style="color:#FFF;" href="${pageContext.request.contextPath}/user/cart">
                                <spring:message code="cart.head"/>
                                (1)
                        </c:if>
                        <c:if test="${empty sessionCart}">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/cart">
                                <spring:message code="cart.head"/>
                                (0)
                        </c:if>
                        </a>
                    </c:if>
                    <li class="nav-item active">
                        <h5 style="padding:8px; color:#ffff00;">${User.login}</h5>
                    </li>
                    <li class="nav-item active" style="margin-right:35px;">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                            <spring:message code="menu.logout"/>
                        </a>
                    </li>
                    <form align="right">
                        <button style="background:transparent;border:none;display:block;" type="submit" name="preferredLanguage" value="en"><img src="${pageContext.request.contextPath}/styles/img/icon_US.png"/></button>
                        <button style="background:transparent;border:none;display:block;" type="submit" name="preferredLanguage" value="uk"><img src="${pageContext.request.contextPath}/styles/img/icon_UA.png"/></button>
                    </form>
                </ul>
            </div>
        </nav>