package blog.personnel.dm.service;

import blog.personnel.dm.entity.Article;
import blog.personnel.dm.entity.Commentaire;
import blog.personnel.dm.entity.User;
import blog.personnel.dm.repository.ArticleRepository;
import blog.personnel.dm.repository.CommentaireRepository;
import blog.personnel.dm.repository.UserRepository;
import blog.personnel.dm.service.inter.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public Commentaire addCommentaire(Integer articleId, Integer userId, String contenu) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        Commentaire commentaire = new Commentaire();
        commentaire.setArticle(article);
        commentaire.setUser(user);
        commentaire.setContenu(contenu);
        return commentaireRepository.save(commentaire);
    }
    public void deleteCommentaire(Integer commentaireId, Integer userId,Integer articleId) {
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé."));
        if (!commentaire.getArticle().getId().equals(articleId)) {
            throw new RuntimeException("Ce commentaire ne fait pas partie de cet article.");
        }
        if (!commentaire.getUser().getId().equals(userId) && !commentaire.getArticle().getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'avez pas l'autorisation de supprimer ce commentaire.");
        }
        commentaireRepository.delete(commentaire);
    }

    public List<Commentaire> getCommentairesForArticle(Integer articleId) {
        return commentaireRepository.findByArticleId(articleId);
    }

}
