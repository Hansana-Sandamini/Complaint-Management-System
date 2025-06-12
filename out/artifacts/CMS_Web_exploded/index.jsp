<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Complaint Management System</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
        crossorigin="anonymous">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<!-- Top Bar -->
<nav class="navbar navbar-expand-lg navbar-dark">
  <div class="container">
    <a class="navbar-brand fw-bold fs-3" href="${pageContext.request.contextPath}/index.jsp">CMS</a>
    <div class="d-flex">
      <a href="${pageContext.request.contextPath}/pages/signin.jsp"
         class="btn btn-custom text-white" style="margin-right: 10px;">Sign In</a>
      <a href="${pageContext.request.contextPath}/pages/signup.jsp"
         class="btn btn-custom text-white">Sign Up</a>
    </div>
  </div>
</nav>

<!-- Main Content -->
<main class="main-content">
  <!-- Hero Section -->
  <div class="container">
    <div class="hero-section p-5 text-center">
      <h1 class="display-4 fw-bold mb-4">Complaint Management System</h1>
      <p class="lead mb-4">Efficiently manage and resolve customer complaints with our powerful platform</p>
      <div class="d-grid gap-3 d-sm-flex justify-content-sm-center">
        <a href="${pageContext.request.contextPath}/pages/signup.jsp"
           class="btn btn-light btn-lg px-4">Get Started</a>
        <a href="${pageContext.request.contextPath}/pages/signin.jsp"
           class="btn btn-outline-light btn-lg px-4">Sign In</a>
      </div>
    </div>
  </div>

  <!-- Features Section -->
  <div class="container">
    <div class="feature-container">
      <div class="row g-4 justify-content-center">
        <div class="col-lg-5 col-md-6">
          <div class="feature-card card p-4 text-center">
            <div class="feature-icon">
              <i class="bi bi-speedometer2"></i>
            </div>
            <h3 class="mb-3">Quick Resolution</h3>
            <p class="mb-0">Streamline your complaint resolution process with our efficient workflow system.</p>
          </div>
        </div>
        <div class="col-lg-5 col-md-6">
          <div class="feature-card card p-4 text-center">
            <div class="feature-icon">
              <i class="bi bi-bar-chart"></i>
            </div>
            <h3 class="mb-3">Real-time Analytics</h3>
            <p class="mb-0">Gain valuable insights with our comprehensive reporting and analytics dashboard.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>

<!-- Footer -->
<footer class="text-white text-center">
  <div class="container">
    <p class="mb-0">&copy; 2025 Complaint Management System. All rights reserved.</p>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.js"></script>

</body>
</html>