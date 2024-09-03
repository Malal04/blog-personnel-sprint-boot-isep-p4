package blog.personnel.dm.service.inter;

import blog.personnel.dm.entity.Article;
import blog.personnel.dm.entity.dto.ArticleDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    Article saveArticle(Article article);

    List<Article> getDashboardArticles(Integer userId);

    List<Article> getAllArticles();

    void createArticle(ArticleDto articleDto, Integer userId);

    Article getArticleById(Integer id);

    void updateArticle(Integer id, ArticleDto articleDto);

    Optional<Article> getArticleById(Integer id, Integer userId);

    void deleteArticle(Integer id);
}
