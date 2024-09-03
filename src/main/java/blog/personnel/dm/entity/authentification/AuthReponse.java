package blog.personnel.dm.entity.authentification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthReponse {

    private Integer id;

    private String token;

    private String email;

    private String userNom;

    public  AuthReponse (Auth auth){
        this.token = auth.getToken();
        this.email = auth.getUser().getEmail();
        this.userNom = auth.getUser().getUserNom();
        this.id = auth.getUser().getId();
    }

}
