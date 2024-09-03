package blog.personnel.dm.service;

import blog.personnel.dm.entity.Article;
import blog.personnel.dm.entity.Commentaire;
import blog.personnel.dm.entity.User;
import blog.personnel.dm.repository.ArticleRepository;
import blog.personnel.dm.repository.CommentaireRepository;
import blog.personnel.dm.repository.UserRepository;
import blog.personnel.dm.service.inter.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public Commentaire addCommentaire(Commentaire commentaire) {

        if (commentaire.getContenu() == null || commentaire.getContenu().trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du commentaire ne peut pas être vide.");
        }

        if (commentaire.getArticle() == null || commentaire.getUser() == null) {
            throw new IllegalArgumentException("Le commentaire doit être associé à un utilisateur et un article.");
        }

         articleRepository.findById(commentaire.getArticle().getId())
                .orElseThrow(() -> new IllegalArgumentException("L'article associé au commentaire n'existe pas."));

         userRepository.findById(commentaire.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("L'utilisateur associé au commentaire n'existe pas."));


        String contenuNormalise = commentaire.getContenu().trim();
        commentaire.setContenu(contenuNormalise);

        return commentaireRepository.save(commentaire);
    }

    public void deleteCommentaire(Integer id, Integer userId) {
        Commentaire commentaire = commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        User commentaireUser = commentaire.getUser();
        User articleOwner = commentaire.getArticle().getUser();

        if (!commentaireUser.getId().equals(userId) && !articleOwner.getId().equals(userId)) {
            throw new SecurityException("Vous n'avez pas la permission de supprimer ce commentaire.");
        }

        commentaireRepository.deleteById(id);
    }

}
