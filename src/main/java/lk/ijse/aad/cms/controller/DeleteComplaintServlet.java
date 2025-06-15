package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aad.cms.model.ComplaintModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-complaint")
public class DeleteComplaintServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int complaintId = Integer.parseInt(request.getParameter("id"));

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            boolean isDeleted = complaintModel.deleteComplaint(complaintId);

            if (isDeleted) {
                request.setAttribute("successMessage", "Complaint deleted successfully...!");
            } else {
                request.setAttribute("errorMessage", "Failed to delete complaint...");
            }

            // Redirect to complaint management servlet
            response.sendRedirect(request.getContextPath() + "/complaint?fromSubmission=true");

        } catch (SQLException e) {
            request.getSession().setAttribute("errorMessage", "Database error occurred.");
            e.printStackTrace();
        }
    }

}
