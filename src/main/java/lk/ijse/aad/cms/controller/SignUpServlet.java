package lk.ijse.aad.cms.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lk.ijse.aad.cms.dto.UserDTO;
import lk.ijse.aad.cms.model.UserModel;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signup")
@MultipartConfig
public class SignUpServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        Part imagePart = request.getPart("image");

        UserModel userModel = new UserModel();
        userModel.setDataSource(dataSource);

        try {
            // Check if email already exists
            if (userModel.isUserEmailExists(email)) {
                session.setAttribute("errorMessage", "Email already exists. Please use a different email...");
                response.sendRedirect(request.getContextPath() + "/pages/signup.jsp");
                return;
            }

            // Validate mobile number length and digits
            if (mobile == null || !mobile.matches("^\\d{10}$")) {
                session.setAttribute("errorMessage", "Mobile number must be exactly 10 digits...");
                response.sendRedirect(request.getContextPath() + "/pages/signup.jsp");
                return;
            }

            // Handle image upload
            String imagePath = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/uploads");
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
                imagePath = "/uploads/" + fileName;
                imagePart.write(uploadDir + File.separator + fileName);
            }

            // Create UserDTO
            UserDTO userDTO = new UserDTO(0, email, password, name, role, mobile, imagePath);

            // Save user
            if (userModel.saveUser(userDTO)) {
                // Forward to signup.jsp -> JavaScript shows SweetAlert
                session.setAttribute("successMessage", "Registration successful! Please sign in...");
                request.getRequestDispatcher("/pages/signup.jsp").forward(request, response);
            } else {
                session.setAttribute("errorMessage", "Registration failed. Please try again...");
                response.sendRedirect(request.getContextPath() + "/pages/signup.jsp");
            }
        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pages/signup.jsp");
        }
    }

}
