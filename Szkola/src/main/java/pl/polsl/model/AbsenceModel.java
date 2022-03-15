package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Przedmioty;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AbsenceModel implements ManageDataBase {


    EntityManager entityManager;

    public List<Nieobecnosci> displayPresent(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Nieobecnosci> query = entityManager.createNamedQuery("nieobecnosci.findAll", Nieobecnosci.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
    public List<Nieobecnosci> findBySubject(Przedmioty p)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Nieobecnosci> query = entityManager.createNamedQuery("nieobecnosci.findBySubject", Nieobecnosci.class);
        query.setParameter("id", p.getID());
        return query.getResultList();
    }

}
