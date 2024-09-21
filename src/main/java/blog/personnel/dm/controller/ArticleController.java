package blog.personnel.dm.controller;

import blog.personnel.dm.entity.Article;
import blog.personnel.dm.entity.dto.ArticleDto;
import blog.personnel.dm.service.inter.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody ArticleDto articleDto, @RequestParam("userId") Integer userId) {
        try {
            articleService.createArticle(articleDto, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Article créé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllArticles(@RequestParam("userId") Integer userId) {
        try {
            List<Article> articles = articleService.getDashboardArticles(userId);
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        try {
            List<Article> articles = articleService.getAllArticles();
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Integer id, @RequestHeader("userId") Integer userId) {
        try {
            Optional<Article> article = articleService.getArticleById(id, userId);
            return article.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Integer id, @RequestBody ArticleDto articleDto) {
        try {
            articleService.updateArticle(id, articleDto);
            return ResponseEntity.ok("Article mis à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Integer id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.ok("Article supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite : " + e.getMessage());
        }
    }
}
