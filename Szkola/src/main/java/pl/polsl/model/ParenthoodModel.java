package pl.polsl.model;

import javafx.scene.control.CheckBox;
import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Rodzice;
import pl.polsl.entities.Rodzicielstwo;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.email.MailSenderModel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ParenthoodModel extends Uczniowie implements ManageDataBase {
    EntityManager entityManager;

    public void setChoose(CheckBox choose) {
        this.choose = choose;
    }

    public CheckBox choose = new CheckBox();

    public ParenthoodModel(){}
    public ParenthoodModel(Uczniowie u) {
        this.ID=u.ID;
        this.imie = u.imie;
        this.drugieImie = u.drugieImie;
        this.email=u.email;
        this.idKlasy=u.idKlasy;
        this.nazwisko=u.nazwisko;
        this.adres=u.adres;
    }
    public List findByParent(Rodzice r)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("rodzicielstwo.findByParent", Rodzicielstwo.class);
        List<Rodzicielstwo> results = query.setParameter("id", r.getID()).getResultList();

        return results;
    }
    public List findByStudent(Uczniowie u)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("rodzicielstwo.findByStudent", Rodzicielstwo.class);
        List<Rodzicielstwo> results = query.setParameter("id", u.getID()).getResultList();

        return results;
    }

    public List<Rodzicielstwo> getParentsByChildID(int id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Rodzicielstwo.getParentsByChildId", Rodzicielstwo.class)
                    .setParameter("ID", id)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(ParenthoodModel.class.getName()).log(Level.WARNING, "Could not get parents by child", e);
            return null;
        }
    }

    public CheckBox getChoose() {
        return choose;
    }

}
