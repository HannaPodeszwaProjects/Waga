package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Administratorzy;
import pl.polsl.entities.Nauczyciele;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminModel implements ManageDataBase {
    private EntityManager entityManager;

    public String getAdminEmailByID(Integer Id){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("administratorzy.getAdminEmailByID", String.class)
                    .setParameter("ID", Id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get email by id", e);
            return null;
        }
    }

    public List<Administratorzy> getAllAdmin()
    {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("administratorzy.findAll", Administratorzy.class)
                    .getResultList();
        } catch (Exception e){
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not admin list", e);
            return Collections.emptyList();
        }
    }

    public Administratorzy getAdminById(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("administratorzy.getAdminById", Administratorzy.class)
                    .setParameter("ID", id)
                    .getSingleResult();
        } catch (Exception e){
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not admin list", e);
            return null;
        }
    }


}
