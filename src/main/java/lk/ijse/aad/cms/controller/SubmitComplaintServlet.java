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
import java.util.ArrayList;

@WebServlet("/submit-complaint")
public class SubmitComplaintServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("errorMessage", "Please sign in to submit a complaint...");
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            // Save the new complaint
            ComplaintDTO complaint = new ComplaintDTO();
            complaint.setTitle(title);
            complaint.setDescription(description);
            complaint.setUserId(user.getId());
            complaint.setStatus("PENDING");
            complaintModel.saveComplaint(complaint);

            // Fetch updated complaints for the user
            ArrayList<ComplaintDTO> complaints = complaintModel.getComplaintsByUserId(user.getId());
            request.setAttribute("allComplaints", complaints);

            // Set success message
            session.setAttribute("successMessage", "Complaint submitted successfully...!");

            // Redirect to the complaint servlet, not directly to the JSP
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");

        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pages/employee-complaint.jsp");
        }
    }

}
