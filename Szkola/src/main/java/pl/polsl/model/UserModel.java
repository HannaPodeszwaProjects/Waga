package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.utils.Roles;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel implements ManageDataBase {

    EntityManager entityManager;

    public List getUnusedAccounts()
    {
        entityManager = MyManager.getEntityManager();
        List results = entityManager.createNamedQuery("Uzytkownicy.getUnusedStudentAccounts").getResultList();
        results.addAll(entityManager.createNamedQuery("Uzytkownicy.getUnusedTeacherAccounts").getResultList());
        results.addAll(entityManager.createNamedQuery("Uzytkownicy.getUnusedParentAccounts").getResultList());
        return results;
    }

    public Uzytkownicy getUserByLoginAndPassword(String login, String password) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getUserByLoginAndPassword", Uzytkownicy.class)
                    .setParameter("LOGIN", login)
                    .setParameter("PASSWORD", password)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get user by login and password");
            return null;
        }
    }

    public Uzytkownicy getUserByLogin(String login) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getUserByLogin", Uzytkownicy.class)
                    .setParameter("LOGIN", login)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get user by login");
            return null;
        }
    }

    public Uzytkownicy getPrincipal() {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getPrincipal", Uzytkownicy.class)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get user by login");
            return null;
        }
    }

    public Uzytkownicy getUserByIdAndRole(Integer ID, String role) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getUserByIdAndRole", Uzytkownicy.class)
                    .setParameter("ROLE", role)
                    .setParameter("ID", ID)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get user by id and role");
            return null;
        }
    }

    public void updatePasswordByIdAndRole(String role, Integer id, String password) {
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Uzytkownicy.updatePasswordByIdAndRole", Uzytkownicy.class)
                    .setParameter("PASSWORD", password)
                    .setParameter("ROLE", role)
                    .setParameter("ID", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not update password");
            entityManager.getTransaction().rollback();
        }
    }

    public String getLoginByIdAndRole(Integer ID, String role) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getLoginByIdAndRole", String.class)
                    .setParameter("ID", ID)
                    .setParameter("ROLE", role)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get login by id and role");
            return "";
        }
    }
}
