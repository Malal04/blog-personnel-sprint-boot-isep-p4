package blog.personnel.dm.repository;

import blog.personnel.dm.entity.authentification.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, String> {
}