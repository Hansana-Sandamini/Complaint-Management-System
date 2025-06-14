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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<!-- Complaint Form -->
<div class="form-container">
    <div class="complaint-container">

        <form id="complaint-form" action="${pageContext.request.contextPath}/submit-complaint" method="POST">
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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        <%--// Check for success message--%>
        <%--<% if (request.getAttribute("successMessage") != null) { %>--%>
        <%--Swal.fire({--%>
        <%--    icon: 'success',--%>
        <%--    title: 'Success!',--%>
        <%--    text: '<%= request.getAttribute("successMessage") %>',--%>
        <%--    confirmButtonColor: '#764ba2'--%>
        <%--}).then(() => {--%>
        <%--    window.location.href = '${pageContext.request.contextPath}/pages/employee-dashboard.jsp';--%>
        <%--});--%>
        <%--<% } %>--%>

        <%--// Check for error message--%>
        <%--<% if (request.getAttribute("errorMessage") != null) { %>--%>
        <%--Swal.fire({--%>
        <%--    icon: 'error',--%>
        <%--    title: 'Error!',--%>
        <%--    text: '<%= request.getAttribute("errorMessage") %>',--%>
        <%--    confirmButtonColor: '#764ba2'--%>
        <%--});--%>
        <%--<% } %>--%>

        // Handle success/error messages
        const successMessage = '<%= session.getAttribute("successMessage") != null ? session.getAttribute("successMessage") : "" %>';
        const errorMessage = '<%= session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage") : "" %>';

        if (successMessage) {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: successMessage,
                confirmButtonColor: '#764ba2',
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/pages/employee-dashboard.jsp';
            });
        }

        if (errorMessage) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: errorMessage,
                confirmButtonColor: '#764ba2',
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/pages/employee-dashboard.jsp';
            });
        }
    });
</script>

<%
    // Clear session messages after they are used in JavaScript
    session.removeAttribute("successMessage");
    session.removeAttribute("errorMessage");
%>

</body>
</html>