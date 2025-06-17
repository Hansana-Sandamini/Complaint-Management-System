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
import lk.ijse.aad.cms.model.UserModel;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/complaint")
public class ComplaintManagementServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/signin.jsp");
            return;
        }

        ComplaintModel complaintModel = new ComplaintModel();
        complaintModel.setDataSource(dataSource);

        try {
            ArrayList<ComplaintDTO> complaints;

            if ("ADMIN".equals(user.getRole())) {
                // Load all Complaints in the system
                complaints = complaintModel.getAllComplaints();
                request.setAttribute("allComplaints", complaints);

                // Load all Employees in the system
                UserModel userModel = new UserModel();
                userModel.setDataSource(dataSource);
                ArrayList<UserDTO> employees = userModel.getAllEmployees();
                request.setAttribute("allEmployees", employees);

                request.getRequestDispatcher("/pages/admin-dashboard.jsp").forward(request, response);

            } else if ("EMPLOYEE".equals(user.getRole())){
                // Load own submitted Complaints
                complaints = complaintModel.getComplaintsByUserId(user.getId());
                request.setAttribute("allComplaints", complaints);

                request.getRequestDispatcher("/pages/employee-dashboard.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
