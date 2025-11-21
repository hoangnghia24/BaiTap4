<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property='title'>Admin Dashboard</sitemesh:write></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    
    <style>
        body { min-height: 100vh; display: flex; flex-direction: column; }
        .admin-container { display: flex; flex: 1; }
        .sidebar { width: 250px; background-color: #343a40; color: white; min-height: 100%; }
        .sidebar a { color: #adb5bd; text-decoration: none; padding: 10px 15px; display: block; }
        .sidebar a:hover, .sidebar a.active { color: white; background-color: #495057; }
        .main-content { flex: 1; padding: 20px; background-color: #f8f9fa; }
    </style>

    <sitemesh:write property='head'/>
</head>
<body>
    <jsp:include page="/commons/admin/header.jsp" />

    <div class="admin-container">
        <div class="sidebar p-3">
             <jsp:include page="/commons/admin/left.jsp" />
        </div>

        <main class="main-content">
             <c:if test="${not empty message}">
                <div class="alert alert-success">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <sitemesh:write property='body'/>
        </main>
    </div>

    <jsp:include page="/commons/admin/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>