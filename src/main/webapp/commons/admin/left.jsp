<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="mb-4 ps-2">Menu</h5>
<nav class="nav flex-column">
    <a class="nav-link" href="<c:url value='/admin/category/list'/>">
        <i class="bi bi-folder me-2"></i> Category Manager
    </a>
    <a class="nav-link" href="<c:url value='/admin/video/list'/>">
        <i class="bi bi-play-circle me-2"></i> Video Manager
    </a>
     <a class="nav-link" href="<c:url value='/home'/>">
        <i class="bi bi-box-arrow-left me-2"></i> Back to Website
    </a>
</nav>