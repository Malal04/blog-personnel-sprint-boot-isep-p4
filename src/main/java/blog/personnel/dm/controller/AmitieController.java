package blog.personnel.dm.controller;

import blog.personnel.dm.entity.Amitie;
import blog.personnel.dm.entity.User;
import blog.personnel.dm.service.inter.AmitieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/amites")
public class AmitieController {

    @Autowired
    private AmitieService amitieService;

    @GetMapping("/deman")
    public ResponseEntity<List<Amitie>> getDemandes(@RequestParam("userId") Integer userId) {
        try {
            List<Amitie> demandes = amitieService.getDemandesAmitie(userId);
            return ResponseEntity.ok(demandes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @PostMapping("/demandes")
    public ResponseEntity<?> demandeAmitie(@RequestParam("userId") Integer userId, @RequestParam("amiId") Integer amiId){
        try {
            amitieService.demandeAmitie(userId, amiId);
            return ResponseEntity.ok("Demande envoyée.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite.");
        }
    }

    @PostMapping("/accepter")
    public ResponseEntity<?> accepterAmitie(@RequestParam("id") Integer id){
        try {
            amitieService.accepterAmitie(id);
            return ResponseEntity.ok("Demande acceptée.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite.");
        }
    }

    @PostMapping("/rejecter")
    public ResponseEntity<?> rejecterAmitie(@RequestParam("id") Integer id){
        try {
            amitieService.rejecterAmitie(id);
            return ResponseEntity.ok("Demande annulée.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite.");
        }
    }

    @GetMapping("/lister")
    public ResponseEntity<List<User>> listAmie(@RequestParam("userId") Integer userId) {
        try {
            List<User> amie = amitieService.getAmie(userId);
            return ResponseEntity.ok(amie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @PostMapping("/blocker")
    public ResponseEntity<?> blockAmie(@RequestParam("userId") Integer userId, @RequestParam("amiId") Integer amiId) {
        try {
            amitieService.blockAmie(userId, amiId);
            return ResponseEntity.ok("Ami bloqué.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite.");
        }
    }

    @PostMapping("/unAmie")
    public ResponseEntity<?> unAmie(@RequestParam("userId") Integer userId, @RequestParam("amiId") Integer amiId) {
        try {
            amitieService.unAmie(userId, amiId);
            return ResponseEntity.ok("Ami supprimé.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite.");
        }
    }

}
