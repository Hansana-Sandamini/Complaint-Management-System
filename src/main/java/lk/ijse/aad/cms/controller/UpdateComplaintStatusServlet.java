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

@WebServlet("/update-complaint-status")
public class UpdateComplaintStatusServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");

        // Check if user is logged in and is an admin
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            return;
        }

        int complaintId = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        String remarks = request.getParameter("remarks");

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            ComplaintDTO complaint = new ComplaintDTO();
            complaint.setId(complaintId);
            complaint.setStatus(status);
            complaint.setRemarks(remarks);

            if (complaintModel.updateComplaintStatus(complaint)) {
                request.setAttribute("successMessage", "Complaint updated successfully...!");
            } else {
                request.setAttribute("errorMessage", "Failed to update complaint...");
            }

            // Redirect to complaint management servlet
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }

}
