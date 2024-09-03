package blog.personnel.dm.service.inter;

import blog.personnel.dm.entity.Commentaire;

public interface CommentaireService {

    Commentaire addCommentaire(Commentaire commentaire);

    void deleteCommentaire(Integer id, Integer userId);

}
