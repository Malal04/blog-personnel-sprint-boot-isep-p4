package blog.personnel.dm.entity.authentification;

import blog.personnel.dm.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@Table(name = "auth")
public class Auth {
    @Id
    private String token;

    @ManyToOne
    private User user;

    private Date expiritionDate;

    private Date creationDate;

    private Date deconnexion;

    private Date lastUpdate;

    public Auth(String token, User user) {
        this.token = token;
        this.user = user;
    }

}