package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.GodzinyLekcji;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Wiadomosci;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonTimeModel implements ManageDataBase {

    private EntityManager entityManager;


    public Integer getHighestNumber() {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("godzinyLekcji.getHighestNumber", Uczniowie.class);
        Integer result = (Integer) query.getResultList().get(0);
        return result;
    }

    public Integer getNumberById(Integer id) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("godzinyLekcji.getNumber", GodzinyLekcji.class);
        query.setParameter("id", id);
        return (Integer) query.getSingleResult();
    }

    public GodzinyLekcji getById(Integer id) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("godzinyLekcji.getById", GodzinyLekcji.class);
        query.setParameter("id", id);
        return (GodzinyLekcji) query.getSingleResult();
    }

    public List<GodzinyLekcji> getTime(){

        entityManager = MyManager.getEntityManager();
        TypedQuery<GodzinyLekcji> query = entityManager.createNamedQuery("godzinyLekcji.getAll", GodzinyLekcji.class);
        List<GodzinyLekcji> list = query.getResultList();

        return list;
    }
}
