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
}
