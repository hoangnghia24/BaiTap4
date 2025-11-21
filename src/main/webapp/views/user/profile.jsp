<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head>
    <title>My Profile</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Update Profile</h4>
                </div>
                <div class="card-body">
                    <form action="<c:url value='/user/profile'/>" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="username" value="${user.username}">
                        
                        <div class="row mb-4">
                            <div class="col-md-4 text-center">
                                <img src="<c:url value='/${not empty user.images ? user.images : "templates/images/default-avatar.png"}'/>" 
                                     alt="Avatar" class="img-thumbnail rounded-circle mb-2" style="width: 150px; height: 150px; object-fit: cover;">
                                <div class="mb-3">
                                    <label for="imageFile" class="form-label fw-bold">Change Avatar</label>
                                    <input class="form-control form-control-sm" type="file" id="imageFile" name="imageFile" accept="image/*">
                                    <input type="hidden" name="oldImage" value="${user.images}">
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Username</label>
                                    <input type="text" class="form-control" value="${user.username}" disabled readonly>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Email</label>
                                    <input type="email" class="form-control" value="${user.email}" disabled readonly>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Full Name</label>
                                    <input type="text" class="form-control" name="fullname" value="${user.fullname}" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Phone</label>
                                    <input type="text" class="form-control" name="phone" value="${user.phone}">
                                </div>
                            </div>
                        </div>
                        <div class="text-end">
                            <button type="submit" class="btn btn-success"><i class="bi bi-save"></i> Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>