package blog.personnel.dm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String titre;

    private String img;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "est_public", nullable = false)
    private Boolean estPublic = true;

    @Column(name = "allow_comments", nullable = false)
    private Boolean allowComments = true;

    @Column(name = "like_count")
    private int likeCount = 0;

    @Column(name = "view_count")
    private int viewCount = 0;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;

    @PreUpdate
    public void preUpdate() {
        this.dateMiseAJour = LocalDateTime.now();
    }

}