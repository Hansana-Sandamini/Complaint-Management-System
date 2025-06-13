package lk.ijse.aad.cms.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {
    private int id;
    private String title;
    private String description;
    private String status;
    private String remarks;
    private int userId;
    private String userName;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ComplaintDTO(int id, String title, String description, String status, String remarks, int userId,
                        Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.remarks = remarks;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
