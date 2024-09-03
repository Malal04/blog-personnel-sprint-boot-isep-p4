package blog.personnel.dm.repository;

import blog.personnel.dm.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article a WHERE (a.user.id = :userId OR (a.user.id IN :friendIds AND a.estPublic = true)) AND a.user.id NOT IN :blockedUserIds")
    public List<Article> findArticlesForUserAndFriends(Integer userId, List<Integer> friendIds, List<Integer> blockedUserIds);

}