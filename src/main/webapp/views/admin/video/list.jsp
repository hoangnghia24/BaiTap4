<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Video Management</title></head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Videos</h2>
        <a href="<c:url value='/admin/video/create'/>" class="btn btn-primary"><i class="bi bi-plus-lg"></i> Add New</a>
    </div>

    <div class="card mb-4">
        <div class="card-body">
            <form action="<c:url value='/admin/video/search'/>" method="get" class="row g-3">
                <div class="col-md-10">
                    <input type="text" class="form-control" name="keyword" value="${currentKeyword}" placeholder="Search by title or description...">
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-outline-primary w-100"><i class="bi bi-search"></i> Search</button>
                </div>
            </form>
        </div>
    </div>
    
    <div class="card shadow-sm">
         <div class="card-body p-0">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>Poster</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Views</th>
                        <th>Active</th>
                        <th class="text-center" style="width: 150px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${videos}" var="item">
                        <tr>
                            <td>
                                <img src="<c:url value='/${not empty item.poster ? item.poster : "templates/images/no-video.png"}'/>" 
                                     alt="poster" style="width: 60px; height: 45px; object-fit: cover;" class="rounded">
                            </td>
                            <td class="fw-bold">${item.title}</td>
                            <td><span class="badge bg-info text-dark">${item.category.categoryname}</span></td>
                            <td>${item.views}</td>
                             <td>
                                <c:choose>
                                    <c:when test="${item.active}"><i class="bi bi-check-circle-fill text-success fs-5"></i></c:when>
                                    <c:otherwise><i class="bi bi-x-circle-fill text-danger fs-5"></i></c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <a href="<c:url value='/admin/video/update?id=${item.videoId}'/>" class="btn btn-sm btn-warning me-2"><i class="bi bi-pencil"></i></a>
                                <a href="<c:url value='/admin/video/delete?id=${item.videoId}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Delete this video?');"><i class="bi bi-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>