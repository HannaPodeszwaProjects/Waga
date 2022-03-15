package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rodzice;
import pl.polsl.entities.Wiadomosci;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageModel implements ManageDataBase {

    private EntityManager entityManager;

    public List<Wiadomosci> getReceivedMessagesByUserLogin(String login) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getReceivedMessagesByUserLogin", Wiadomosci.class)
                    .setParameter("LOGIN", login)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not get messages", e);
            return Collections.emptyList();
        }
    }

    public List<Wiadomosci> getSentMessagesByUserLogin(String login) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getSentMessagesByUserLogin", Wiadomosci.class)
                    .setParameter("LOGIN", login)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not get messages", e);
            return Collections.emptyList();
        }
    }

    public Boolean insertMessage(String topic, String message, Date date, String receiver, String sender) {
        Wiadomosci wiadomosci = new Wiadomosci();
        wiadomosci.setTemat(topic);
        wiadomosci.setTresc(message);
        wiadomosci.setData(date);
        wiadomosci.setOdbiorca(receiver);
        wiadomosci.setNadawca(sender);
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(wiadomosci);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not insert message", e);
            return false;
        }
    }

    public String getMessageByReceiverSenderAndDat(String receiver, String sender, Date date) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getMessageByReceiverSenderAndDate", String.class)
                    .setParameter("RECEIVER", receiver)
                    .setParameter("SENDER", sender)
                    .setParameter("DATE", date)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not get message", e);
            return "";
        }
    }

    public List<Wiadomosci> findByTeacher(String login)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("wiadomosci.findByTeacher", Wiadomosci.class);
        List<Wiadomosci> results = query.setParameter("l", login).getResultList();

        return results;
    }

    public List<Wiadomosci> findByParent(String login)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("wiadomosci.findByParent", Wiadomosci.class);
        List<Wiadomosci> results = query.setParameter("l", login).getResultList();

        return results;
    }

}
