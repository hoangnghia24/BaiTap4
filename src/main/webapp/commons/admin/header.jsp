<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3 border-bottom border-secondary">
    <a class="navbar-brand" href="<c:url value='/admin/home'/>">Admin Panel</a>
    <div class="collapse navbar-collapse justify-content-end">
        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    Hi, ${sessionScope.loginedUsername}
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="<c:url value='/user/profile'/>">Profile</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="<c:url value='/logout'/>">Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>