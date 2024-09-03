package blog.personnel.dm.controller;

import blog.personnel.dm.entity.Commentaire;
import blog.personnel.dm.service.inter.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentaireController {
    @Autowired
    private CommentaireService commentaireService;

    @PostMapping
    public ResponseEntity<?> addCommentaire(@RequestBody Commentaire commentaire) {
        try {
            Commentaire createdCommentaire = commentaireService.addCommentaire(commentaire);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentaire);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la création du commentaire.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentaire(@PathVariable Integer id, @RequestHeader("UserId") Integer userId) {
        try {
            commentaireService.deleteCommentaire(id, userId);
            return ResponseEntity.ok("Commentaire supprimé avec succès.");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la suppression du commentaire.");
        }
    }
}

