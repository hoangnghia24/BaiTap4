<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property='title'>IoTStar Video</sitemesh:write></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <sitemesh:write property='head'/>
    <style>
        body { display: flex; flex-direction: column; min-height: 100vh; }
        main { flex: 1; }
    </style>
</head>
<body>
    <jsp:include page="/commons/web/navbar.jsp" />

    <main class="container py-4">
        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissible fade show">
                ${message} <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <sitemesh:write property='body'/>
    </main>

    <jsp:include page="/commons/web/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>