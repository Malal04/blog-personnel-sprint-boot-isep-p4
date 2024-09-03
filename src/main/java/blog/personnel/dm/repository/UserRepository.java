package blog.personnel.dm.repository;

import blog.personnel.dm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByUserNomContaining(String userNom);

    public Optional<User> findById(Integer userId);

    public Optional<User> findByUserNom(String userNom);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByIdAndUserNomAndEmail(Integer userId,String userNom,String email);

    public Optional<User> findByUserNomAndEmail(String userNom,String email);

    public Optional<User> findByEmailAndPassword(String email, String password);

    public Optional<User> findByUserNomAndPassword(String userNom, String password);

    public Optional<User> findByUserNomAndEmailAndPassword(String userNom, String email, String password);

}