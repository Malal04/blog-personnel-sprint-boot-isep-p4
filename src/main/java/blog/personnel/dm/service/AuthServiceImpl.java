package blog.personnel.dm.service;

import blog.personnel.dm.entity.User;
import blog.personnel.dm.entity.authentification.Auth;
import blog.personnel.dm.entity.authentification.RegistreRequest;
import blog.personnel.dm.repository.AuthRepository;
import blog.personnel.dm.repository.UserRepository;
import blog.personnel.dm.service.inter.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private  String TOKEN_CHARS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
    int  TOKEN_LENGTH = 32;
    private int  DELAI_EXPIRATION = 30 * 60 * 120;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    public Auth genToken(User user) {
        while (true) {
            String newToken = generateToken();
            Optional<Auth> authentification = authRepository.findById(newToken);
            if (authentification.isEmpty()) {
                Auth auth = new Auth(newToken, user);
                Date now = new Date();
                auth.setCreationDate(now);
                auth.setLastUpdate(now);
                auth.setExpiritionDate(new Date(now.getTime() + DELAI_EXPIRATION * 1000));
                authRepository.save(auth);
                return auth;
            }
        }
    }

    public String generateToken() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int postisin = (int) (Math.random() * TOKEN_CHARS.length());
            res.append(TOKEN_CHARS.charAt(postisin));
        }
        return res.toString();
    }

    public String hashPassword(String pwd){
        //les messages digest : MD5 , SHA-512 ,
        String prefix = "@1JgT0)";
        String suffix = "Ge5t!*T";
        String newPwd = prefix + pwd + suffix;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(newPwd.getBytes());
            String b64Pwd = Base64.getEncoder().encodeToString(hash);
            return b64Pwd;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPassword(String rawPassword, String hashedPassword) {
        String hashedRawPassword = hashPassword(rawPassword);
        return hashedRawPassword.equals(hashedPassword);
    }


    public User registerUser(RegistreRequest registreRequest) {
        if (!registreRequest.getPassword().equals(registreRequest.getConfigPassword())){
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
        }
        User user = new User();
        user.setNomComplete(registreRequest.getNomComplete());
        user.setUserNom(registreRequest.getUserNom());
        user.setEmail(registreRequest.getEmail());
        user.setPassword(hashPassword(registreRequest.getPassword()));
        return userRepository.save(user);
    }

    public Optional<Auth> findByToken(String token) {
        return authRepository.findById(token);
    }


    public void disconnect(Auth auth){
        auth.setDeconnexion(new Date());
        auth.setLastUpdate(new Date());
        authRepository.save(auth);
    }

    public Optional<User> findUserByUserNom(String userNom){
        return userRepository.findByUserNom(userNom);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUserNomAndEmail(String userNom,String email){
        return userRepository.findByUserNomAndEmail(userNom,email);
    }

    public Optional<User> findByIdAndUserNomAndEmail(Integer userId, String userNom, String email){
        return userRepository.findByIdAndUserNomAndEmail(userId, userNom, email);
    }

}
