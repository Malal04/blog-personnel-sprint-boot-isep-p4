package blog.personnel.dm.entity.authentification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequest {

    public String userNom;

    public String password;
}
