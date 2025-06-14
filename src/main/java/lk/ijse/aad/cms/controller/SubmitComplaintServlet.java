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

@WebServlet("/submit-complaint")
public class SubmitComplaintServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");

        // Check if user is logged in and is an employee
        if (user == null || !"EMPLOYEE".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            ComplaintDTO complaint = new ComplaintDTO(0, title, description, "PENDING", null, user.getId(), null, null, null);

            if (complaintModel.saveComplaint(complaint)) {
                request.getSession().setAttribute("successMessage", "Complaint submitted successfully...!");
                request.getRequestDispatcher("/pages/employee-dashboard.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to submit complaint. Please try again...");
                request.getRequestDispatcher("/pages/employee-complaint.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/employee-complaint.jsp").forward(request, response);
        }
    }

}
