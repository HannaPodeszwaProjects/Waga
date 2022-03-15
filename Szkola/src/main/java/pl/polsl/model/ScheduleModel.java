package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ScheduleModel implements ManageDataBase {

    EntityManager entityManager;

    public List<Rozklady> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByTeacher", Rozklady.class);
        query.setParameter("id", n.getID());
        List<Rozklady> results = query.getResultList();
        return results;
    }

    public List<Rozklady> findByTeacher(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByTeacher", Rozklady.class);
        query.setParameter("id", id);
        List<Rozklady> results = query.getResultList();
        return results;
    }

    public List<Rozklady> findByClass(Klasy k)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByClass", Rozklady.class);
        query.setParameter("id", k.getID());
        List<Rozklady> results = query.getResultList();
        return results;
    }

    public List<Rozklady> findByClassroom(Sale s)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByClassroom", Rozklady.class);
        query.setParameter("id", s.getID());
        List<Rozklady> results = query.getResultList();
        return results;
    }

    public List<Rozklady> findByClass(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByClass", Rozklady.class);
        query.setParameter("id", id);
        List<Rozklady> results = query.getResultList();
        return results;
    }

    public List<Rozklady> findBySubject(Przedmioty p)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findBySubject", Rozklady.class);
        query.setParameter("id", p.getID());
        List<Rozklady> results = query.getResultList();
        return results;
    }

}
