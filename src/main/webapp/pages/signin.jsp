<%--
  Created by IntelliJ IDEA.
  User: hansana
  Date: 6/12/25
  Time: 12:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
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
                    <h2 style="color: #764ba2; font-weight: 700;">Welcome Back</h2>
                    <p class="text-muted">Sign in to continue to your account</p>
                </div>

                <form id="sign-in-form" action="{pageContext.request.contextPath}/signup" method="POST" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="loginEmail" class="form-label fw-medium">Email Address</label>
                        <input type="email" class="form-control" id="loginEmail" name="email" placeholder="example@gmail.com" required>
                    </div>

                    <div class="mb-3">
                        <label for="loginPassword" class="form-label fw-medium">Password</label>
                        <input type="password" class="form-control" id="loginPassword" name="password" placeholder="••••••••" required>
                        <div class="text-end mt-2">
                            <a href="#" class="forgot-password">Forgot password?</a>
                        </div>
                    </div>

                    <div class="form-check mb-4">
                        <input class="form-check-input" type="checkbox" id="rememberMe">
                        <label class="form-check-label" for="rememberMe">Remember me</label>
                    </div>

                    <button type="submit" id="sign-in-btn" class="btn btn-custom text-white w-100 py-2 mb-3">
                        <span class="fw-bold">Sign In</span>
                    </button>

                    <div class="text-center">
                        <p class="text-muted">Don't have an account?
                            <a href="${pageContext.request.contextPath}/pages/signup.jsp"
                               style="color: #764ba2; text-decoration: none; font-weight: bold">Sign Up
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
