<%--
  Created by IntelliJ IDEA.
  User: hansana
  Date: 6/11/25
  Time: 11:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/authentication.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body style="min-height: 100vh; display: flex; align-items: center;">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card p-4">
                <div class="text-center mb-4">
                    <h2 style="color: #764ba2; font-weight: 700;">Create Your Account</h2>
                    <p class="text-muted">Join us today and get started</p>
                </div>

                <form id="sign-up-form" action="${pageContext.request.contextPath}/signup" method="POST" enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="name" class="form-label fw-medium">Full Name</label>
                            <input type="text" class="form-control" id="name" name="name" placeholder="John Doe" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="email" class="form-label fw-medium">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="example@gmail.com" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="mobile" class="form-label fw-medium">Mobile Number</label>
                            <input type="tel" class="form-control" id="mobile" name="mobile" placeholder="0771234567" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-medium">User Role</label>
                            <div class="role-selector d-flex gap-2">
                                <input type="radio" class="btn-check" name="role" id="employee" value="EMPLOYEE" checked>
                                <label class="btn btn-outline-primary flex-grow-1" for="employee">Employee</label>

                                <input type="radio" class="btn-check" name="role" id="admin" value="ADMIN">
                                <label class="btn btn-outline-primary flex-grow-1" for="admin">Admin</label>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="image" class="form-label fw-medium">Profile Image (Optional)</label>
                        <input type="file" class="form-control" id="image" name="image" accept="image/*">
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="password" class="form-label fw-medium">Password</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="••••••••" required>
                        </div>
                        <div class="col-md-6 mb-4">
                            <label for="confirmPassword" class="form-label fw-medium">Confirm Password</label>
                            <input type="password" class="form-control" id="confirmPassword" placeholder="••••••••" required>
                        </div>
                    </div>

                    <button type="submit" id="sign-up-btn" class="btn btn-custom text-white w-100 py-2 mb-3">
                        <span class="fw-bold">Sign Up</span>
                    </button>

                    <div class="text-center">
                        <p class="text-muted">Already have an account?
                            <a href="${pageContext.request.contextPath}/pages/signin.jsp"
                               style="color: #764ba2; text-decoration: none; font-weight: bold">Sign In
                            </a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous">
</script>

</body>
</html>