package blog.personnel.dm.controller;

import blog.personnel.dm.entity.User;
import blog.personnel.dm.entity.authentification.Auth;
import blog.personnel.dm.entity.authentification.AuthReponse;
import blog.personnel.dm.entity.authentification.AuthRequest;
import blog.personnel.dm.entity.authentification.RegistreRequest;
import blog.personnel.dm.service.inter.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController{

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistreRequest registreRequest) {
        try {
            if (registreRequest.getPassword().equals(registreRequest.getConfigPassword())) {
//                Optional<User> user = authService.findByUserNomAndEmail(registreRequest.getUserNom(), registreRequest.getEmail());
                  Optional<User> user =authService.findByIdAndUserNomAndEmail(registreRequest.getId(), registreRequest.getUserNom(), registreRequest.getEmail());
                if (user.isPresent()) {
                    return ResponseEntity.badRequest().body("Un compte est déjà associé à l'adresse email ou au nom d'utilisateur renseigné.");
                }
                authService.registerUser(registreRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body("Inscription réussie.");
            } else {
                return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de l'inscription.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            String userNom = authRequest.getUserNom();
            String password = authRequest.getPassword();

            Optional<User> exuser = authService.findUserByUserNom(userNom);
            if (exuser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le nom d'utilisateur est incorrect.");
            }

            User user = exuser.get();
            if (!authService.checkPassword(password, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le nom utilisateur ou le mot de passe  est incorrect.");
            }

            Auth auth = authService.genToken(user);
            AuthReponse reponse = new AuthReponse(auth);

            return ResponseEntity.ok(reponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la connexion.");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        try {
            // Vérifier si le header contient "Bearer" et extraire le token
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format du token invalide");
            }

            String token = authorization.substring("Bearer ".length()).trim();

            // Trouver le token dans le service
            Optional<Auth> auth = authService.findByToken(token);
            if (auth.isPresent()) {
                authService.disconnect(auth.get());
                return ResponseEntity.ok("Déconnexion réussie");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide");
            }
        } catch (Exception e) {
            // Optionnel: Ajouter du logging ici
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la déconnexion.");
        }
    }

}
