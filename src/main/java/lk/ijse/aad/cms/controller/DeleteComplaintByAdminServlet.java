package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.aad.cms.model.ComplaintModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-complaint")
public class DeleteComplaintByAdminServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int complaintId = Integer.parseInt(request.getParameter("id"));

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            boolean isDeleted = complaintModel.deleteComplaint(complaintId);

            if (isDeleted) {
                session.setAttribute("successMessage", "Complaint deleted successfully...!");
            } else {
                session.setAttribute("errorMessage", "Failed to delete complaint...");
            }

            // Redirect to complaint management servlet
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");

        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Database error occurred.");
            e.printStackTrace();
        }
    }

}
