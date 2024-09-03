package blog.personnel.dm.service.inter;

import blog.personnel.dm.entity.User;
import blog.personnel.dm.entity.authentification.Auth;
import blog.personnel.dm.entity.authentification.RegistreRequest;

import java.util.Optional;

public interface AuthService {

    User registerUser(RegistreRequest registreRequest);

    void disconnect(Auth auth);

    boolean checkPassword(String rawPassword, String hashedPassword);

    Auth genToken(User user);

    Optional<Auth> findByToken(String token);

    Optional<User> findUserByUserNom(String userNom);

    Optional<User> findByUserNomAndEmail(String userNom,String email);

    Optional<User> findByIdAndUserNomAndEmail(Integer userId, String userNom, String email);

    Optional<User> findUserByEmail(String email);

}
