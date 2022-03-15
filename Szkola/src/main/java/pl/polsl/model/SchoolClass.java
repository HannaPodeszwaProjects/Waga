package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uzytkownicy;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SchoolClass implements ManageDataBase {
    /**
     * Entity manager
     */
    EntityManager em;

    public List displayClass()
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findAll", Klasy.class);
        List<Klasy> results = query.getResultList();
        return results;
    }



    public Klasy getClassByLeader(Integer Id)
    {
        em = MyManager.getEntityManager();
        try {TypedQuery query = em.createNamedQuery("klasy.findByLeader", Klasy.class);
            query.setParameter("idPrzewodniczacego", Id);
            Klasy result = (Klasy) query.getResultList();
            return result;
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.INFO, "This user wasn't a leader of any class");
            return null;
        }
    }

    public Integer getClassId(String num)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findByNumber", Klasy.class);
        query.setParameter("number", num);
        Klasy results = (Klasy) query.getSingleResult();
        return results.getID();
    }

    public Klasy getClassById(Integer id)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findById", Klasy.class);
        query.setParameter("id", id);
        Klasy results = (Klasy) query.getSingleResult();
        return results;
    }


    public String getClassNumber(Integer id)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findById", Klasy.class);
        query.setParameter("id", id);
        Klasy results = (Klasy) query.getSingleResult();
        return results.getNumer();
    }

public List getStudentsByClass(Klasy k)
{
    em = MyManager.getEntityManager();
    TypedQuery query =
            em.createNamedQuery("klasy.findStudentsById", Klasy.class);
    List<Klasy> results = query.setParameter("id", k.getID()).getResultList();

    return results;
}

}
