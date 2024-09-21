package blog.personnel.dm.repository;

import blog.personnel.dm.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
    public List<Commentaire> findByArticleId(Integer articleId);
}