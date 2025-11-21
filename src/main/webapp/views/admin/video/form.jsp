<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="isEdit" value="${not empty video}"/>
<head><title>${isEdit ? 'Update Video' : 'Create Video'}</title></head>
<body>
    <div class="card shadow-sm">
        <div class="card-header bg-${isEdit ? 'warning' : 'primary'} text-white">
            <h4 class="mb-0">${isEdit ? 'Update Video' : 'Create New Video'}</h4>
        </div>
        <div class="card-body">
            <form action="<c:url value='${isEdit ? "/admin/video/update" : "/admin/video/create"}'/>" method="post" enctype="multipart/form-data">
                
                <c:if test="${isEdit}">
                    <input type="hidden" name="videoId" value="${video.videoId}">
                </c:if>

                <div class="row">
                    <div class="col-md-8">
                         <c:if test="${!isEdit}">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Video ID (Youtube ID) <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="videoId" required placeholder="e.g., dQw4w9WgXcQ">
                            </div>
                        </c:if>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Title <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" name="title" value="${video.title}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Category <span class="text-danger">*</span></label>
                            <select class="form-select" name="categoryId" required>
                                <option value="">-- Select Category --</option>
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.categoryId}" ${isEdit && video.category.categoryId == cat.categoryId ? 'selected' : ''}>
                                        ${cat.categoryname}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Description</label>
                            <textarea class="form-control" name="description" rows="5">${video.description}</textarea>
                        </div>
                    </div>

                    <div class="col-md-4 border-start">
                         <div class="mb-3 text-center">
                            <label class="form-label fw-bold d-block">Poster Image</label>
                            <img src="<c:url value='/${not empty video.poster ? video.poster : "templates/images/no-video.png"}'/>" 
                                 class="img-thumbnail mb-2" style="max-height: 200px; width: auto;">
                            <input type="file" class="form-control form-control-sm" name="posterFile" accept="image/*">
                            <input type="hidden" name="oldPoster" value="${video.poster}">
                        </div>

                        <c:if test="${isEdit}">
                             <div class="mb-3 form-check form-switch ps-5">
                                <input class="form-check-input" type="checkbox" id="activeSwitch" name="active" value="true" ${video.active ? 'checked' : ''}>
                                <label class="form-check-label fw-bold" for="activeSwitch">Is Active?</label>
                            </div>
                            <div class="mb-3">
                                <label class="form-label fw-bold">Views: </label> <span>${video.views}</span>
                            </div>
                        </c:if>
                    </div>
                </div>

                <div class="d-flex justify-content-end border-top pt-3 mt-3">
                    <a href="<c:url value='/admin/video/list'/>" class="btn btn-secondary me-2">Cancel</a>
                    <button type="submit" class="btn btn-${isEdit ? 'warning' : 'primary'}">
                        <i class="bi bi-save"></i> ${isEdit ? 'Update' : 'Create'}
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>