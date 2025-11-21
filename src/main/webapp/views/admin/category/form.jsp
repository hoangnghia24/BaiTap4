<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="isEdit" value="${not empty category}"/>
<head>
    <title>${isEdit ? 'Update Category' : 'Create Category'}</title>
</head>
<body>
    <div class="card shadow-sm">
        <div class="card-header bg-${isEdit ? 'warning' : 'primary'} text-white">
            <h4 class="mb-0">${isEdit ? 'Update Category' : 'Create New Category'}</h4>
        </div>
        <div class="card-body">
            <form action="<c:url value='${isEdit ? "/admin/category/update" : "/admin/category/create"}'/>" method="post">
                
                <c:if test="${isEdit}">
                    <input type="hidden" name="categoryId" value="${category.categoryId}">
                    <div class="mb-3">
                        <label class="form-label">Category ID</label>
                        <input type="text" class="form-control" value="${category.categoryId}" disabled>
                    </div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label fw-bold">Category Name <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="categoryname" value="${category.categoryname}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Category Code</label>
                    <input type="text" class="form-control" name="categorycode" value="${category.categorycode}">
                </div>

                 <c:if test="${isEdit}">
                     <div class="mb-3">
                        <label class="form-label fw-bold">Status</label>
                        <select class="form-select" name="status">
                            <option value="1" ${category.status == 1 ? 'selected' : ''}>Active</option>
                            <option value="0" ${category.status == 0 ? 'selected' : ''}>Inactive</option>
                        </select>
                    </div>
                </c:if>
                
                <div class="d-flex justify-content-end">
                    <a href="<c:url value='/admin/category/list'/>" class="btn btn-secondary me-2">Cancel</a>
                    <button type="submit" class="btn btn-${isEdit ? 'warning' : 'primary'}">
                        <i class="bi bi-save"></i> ${isEdit ? 'Update' : 'Create'}
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>