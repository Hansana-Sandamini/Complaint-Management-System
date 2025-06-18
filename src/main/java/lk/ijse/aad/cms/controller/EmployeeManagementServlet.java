package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.aad.cms.model.UserModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-employee")
public class EmployeeManagementServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int id = Integer.parseInt(request.getParameter("id"));

        UserModel userModel = new UserModel();
        userModel.setDataSource(dataSource);

        try {
            boolean isDeleted = userModel.deleteUser(id);

            if (isDeleted) {
                session.setAttribute("successMessage", "Employee deleted successfully...!");
            } else {
                session.setAttribute("errorMessage", "Failed to delete employee...");
            }

            // Redirect to complaint management servlet
            response.sendRedirect(request.getContextPath() + "/pages/admin-dashboard.jsp?fromSubmission=true");

        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Database error occurred.");
            e.printStackTrace();
        }
    }

}
