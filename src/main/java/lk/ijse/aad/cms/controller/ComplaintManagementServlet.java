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
                complaints = complaintModel.getAllComplaints();
                System.out.println(complaints.toString());
                request.setAttribute("allComplaints", complaints);
                request.getRequestDispatcher("/pages/admin-dashboard.jsp").forward(request, response);

            } else if ("EMPLOYEE".equals(user.getRole())){
                complaints = complaintModel.getComplaintsByUserId(user.getId());
                System.out.println(complaints.toString());
                request.setAttribute("allComplaints", complaints);
                request.getRequestDispatcher("/pages/employee-dashboard.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

}
