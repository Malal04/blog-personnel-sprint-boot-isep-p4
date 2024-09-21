package blog.personnel.dm.service.inter;

import blog.personnel.dm.entity.Amitie;
import blog.personnel.dm.entity.User;

import java.util.List;

public interface AmitieService {

    void demandeAmitie(Integer userId, Integer amiId);

    void accepterAmitie(Integer id);

    void rejecterAmitie(Integer id);

    List<User> getAmie(Integer userId);

    void blockAmie(Integer userId, Integer amiId);

    void unAmie(Integer userId, Integer amiId);

    List<Amitie> getDemandesAmitie(Integer userId);

}
