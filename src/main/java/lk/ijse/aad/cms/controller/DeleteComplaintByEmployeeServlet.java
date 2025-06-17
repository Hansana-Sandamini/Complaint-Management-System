package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aad.cms.dto.ComplaintDTO;
import lk.ijse.aad.cms.model.ComplaintModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-employee-complaint")
public class DeleteComplaintByEmployeeServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get complaint ID from request
        String complaintIdStr = request.getParameter("complaintId");
        int complaintId;

        try {
            complaintId = Integer.parseInt(complaintIdStr);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid complaint ID...");
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
            return;
        }

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            // Check if complaint exists and is in PENDING status
            ComplaintDTO complaint = complaintModel.getComplaintById(complaintId);
            if (complaint == null) {
                request.getSession().setAttribute("errorMessage", "Complaint not found...");
            } else if (!"PENDING".equals(complaint.getStatus())) {
                request.getSession().setAttribute("errorMessage", "Only pending complaints can be deleted...");
            } else {
                // Delete the complaint
                boolean isDeleted = complaintModel.deleteComplaint(complaintId);
                if (isDeleted) {
                    request.getSession().setAttribute("successMessage", "Complaint deleted successfully...!");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to delete complaint...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Database error occurred while deleting complaint...");
        }

        // Redirect back to the dashboard
        response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
    }

}
