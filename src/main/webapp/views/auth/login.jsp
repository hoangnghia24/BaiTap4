<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head>
    <title>Login</title>
</head>
<body>
    <h3 class="text-center mb-4">Login System</h3>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <form action="<c:url value='/login'/>" method="post">
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" class="form-control" name="username" value="${username}" required autofocus>
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" class="form-control" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Login</button>
        <div class="d-flex justify-content-between mt-3">
            <a href="<c:url value='/register'/>">Register new account</a>
            <a href="<c:url value='/forgot-password'/>">Forgot password?</a>
        </div>
    </form>
</body>