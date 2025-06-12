package lk.ijse.aad.cms.model;

import jakarta.annotation.Resource;
import lk.ijse.aad.cms.dto.UserDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    public ArrayList<UserDTO> getAllUsers() throws SQLException {
        ArrayList<UserDTO> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rst = pstm.executeQuery()) {

            while (rst.next()) {
                users.add(new UserDTO(
                        rst.getString("id"),
                        rst.getString("email"),
                        rst.getString("password"),
                        rst.getString("name"),
                        rst.getString("role"),
                        rst.getString("mobile"),
                        rst.getString("image")
                ));
            }
        }
        return users;
    }

    public boolean saveUser(UserDTO userDTO) throws SQLException {
        String sql = "INSERT INTO user (email, password, name, role, mobile, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, userDTO.getEmail());
            pstm.setString(2, userDTO.getPassword());
            pstm.setString(3, userDTO.getName());
            pstm.setString(4, userDTO.getRole());
            pstm.setString(5, userDTO.getMobile());
            pstm.setString(6, userDTO.getImage());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean updateUser(UserDTO userDTO) throws SQLException {
        String sql = "UPDATE user SET email = ?, password = ?, name = ?, role = ?, mobile = ?, image = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, userDTO.getEmail());
            pstm.setString(2, userDTO.getPassword());
            pstm.setString(3, userDTO.getName());
            pstm.setString(4, userDTO.getRole());
            pstm.setString(5, userDTO.getMobile());
            pstm.setString(6, userDTO.getImage());
            pstm.setString(7, userDTO.getId());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(String userId) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, userId);
            return pstm.executeUpdate() > 0;
        }
    }

    public UserDTO authenticateUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, email);
            pstm.setString(2, password);

            try (ResultSet rst = pstm.executeQuery()) {
                if (rst.next()) {
                    return new UserDTO(
                            rst.getString("id"),
                            rst.getString("email"),
                            rst.getString("password"),
                            rst.getString("name"),
                            rst.getString("role"),
                            rst.getString("mobile"),
                            rst.getString("image")
                    );
                }
                return null;
            }
        }
    }

    public boolean isUserEmailExists(String email) throws SQLException {
        String sql = "SELECT id FROM user WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, email);
            try (ResultSet rst = pstm.executeQuery()) {
                return rst.next();
            }
        }
    }

}
