package lk.ijse.aad.cms.model;

import jakarta.annotation.Resource;
import lk.ijse.aad.cms.dto.ComplaintDTO;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComplaintModel {

    @Getter
    @Setter
    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    public ArrayList<ComplaintDTO> getAllComplaints() throws SQLException {
        ArrayList<ComplaintDTO> complaints = new ArrayList<>();
        String sql = "SELECT c.*, u.name as user_name FROM complaint c JOIN user u ON c.user_id = u.id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rst = pstm.executeQuery()) {

            while (rst.next()) {
                complaints.add(new ComplaintDTO(
                        rst.getInt("id"),
                        rst.getString("title"),
                        rst.getString("description"),
                        rst.getString("status"),
                        rst.getString("remarks"),
                        rst.getInt("user_id"),
                        rst.getString("user_name"),
                        rst.getTimestamp("created_at"),
                        rst.getTimestamp("updated_at")
                ));
            }
        }
        return complaints;
    }

    public ArrayList<ComplaintDTO> getComplaintsByUserId(int userId) throws SQLException {
        ArrayList<ComplaintDTO> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaint WHERE user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, userId);
            try (ResultSet rst = pstm.executeQuery()) {
                while (rst.next()) {
                    complaints.add(new ComplaintDTO(
                            rst.getInt("id"),
                            rst.getString("title"),
                            rst.getString("description"),
                            rst.getString("status"),
                            rst.getString("remarks"),
                            rst.getInt("user_id"),
                            null, // user_name not needed here
                            rst.getTimestamp("created_at"),
                            rst.getTimestamp("updated_at")
                    ));
                }
            }
        }
        return complaints;
    }

    public boolean saveComplaint(ComplaintDTO complaintDTO) throws SQLException {
        String sql = "INSERT INTO complaint (title, description, user_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, complaintDTO.getTitle());
            pstm.setString(2, complaintDTO.getDescription());
            pstm.setInt(3, complaintDTO.getUserId());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean updateComplaint(ComplaintDTO complaintDTO) throws SQLException {
        String sql = "UPDATE complaint SET title = ?, description = ? WHERE id = ? AND status = 'PENDING'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, complaintDTO.getTitle());
            pstm.setString(2, complaintDTO.getDescription());
            pstm.setInt(3, complaintDTO.getId());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean updateComplaintStatus(ComplaintDTO complaintDTO) throws SQLException {
        String sql = "UPDATE complaint SET status = ?, remarks = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, complaintDTO.getStatus());
            pstm.setString(2, complaintDTO.getRemarks());
            pstm.setInt(3, complaintDTO.getId());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean deleteComplaint(int complaintId) throws SQLException {
        String sql = "DELETE FROM complaint WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, complaintId);
            return pstm.executeUpdate() > 0;
        }
    }

    public ComplaintDTO getComplaintById(int complaintId) throws SQLException {
        String sql = "SELECT c.*, u.name as user_name FROM complaint c JOIN user u ON c.user_id = u.id WHERE c.id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, complaintId);
            try (ResultSet rst = pstm.executeQuery()) {
                if (rst.next()) {
                    return new ComplaintDTO(
                            rst.getInt("id"),
                            rst.getString("title"),
                            rst.getString("description"),
                            rst.getString("status"),
                            rst.getString("remarks"),
                            rst.getInt("user_id"),
                            rst.getString("user_name"),
                            rst.getTimestamp("created_at"),
                            rst.getTimestamp("updated_at")
                    );
                }
                return null;
            }
        }
    }

    public int getTotalComplaintCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM complaint";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int getResolvedComplaintCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM complaint WHERE status = 'RESOLVED'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int getPendingComplaintCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM complaint WHERE status = 'PENDING'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int getTotalComplaintCountByUserId(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM complaint WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    public int getResolvedComplaintCountByUserId(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM complaint WHERE user_id = ? AND status = 'RESOLVED'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    public int getPendingComplaintCountByUserId(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM complaint WHERE user_id = ? AND status = 'PENDING'";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

}
