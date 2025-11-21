<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Category Management</title></head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Categories</h2>
        <a href="<c:url value='/admin/category/create'/>" class="btn btn-primary"><i class="bi bi-plus-lg"></i> Add New</a>
    </div>
    
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-hover table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Code</th>
                        <th>Manager</th>
                        <th>Status</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="item">
                        <tr>
                            <td>${item.categoryId}</td>
                            <td>${item.categoryname}</td>
                            <td>${item.categorycode}</td>
                            <td>${item.manager.username}</td>
                            <td>
                                <span class="badge bg-${item.status == 1 ? 'success' : 'secondary'}">
                                    ${item.status == 1 ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                            <td class="text-center">
                                <a href="<c:url value='/admin/category/update?id=${item.categoryId}'/>" class="btn btn-sm btn-warning me-2"><i class="bi bi-pencil"></i></a>
                                <a href="<c:url value='/admin/category/delete?id=${item.categoryId}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure to delete this category?');"><i class="bi bi-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>