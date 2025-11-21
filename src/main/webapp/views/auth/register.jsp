<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head>
    <title>Register New Account</title>
</head>
<body>
    <h3 class="text-center mb-4">Create an Account</h3>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show mb-4">
            ${error} <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <form action="<c:url value='/register'/>" method="post" class="needs-validation" novalidate>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label fw-bold">Username <span class="text-danger">*</span></label>
                <input type="text" class="form-control" name="username" value="${username}" required autofocus placeholder="e.g., johndoe">
                <div class="invalid-feedback">Please choose a username.</div>
            </div>
            <div class="col-md-6 mb-3">
                 <label class="form-label fw-bold">Full Name <span class="text-danger">*</span></label>
                 <input type="text" class="form-control" name="fullname" value="${fullname}" required placeholder="e.g., John Doe">
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Email Address <span class="text-danger">*</span></label>
            <input type="email" class="form-control" name="email" value="${email}" required placeholder="john@example.com">
            <div class="invalid-feedback">Please enter a valid email address.</div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6 mb-3 mb-md-0">
                <label class="form-label fw-bold">Password <span class="text-danger">*</span></label>
                <input type="password" class="form-control" name="password" required minlength="6">
                <div class="form-text">Must be at least 6 characters long.</div>
            </div>
            <div class="col-md-6">
                <label class="form-label fw-bold">Confirm Password <span class="text-danger">*</span></label>
                <input type="password" class="form-control" name="confirmPassword" required>
            </div>
        </div>
        
        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" id="agreeTerm" required>
            <label class="form-check-label" for="agreeTerm">I agree to the <a href="#">terms and conditions</a></label>
        </div>

        <button type="submit" class="btn btn-success w-100 btn-lg mt-2">
            <i class="bi bi-person-plus-fill me-2"></i> Register Now
        </button>
        
        <div class="text-center mt-4 border-top pt-3">
             <span class="text-muted">Already have an account?</span>
             <a href="<c:url value='/login'/>" class="fw-bold text-decoration-none ms-1">Login here</a>
        </div>
    </form>

    <script>
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms)
                .forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault()
                            event.stopPropagation()
                        }
                        form.classList.add('was-validated')
                    }, false)
                })
        })()
    </script>
</body>