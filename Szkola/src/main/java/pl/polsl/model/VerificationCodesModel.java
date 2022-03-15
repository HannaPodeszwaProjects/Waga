package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Kodyweryfikacyjne;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerificationCodesModel {
    EntityManager entityManager;

    public void insertVerificationCode(String code, String login, String email) {
        Kodyweryfikacyjne verificationCode = new Kodyweryfikacyjne();
        verificationCode.setEmail(email);
        verificationCode.setKod(code);
        verificationCode.setLogin(login);
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(verificationCode);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(VerificationCodesModel.class.getName()).log(Level.WARNING, "Could not insert varification code");
        }
    }

    public Kodyweryfikacyjne getVerificationCode(String code) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Kodyweryfikacyjne.getVerificationCodeByCode", Kodyweryfikacyjne.class)
                    .setParameter("CODE", code)
                    .getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(VerificationCodesModel.class.getName()).log(Level.WARNING, "Could not get verification code by code");
            return null;
        }
    }

    public Kodyweryfikacyjne getVerificationCodeByLogin(String login){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Kodyweryfikacyjne.getVerificationCodeByLogin", Kodyweryfikacyjne.class)
                    .setParameter("LOGIN", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(VerificationCodesModel.class.getName()).log(Level.WARNING, "Could not get verification code by login");
            return null;
        }
    }

    public void removeVerificationCodeByLogin(String login){
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM Kodyweryfikacyjne K WHERE K.login = :LOGIN", Kodyweryfikacyjne.class)
                    .setParameter("LOGIN", login)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            Logger.getLogger(VerificationCodesModel.class.getName()).log(Level.WARNING, "Could not delete code by login");
            entityManager.getTransaction().rollback();
        }
    }
}
