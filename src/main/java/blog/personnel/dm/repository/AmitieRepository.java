package blog.personnel.dm.repository;

import blog.personnel.dm.entity.Amitie;
import blog.personnel.dm.entity.Statut;
import blog.personnel.dm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmitieRepository extends JpaRepository<Amitie, Integer> {

    @Query("SELECT a FROM Amitie a WHERE a.ami.id = :userId AND a.statut = :statut")
    List<Amitie> findDemandesAmitie(Integer userId, Statut statut);

    @Query("SELECT a.ami.id FROM Amitie a WHERE a.user.id = :userId AND a.statut = :statut")
    public List<Integer> findUserByStatus(Integer userId, Statut statut);

    public default List<Integer> findFriendsIds(Integer userId) {
        return findUserByStatus(userId, Statut.ACCEPTEE);
    }

    public default List<Integer> findBlockedUsersIds(Integer userId) {
        return findUserByStatus(userId, Statut.BLOQUEE);
    }

    public default List<Integer> findPendingRequestsIds(Integer userId) {
        return findUserByStatus(userId, Statut.EN_ATTENTE);
    }

    public Amitie findByUserIdAndAmiId(Integer userId, Integer amiId);

    public boolean existsByUserAndAmi(User user, User ami);

}