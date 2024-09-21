package blog.personnel.dm.service;

import blog.personnel.dm.entity.Article;
import blog.personnel.dm.entity.User;
import blog.personnel.dm.entity.dto.ArticleDto;
import blog.personnel.dm.repository.AmitieRepository;
import blog.personnel.dm.repository.ArticleRepository;
import blog.personnel.dm.repository.UserRepository;
import blog.personnel.dm.service.inter.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AmitieRepository    amitieRepository;

    @Autowired
    private UserRepository userRepository;

    public Article saveArticle(Article article) {
        article.setDateCreation(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public List<Article> getDashboardArticles(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        System.out.println("######### GetName : " + currentUsername);
        System.out.println("####### Fetching articles for userId: " + userId);

        List<Integer> friendIds = amitieRepository.findFriendsIds(userId);
        List<Integer> blockedUserIds = amitieRepository.findBlockedUsersIds(userId);

        System.out.println("Friend IDs: " + friendIds);
        System.out.println("Blocked User IDs: " + blockedUserIds);

        return articleRepository.findArticlesForUserAndFriends(userId, friendIds, blockedUserIds);
    }

    public List<Article> getAllArticles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        System.out.println("######### GetName : " + currentUsername);
        return articleRepository.findAll();
    }

    public void createArticle(ArticleDto articleDto, Integer userId) {
        Article article = new Article();
        article.setTitre(articleDto.getTitre());
        article.setImg(articleDto.getImg());
        article.setContenu(articleDto.getContenu());
        article.setEstPublic(articleDto.isEstPublic());
        article.setAllowComments(articleDto.isAllowComments());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        article.setUser(user);
        articleRepository.save(article);
    }

    public Article getArticleById(Integer id) {
        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article non trouvé."));
    }

    public void updateArticle(Integer id, ArticleDto articleDto) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article non trouvé."));
        article.setTitre(articleDto.getTitre());
        article.setImg(articleDto.getImg());
        article.setContenu(articleDto.getContenu());
        article.setEstPublic(articleDto.isEstPublic());
        article.setAllowComments(articleDto.isAllowComments());
        articleRepository.save(article);
    }

    public Optional<Article> getArticleById(Integer id, Integer userId) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent() && !article.get().getEstPublic()) {
            if (!article.get().getUser().getId().equals(userId)) {
                return Optional.empty();
            }
        }
        return article;
    }

    public void deleteArticle(Integer id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article non trouvé."));
        articleRepository.delete(article);
    }

}
