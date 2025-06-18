package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession(false);

        // Get complaint ID from request
        String complaintIdStr = request.getParameter("complaintId");
        int complaintId;

        try {
            complaintId = Integer.parseInt(complaintIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid complaint ID...");
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
            return;
        }

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            // Check if complaint exists and is in PENDING status
            ComplaintDTO complaint = complaintModel.getComplaintById(complaintId);
            if (complaint == null) {
                session.setAttribute("errorMessage", "Complaint not found...");
            } else if (!"PENDING".equals(complaint.getStatus())) {
                session.setAttribute("errorMessage", "Only pending complaints can be deleted...");
            } else {
                // Delete the complaint
                boolean isDeleted = complaintModel.deleteComplaint(complaintId);
                if (isDeleted) {
                    session.setAttribute("successMessage", "Complaint deleted successfully...!");
                } else {
                    session.setAttribute("errorMessage", "Failed to delete complaint...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error occurred while deleting complaint...");
        }

        // Redirect back to the dashboard
        response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
    }

}
