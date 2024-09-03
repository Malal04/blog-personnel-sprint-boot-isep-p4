package blog.personnel.dm.service;

import blog.personnel.dm.entity.Amitie;
import blog.personnel.dm.entity.Statut;
import blog.personnel.dm.entity.User;
import blog.personnel.dm.repository.AmitieRepository;
import blog.personnel.dm.repository.UserRepository;
import blog.personnel.dm.service.inter.AmitieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmitieServiceImpl implements AmitieService {

    @Autowired
    private AmitieRepository amitieRepository;

    @Autowired
    private UserRepository userRepository;


    public void demandeAmitie(Integer userId, Integer amiId) {
        if (userId.equals(amiId)) {
            throw new IllegalArgumentException("Vous ne pouvez pas vous ajouter vous-même.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
        User ami = userRepository.findById(amiId).orElseThrow(() -> new RuntimeException("Ami non trouvé."));

        if (amitieRepository.existsByUserAndAmi(user, ami)) {
            throw new IllegalStateException("Demande d'amitié déjà existante.");
        }

        Amitie amitie = new Amitie();
        amitie.setUser(user);
        amitie.setAmi(ami);
        amitie.setStatut(Statut.EN_ATTENTE);
        amitieRepository.save(amitie);
    }

    public void accepterAmitie(Integer id) {
        Amitie amitie = amitieRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande d'amitié non trouvée."));
        if (amitie.getStatut() != Statut.EN_ATTENTE) {
            throw new IllegalStateException("La demande d'amitié ne peut pas être acceptée.");
        }
        amitie.setStatut(Statut.ACCEPTEE);
        amitieRepository.save(amitie);
    }

    public void rejecterAmitie(Integer id) {
        Amitie amitie = amitieRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande d'amitié non trouvée."));
        amitieRepository.delete(amitie);
    }

    public List<User> getAmie(Integer userId) {
        List<Integer> friendIds = amitieRepository.findFriendsIds(userId);
        return userRepository.findAllById(friendIds);
    }

    public void blockAmie(Integer userId, Integer amiId) {
        Amitie amitie = amitieRepository.findByUserIdAndAmiId(userId, amiId);
        if (amitie == null) {
            throw new RuntimeException("Relation d'amitié non trouvée.");
        }
        amitie.setStatut(Statut.BLOQUEE);
        amitieRepository.save(amitie);
    }

    public void unAmie(Integer userId, Integer amiId) {
        Amitie amitie = amitieRepository.findByUserIdAndAmiId(userId, amiId);
        if (amitie == null) {
            throw new RuntimeException("Relation d'amitié non trouvée.");
        }
        amitieRepository.delete(amitie);
    }

}
