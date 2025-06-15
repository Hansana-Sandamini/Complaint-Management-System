package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.aad.cms.dto.UserDTO;
import lk.ijse.aad.cms.model.UserModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserModel userModel = new UserModel();
        userModel.setDataSource(dataSource);

        try {
            UserDTO user = userModel.authenticateUser(email, password);
            if (user != null) {
                // Create session and store user
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Set success message
                request.getSession().setAttribute("successMessage", "Sign in successful! Moving to Dashboard...");

                // Redirect based on role
                if ("ADMIN".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/complaint"); // forward to admin logic
//                    response.sendRedirect(request.getContextPath() + "/pages/admin-dashboard.jsp");
                } else if ("EMPLOYEE".equals(user.getRole())){
                    response.sendRedirect(request.getContextPath() + "/complaint"); // forward to admin logic
//                    response.sendRedirect(request.getContextPath() + "/pages/employee-dashboard.jsp");
                }
            } else {
                // Set error message
                request.getSession().setAttribute("errorMessage", "Invalid email or password");
                response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            }
        } catch (SQLException e) {
            // Set error message for database error
            request.getSession().setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
        }
    }

}
