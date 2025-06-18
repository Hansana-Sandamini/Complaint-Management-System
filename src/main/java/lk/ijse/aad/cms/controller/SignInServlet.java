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
        HttpSession session = request.getSession(true);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserModel userModel = new UserModel();
        userModel.setDataSource(dataSource);

        try {
            UserDTO user = userModel.authenticateUser(email, password);
            if (user != null) {
                session.setAttribute("user", user);

                // Set success message
                session.setAttribute("successMessage", "Sign in successful! Moving to Dashboard...");

                // Redirect based on role
                if ("ADMIN".equals(user.getRole()) || "EMPLOYEE".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/complaint?fromSignIn=true");
                }

            } else {
                // Set error message
                session.setAttribute("errorMessage", "Invalid email or password");
                response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            }
        } catch (SQLException e) {
            // Set error message for database error
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
        }
    }

}
