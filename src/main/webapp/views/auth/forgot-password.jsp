<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head>
    <title>Forgot Password</title>
</head>
<body>
    <div class="text-center mb-4">
        <i class="bi bi-shield-lock-fill text-primary" style="font-size: 3rem;"></i>
        <h3 class="mt-2">Forgot Password?</h3>
        <p class="text-muted">Enter your email address associated with your account, and we'll send you a link to reset your password.</p>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show">${error} <button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible fade show">${message} <button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>
    </c:if>

    <form action="<c:url value='/forgot-password'/>" method="post">
        <div class="mb-4">
            <label class="form-label fw-bold">Email Address</label>
            <div class="input-group">
                <span class="input-group-text bg-light"><i class="bi bi-envelope"></i></span>
                <input type="email" class="form-control form-control-lg" name="email" required placeholder="name@example.com" autofocus>
            </div>
        </div>

        <button type="submit" class="btn btn-primary w-100 btn-lg">
            <i class="bi bi-envelope-paper-fill me-2"></i> Send Reset Link
        </button>

        <div class="text-center mt-4 border-top pt-3">
            <a href="<c:url value='/login'/>" class="text-decoration-none">
                <i class="bi bi-arrow-left me-1"></i> Back to Login
            </a>
        </div>
    </form>
</body>