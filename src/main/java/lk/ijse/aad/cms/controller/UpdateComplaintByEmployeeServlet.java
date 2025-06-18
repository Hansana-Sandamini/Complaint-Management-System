package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.aad.cms.dto.ComplaintDTO;
import lk.ijse.aad.cms.dto.UserDTO;
import lk.ijse.aad.cms.model.ComplaintModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/update-complaint")
public class UpdateComplaintByEmployeeServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (session == null || user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            return;
        }

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        if (id == null || title == null || description == null || id.trim().isEmpty() || title.trim().isEmpty() || description.trim().isEmpty()) {
            session.setAttribute("errorMessage", "All fields are required...");
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
            return;
        }

        int complaintId;
        try {
            complaintId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid complaint ID format...");
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
            return;
        }

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            ComplaintDTO existingComplaint = complaintModel.getComplaintById(complaintId);
            if (existingComplaint == null || existingComplaint.getStatus() == null || !existingComplaint.getStatus().trim().equalsIgnoreCase("PENDING")) {
                session.setAttribute("errorMessage", "Complaint not found, status is null, or not in PENDING status...");
                response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
                return;
            }

            if (!Objects.equals(String.valueOf(existingComplaint.getUserId()), String.valueOf(user.getId()))) {
                session.setAttribute("errorMessage", "You are not authorized to edit this complaint...");
                response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");
                return;
            }

            ComplaintDTO complaintDTO = new ComplaintDTO();
            complaintDTO.setId(complaintId);
            complaintDTO.setTitle(title);
            complaintDTO.setDescription(description);
            complaintDTO.setUserId(user.getId());

            boolean isUpdated = complaintModel.updateComplaint(complaintDTO);
            if (isUpdated) {
                session.setAttribute("successMessage", "Complaint updated successfully...!");
            } else {
                session.setAttribute("errorMessage", "Failed to update complaint...");
            }

        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");

    }

}
