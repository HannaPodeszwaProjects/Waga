package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Sale;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Classroom implements ManageDataBase {


    EntityManager entityManager;

    public List<Sale> getAllClassrooms() {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Sale> query = entityManager.createNamedQuery("sale.findAll", Sale.class);
        return query.getResultList();
    }

    public Sale getClassroomById(Integer id) {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Sale> query = entityManager.createNamedQuery("sale.getById", Sale.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
    }

    public String getNameById(Integer id) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("sale.getNameById", Sale.class);
        query.setParameter("id", id);
        String result = "";
        try {
            result = (String) query.getSingleResult();
        } catch (Exception e) {
            result = "-";
        }

        return result;
    }

}
