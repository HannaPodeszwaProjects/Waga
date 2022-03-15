package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Udzialwkole;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClubParticipationModel implements ManageDataBase {
    EntityManager entityManager;

    public Udzialwkole findByBoth(Integer studentId, Integer clubId) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByBoth", Udzialwkole.class);
        query.setParameter("studentID", studentId);
        query.setParameter("clubID", clubId);
        Udzialwkole result = (Udzialwkole) query.getSingleResult();
        return result;
    }

    public List<Udzialwkole> findByClub(Kolanaukowe k)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByClub", Udzialwkole.class);
        query.setParameter("idKola", k.getID());
        List<Udzialwkole> results = query.getResultList();
        return results;
    }
    public List<Udzialwkole> findByClub(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByClub", Udzialwkole.class);
        query.setParameter("idKola", id);
        List<Udzialwkole> results = query.getResultList();
        return results;
    }

    public List<Udzialwkole> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByTeacher", Udzialwkole.class);
        query.setParameter("id", n.getID());
        List<Udzialwkole> results = query.getResultList();
        return results;
    }

    public List<Udzialwkole> findByStudent(Uczniowie u) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByStudent", Udzialwkole.class);
        query.setParameter("id", u.getID());
        List<Udzialwkole> results = query.getResultList();
        return results;
    }
}
