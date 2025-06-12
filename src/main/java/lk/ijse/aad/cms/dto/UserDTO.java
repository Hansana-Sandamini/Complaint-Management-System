package lk.ijse.aad.cms.dto;

import lombok.*;

@Data
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String password;
    private String name;
    private String role;
    private String mobile;
    private String image;
}
