package blog.personnel.dm.controller;

import blog.personnel.dm.entity.Commentaire;
import blog.personnel.dm.service.inter.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/commentaires")
public class CommentaireController {
    @Autowired
    private CommentaireService commentaireService;

    @PostMapping
    public ResponseEntity<Commentaire> addCommentaire(
            @PathVariable Integer articleId,
            @RequestParam Integer userId,
            @RequestParam String contenu) {
        try {
            Commentaire commentaire = commentaireService.addCommentaire(articleId, userId, contenu);
            return ResponseEntity.ok(commentaire);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @DeleteMapping("/{commentaireId}")
    public ResponseEntity<Void> deleteCommentaire(
            @PathVariable Integer articleId,
            @PathVariable Integer commentaireId,
            @RequestParam Integer userId) {
        try {
            commentaireService.deleteCommentaire(commentaireId, userId, articleId);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Commentaire>> getCommentaires(@PathVariable Integer articleId) {
        try {
            List<Commentaire> commentaires = commentaireService.getCommentairesForArticle(articleId);
            return ResponseEntity.ok(commentaires);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
