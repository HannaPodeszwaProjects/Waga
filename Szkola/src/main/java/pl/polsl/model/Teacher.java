package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Teacher implements ManageDataBase {

    /**
     * Entity manager
     */
    EntityManager entityManager;


    public List getUnusedTeachers()
    {
        entityManager = MyManager.getEntityManager();
        Query query = entityManager.createNamedQuery("nauczyciele.getUnusedTeachers");
        List results = query.getResultList();
        return results;
    }


    public List<Nauczyciele> getAllTeachers()
    {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("nauczyciele.findAll", Nauczyciele.class)
                    .getResultList();
        } catch (Exception e){
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not teachers list", e);
            return Collections.emptyList();
        }
    }

    public List checkTutor(Nauczyciele teacher)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("klasy.findByTutor", Klasy.class);
        List<Klasy> results = query.setParameter("idWychowawcy", teacher.getID()).getResultList();

        return results;
    }

    public List checkTeacherSubjects(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("przedmioty.findByTutorId", Przedmioty.class);
        List<Przedmioty> results = query.setParameter("idNauczyciela", id).getResultList();

        return results;
    }

    /*returns all classes that take courses led by a given teacher*/
    public List checkTeacherClasses(Integer id){


            entityManager = MyManager.getEntityManager();
            TypedQuery query = entityManager.createNamedQuery("klasy.findByTeacherId", Klasy.class);
            List<Klasy> results = query.setParameter("idNauczyciela", id).getResultList();

            return results;

    }

    public Nauczyciele getTeacherById(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("nauczyciele.findById", Nauczyciele.class);
        query.setParameter("id", id);
        Nauczyciele results = (Nauczyciele) query.getResultList().get(0);
        return results;
    }

    public String getTeacherEmailByID(Integer Id){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("nauczyciele.getTeacherEmailByID", String.class)
                    .setParameter("ID", Id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get email by id", e);
            return null;
        }
    }
}
