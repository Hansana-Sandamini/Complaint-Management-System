<%--
  Created by IntelliJ IDEA.
  User: hansana
  Date: 6/13/25
  Time: 6:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="lk.ijse.aad.cms.dto.ComplaintDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="lk.ijse.aad.cms.dto.UserDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Admin</title>
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
               class="btn btn-custom text-white me-2">My Profile</a>
            <form action="${pageContext.request.contextPath}/logout" method="post" class="m-0">
                <button type="submit" class="btn btn-custom text-white">Logout</button>
            </form>
        </div>
    </div>
</nav>

<!-- Main Content -->
<div class="container mt-5 hero-section">
    <div class="text-center mb-4">
        <h2 class="display-4 fw-bold mb-4">Admin Dashboard</h2>
        <p class="text-white fw-bold lead mb-4">Manage all complaints in the system</p>
    </div>

    <div class="row mb-4">
        <div class="col-md-3">
            <div class="card bg-dark text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Total Employees</h5>
                    <p class="card-text"><%=(Integer) request.getAttribute("totalEmployees") != null ? request.getAttribute("totalEmployees") : 0 %></p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card bg-dark text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Total Complaints</h5>
                    <p class="card-text"><%=(Integer) request.getAttribute("totalComplaints") != null ? request.getAttribute("totalComplaints") : 0 %></p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card bg-dark text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Resolved Complaints</h5>
                    <p class="card-text"><%=(Integer) request.getAttribute("resolvedComplaints") != null ? request.getAttribute("resolvedComplaints") : 0 %></p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card bg-dark text-white">
                <div class="card-body text-center">
                    <h5 class="card-title">Pending Complaints</h5>
                    <p class="card-text"><%=(Integer) request.getAttribute("pendingComplaints") != null ? request.getAttribute("pendingComplaints") : 0 %></p>
                </div>
            </div>
        </div>
    </div>

    <div class="dashboard-actions">
        <button type="button" class="btn btn-custom text-white btn-lg" data-bs-toggle="modal" data-bs-target="#viewAllComplaintsModal" >
            View All Complaints
        </button>

        <button type="button" class="btn btn-custom text-white btn-lg" data-bs-toggle="modal" data-bs-target="#viewAllEmployeesModal">
            View All Employees
        </button>
    </div>
</div>

<!-- View All Complaints Modal -->
<div class="modal fade" id="viewAllComplaintsModal" tabindex="-1" aria-labelledby="viewAllComplaintsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewAllComplaintsModalLabel">All Complaints</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <!-- All Complaints Table -->
                <div class="table-responsive">
                    <table class="table complaint-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Submitted By</th>
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
                            <td colspan="9" class="text-center text-white">No complaints found.</td>
                        </tr>
                        <%
                        } else {
                            for (ComplaintDTO complaintDTO : complaintDTOS) {
                        %>
                        <tr>
                            <td><%= complaintDTO.getId() %></td>
                            <td><%= complaintDTO.getTitle() %></td>
                            <td><%= complaintDTO.getDescription() %></td>
                            <td><%= complaintDTO.getUserName() %></td>
                            <td><%= complaintDTO.getCreatedAt() %></td>
                            <td>
                                <span class="status-badge <%= complaintDTO.getStatus().toLowerCase().replace("_", "-") %>">
                                    <%= complaintDTO.getStatus() %>
                                </span>
                            </td>
                            <td><%= complaintDTO.getRemarks() != null ? complaintDTO.getRemarks() : "N/A" %></td>
                            <td><%= complaintDTO.getUpdatedAt() %></td>
                            <td>
                                <button class="btn btn-sm btn-secondary update-btn"
                                        data-complaint-id="<%= complaintDTO.getId() %>"
                                        data-bs-toggle="modal"
                                        data-bs-target="#updateComplaintModal">Update</button>

                                <form action="${pageContext.request.contextPath}/delete-complaint" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="<%= complaintDTO.getId() %>">
                                    <button type="submit" class="btn btn-sm btn-danger delete-btn">Delete</button>
                                </form>
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

<!-- Update Complaint Modal -->
<div class="modal fade" id="updateComplaintModal" tabindex="-1" aria-labelledby="updateComplaintModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="updateComplaintModalLabel">Update Complaint Status</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="update-complaint-form" action="${pageContext.request.contextPath}/update-complaint-status" method="POST">
                    <input type="hidden" id="update-complaint-id" name="id">
                    <div class="mb-3">
                        <label for="status" class="form-label">Status</label>
                        <select class="form-select" id="status" name="status" required>
                            <option value="PENDING">Pending</option>
                            <option value="RESOLVED">Resolved</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="remarks" class="form-label">Remarks</label>
                        <textarea class="form-control" id="remarks" name="remarks" rows="3"></textarea>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-custom text-white btn-lg">Update Status</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- View All Employees Modal -->
<div class="modal fade" id="viewAllEmployeesModal" tabindex="-1" aria-labelledby="viewAllEmployeesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewAllEmployeesModalLabel">All Employees</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table class="table complaint-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Mobile</th>
                            <th>Image</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            ArrayList<UserDTO> employeeList = (ArrayList<UserDTO>) request.getAttribute("allEmployees");
                            if (employeeList == null || employeeList.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="5" class="text-center text-white">No employees found.</td>
                        </tr>
                        <%
                        } else {
                            for (UserDTO employee : employeeList) {
                        %>
                        <tr>
                            <td><%= employee.getId() %></td>
                            <td><%= employee.getName() %></td>
                            <td><%= employee.getEmail() %></td>
                            <td><%= employee.getMobile() != null ? employee.getMobile() : "N/A" %></td>
                            <td>
                                <% if (employee.getImage() != null && !employee.getImage().isEmpty()) { %>
                                <img src="${pageContext.request.contextPath}/uploads/<%= employee.getImage() %>" alt="Employee Image" class="img-thumbnail" style="max-width: 100px; max-height: 100px;">
                                <% } else { %>
                                No Image
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
    document.addEventListener('DOMContentLoaded', function () {
        // Get all update buttons
        const updateButtons = document.querySelectorAll('.update-btn');

        updateButtons.forEach(button => {
            button.addEventListener('click', function () {
                // Get the complaint ID from the data attribute
                const complaintId = this.getAttribute('data-complaint-id');
                // Set the complaint ID in the modal's hidden input field
                document.getElementById('update-complaint-id').value = complaintId;
            });
        });

        // Handle delete button clicks
        document.querySelectorAll('.delete-btn').forEach(button => {
            button.addEventListener('click', function () {
                const complaintId = this.getAttribute('data-complaint-id');

                Swal.fire({
                    title: 'Are you sure?',
                    text: "You won't be able to revert this!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#764ba2',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes, delete it!'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Send POST request to delete servlet
                        fetch('${pageContext.request.contextPath}/delete-complaint', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                            },
                            body: 'complaintId=' + encodeURIComponent(complaintId)
                        }).then(response => {
                            if (response.ok) {
                                window.location.href = '${pageContext.request.contextPath}/complaint?fromSubmission=true';
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Failed to delete complaint.',
                                    confirmButtonColor: '#764ba2',
                                });
                            }
                        }).catch(error => {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'An error occurred while deleting the complaint.',
                                confirmButtonColor: '#764ba2',
                            });
                        });
                    }
                });
            });
        });

        // Handle success/error messages
        const successMessage = '<%= session.getAttribute("successMessage") != null ? session.getAttribute("successMessage") : "" %>';
        const errorMessage = '<%= session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage") : "" %>';

        if (successMessage && window.location.search.includes('fromSubmission=true')) {
            Swal.fire({
                icon: 'success',
                title: 'Success',
                text: successMessage,
                confirmButtonColor: '#764ba2',
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/pages/admin-dashboard.jsp';
            });
        }

        if (errorMessage && window.location.search.includes('fromSubmission=true')) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: errorMessage,
                confirmButtonColor: '#764ba2',
            }).then(() => {
                window.location.href = '${pageContext.request.contextPath}/pages/admin-dashboard.jsp';
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
