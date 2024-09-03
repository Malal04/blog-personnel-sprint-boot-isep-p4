package blog.personnel.dm.entity.authentification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistreRequest {

    private Integer id;
    private String nomComplete;
    private String userNom;
    private String email;
    private String password;
    private String configPassword;
}
