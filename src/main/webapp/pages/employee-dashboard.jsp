<%--
  Created by IntelliJ IDEA.
  User: hansana
  Date: 6/13/25
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="lk.ijse.aad.cms.dto.ComplaintDTO" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Employee</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<!-- Top Bar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3">CMS</a>
        <div class="d-flex">
            <a href="${pageContext.request.contextPath}/pages/profile.jsp"
               class="btn btn-custom text-white" style="margin-right: 10px">My Profile</a>
            <form action="${pageContext.request.contextPath}/index.jsp" method="post">
                <button type="submit" class="btn btn-custom text-white">Logout</button>
            </form>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mt-5 hero-section">
    <div class="text-center mb-4">
        <h2 class="display-4 fw-bold mb-4">Employee Dashboard</h2>
        <p class="text-white lead mb-4">Manage your complaints and submissions</p>
    </div>

    <div class="dashboard-actions">
        <button type="button" class="btn btn-custom text-white btn-lg" data-bs-toggle="modal" data-bs-target="#newComplaintModal">
            New Complaint
        </button>

        <button type="button" class="btn btn-custom text-white btn-lg" data-bs-toggle="modal" data-bs-target="#viewComplaintsModal">
            View My Complaints
        </button>
    </div>
</div>

<!-- New Complaint Modal -->
<div class="modal fade" id="newComplaintModal" tabindex="-1" aria-labelledby="newComplaintModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="newComplaintModalLabel">Submit New Complaint</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <jsp:include page="/pages/employee-complaint.jsp"/>
            </div>
        </div>
    </div>
</div>

<!-- View Complaints Modal -->
<div class="modal fade" id="viewComplaintsModal" tabindex="-1" aria-labelledby="viewComplaintsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewComplaintsModalLabel">My Complaints</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table class="table complaint-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Date Submitted</th>
                            <th>Status</th>
                            <th>Remarks</th>
                            <th>Date Updated</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            ArrayList<ComplaintDTO> complaintDTOS = (ArrayList<ComplaintDTO>) request.getAttribute("allComplaints");
                            if (complaintDTOS == null || complaintDTOS.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="8" class="text-center text-white">No complaints found.</td>
                        </tr>
                        <%
                        } else {
                            for (ComplaintDTO complaintDTO : complaintDTOS) {
                        %>
                        <tr>
                            <td><%= complaintDTO.getId() %></td>
                            <td><%= complaintDTO.getTitle() %></td>
                            <td><%= complaintDTO.getDescription() %></td>
                            <td><%= complaintDTO.getCreatedAt() %></td>
                            <td>
                                <span class="status-badge <%= complaintDTO.getStatus().toLowerCase().replace("_", "-") %>">
                                    <%= complaintDTO.getStatus() %>
                                </span>
                            </td>
                            <td><%= complaintDTO.getRemarks() != null ? complaintDTO.getRemarks() : "N/A" %></td>
                            <td><%= complaintDTO.getUpdatedAt() %></td>
                            <td>
                                <% if ("PENDING".equals(complaintDTO.getStatus())) { %>
                                <button class="btn btn-sm btn-secondary edit-btn"
                                        data-complaint-id="<%= complaintDTO.getId() %>"
                                        data-complaint-title="<%= complaintDTO.getTitle() %>"
                                        data-complaint-description="<%= complaintDTO.getDescription() %>"
                                        data-bs-toggle="modal"
                                        data-bs-target="#editComplaintModal">Edit</button>
                                <button class="btn btn-sm btn-danger delete-btn"
                                        data-complaint-id="<%= complaintDTO.getId() %>">Delete</button>
                                <% } %>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Edit Complaint Modal -->
<div class="modal fade" id="editComplaintModal" tabindex="-1" aria-labelledby="editComplaintModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editComplaintModalLabel">Edit Complaint</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="edit-complaint-form" action="${pageContext.request.contextPath}/update-complaint" method="POST">
                    <input type="hidden" id="edit-complaint-id" name="id">
                    <div class="mb-3">
                        <label for="edit-title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="edit-title" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="edit-description" class="form-label">Description</label>
                        <textarea class="form-control" id="edit-description" name="description" rows="8" required></textarea>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-custom text-white btn-lg">Update Complaint</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="text-white text-center">
    <div class="container">
        <p class="mb-0">&copy; 2025 Complaint Management System. All rights reserved.</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const successMessage = '<%= session.getAttribute("successMessage") != null ? session.getAttribute("successMessage") : "" %>';
        const errorMessage = '<%= session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage") : "" %>';

        // Only show SweetAlert and redirect if the page was loaded from a form submission
        if (successMessage && window.location.search.includes('fromSubmission=true')) {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: successMessage,
                confirmButtonColor: '#764ba2',
            }).then(() => {
                // Redirect to clean URL to avoid re-triggering
                window.location.href = '${pageContext.request.contextPath}/complaint';
            });
        }

        if (errorMessage && window.location.search.includes('fromSubmission=true')) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: errorMessage,
                confirmButtonColor: '#764ba2',
            }).then(() => {
                // Redirect to clean URL to avoid re-triggering
                window.location.href = '${pageContext.request.contextPath}/complaint';
            });
        }
    });
</script>

<%
    // Clear session messages after they are used
    session.removeAttribute("successMessage");
    session.removeAttribute("errorMessage");
%>

</body>
</html>