package blog.personnel.dm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 500)
    private String nomComplete;

    private String profile;

    @Column(nullable = false, unique = true, length = 255)
    private String userNom;

    @Column( unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Commentaire> commentaires = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Amitie> amities = new HashSet<>();

    public User(String nomComplete, String userNom, String email, String password) {
        this.nomComplete = nomComplete;
        this.userNom = userNom;
        this.email = email;
        this.password = password;
    }

    public User(String nomComplete, String profile, String userNom, String email, String password, Set<Article> articles, Set<Commentaire> commentaires, Set<Amitie> amities) {
        this.nomComplete = nomComplete;
        this.profile = profile;
        this.userNom = userNom;
        this.email = email;
        this.password = password;
        this.articles = articles;
        this.commentaires = commentaires;
        this.amities = amities;
    }

}