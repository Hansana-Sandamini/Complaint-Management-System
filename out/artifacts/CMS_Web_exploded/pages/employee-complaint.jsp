<%--
  Created by IntelliJ IDEA.
  User: hansana
  Date: 6/12/25
  Time: 11:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit Complaint - CMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/complaint.css">
</head>
<body>
<!-- Top Bar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3" href="${pageContext.request.contextPath}/index.jsp">CMS</a>
        <div class="d-flex">
            <a href="${pageContext.request.contextPath}/pages/employee-dashboard.jsp"
               class="btn btn-custom text-white" style="margin-right: 10px">Dashboard</a>
            <form action="${pageContext.request.contextPath}/index.jsp" method="post">
                <button type="submit" class="btn btn-custom text-white">Logout</button>
            </form>
        </div>
    </div>
</nav>

<!-- Complaint Form -->
<div class="form-container">
    <div class="complaint-container">
        <h2 class="text-center mb-4">Submit New Complaint</h2>

        <form id="complaint-form" action="${pageContext.request.contextPath}/submit-complaint" method="post">
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="8" required></textarea>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-custom text-white btn-lg">Submit Complaint</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous">
</script>

</body>
</html>