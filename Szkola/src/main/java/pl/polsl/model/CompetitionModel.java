package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompetitionModel implements ManageDataBase {
    EntityManager entityManager;

    public List<Konkursy> findAll()
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("Konkursy.findAll", Konkursy.class);
        List<Konkursy> results = query.getResultList();
        return results;
    }

    public List<Konkursy> findByStudentId(Integer Id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("Konkursy.findByStudent", Konkursy.class);
        query.setParameter("id", Id);
        List<Konkursy> results = query.getResultList();
        return results;
    }



    public List<Udzialwkonkursie> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkonkursie.findByTeacher", Udzialwkonkursie.class);
        query.setParameter("id", n.getID());
        List<Udzialwkonkursie> results = query.getResultList();
        return results;
    }
    public List<Udzialwkonkursie> findByStudent(Uczniowie u)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkonkursie.findByStudent", Udzialwkonkursie.class);
        query.setParameter("id", u.getID());
        List<Udzialwkonkursie> results = query.getResultList();
        return results;
    }

    public List<Udzialwkonkursie> findByCompetition(Konkursy k)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkonkursie.findByCompetition", Udzialwkonkursie.class);
        query.setParameter("id", k.getID());
        List<Udzialwkonkursie> results = query.getResultList();
        return results;
    }

    public List<Udzialwkonkursie> findByCompetitionId(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkonkursie.findByCompetition", Udzialwkonkursie.class);
        query.setParameter("id", id);
        List<Udzialwkonkursie> results = query.getResultList();
        return results;
    }

    public List<Konkursy> findCompetitionsByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("Konkursy.findByTeacher", Konkursy.class);
        query.setParameter("id", n.getID());
        List<Konkursy> results = query.getResultList();
        return results;
    }

    public Udzialwkonkursie findByBoth(Integer studentId, Integer competitionId) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkonkursie.findByBoth", Udzialwkonkursie.class);
        query.setParameter("studentID", studentId);
        query.setParameter("competitionID", competitionId);
        Udzialwkonkursie result = (Udzialwkonkursie) query.getSingleResult();
        return result;
    }
}
