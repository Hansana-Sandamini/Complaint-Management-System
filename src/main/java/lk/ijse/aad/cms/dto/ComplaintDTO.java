package lk.ijse.aad.cms.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
