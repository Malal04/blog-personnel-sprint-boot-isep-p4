package blog.personnel.dm.service.inter;

import blog.personnel.dm.entity.Commentaire;

import java.util.List;


public interface CommentaireService {

    Commentaire addCommentaire(Integer articleId, Integer userId, String contenu);

    void deleteCommentaire(Integer commentaireId, Integer userId,Integer articleId);

    List<Commentaire> getCommentairesForArticle(Integer articleId);

}
