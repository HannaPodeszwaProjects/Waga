package pl.polsl.model;

import javax.persistence.*;

import javafx.scene.control.DatePicker;
import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student implements ManageDataBase {
    /**
     * Entity manager
     */
    EntityManager entityManager;



    public List getUnusedStudents()
    {
        entityManager = MyManager.getEntityManager();
        Query query = entityManager.createNamedQuery("uczniowie.getUnusedStudents");
        List results = query.getResultList();
        return results;
    }

    public List<Uczniowie> getAllStudents()
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.findAll", Uczniowie.class);
        List<Uczniowie> results = query.getResultList();
        return results;
    }

    public Integer getHighestId(){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.getHighestID", Uczniowie.class);
        Integer result = (Integer) query.getResultList().get(0);
        return result;
    }

    public Uczniowie getStudentById(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.findById", Uczniowie.class);
        query.setParameter("id", id);
        Uczniowie result = (Uczniowie) query.getResultList().get(0);
        return result;
    }

    public String getStudentEmailById(Integer Id){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("uczniowie.getStudentEmailById", String.class)
                    .setParameter("ID", Id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get email by id", e);
            return null;
        }
    }

    public List getParentsChildren(Integer Id){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.findByParentsId", Uczniowie.class);
        query.setParameter("id", Id);
        List<Uczniowie> result = query.getResultList();
        return result;


    }

    public List getStudentInClass(Integer Id){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.getStudentInClass", Uczniowie.class);
        query.setParameter("id", Id);
        List<Uczniowie> result = query.getResultList();
        return result;


    }

    public List getStudentInClub(Integer Id){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.getStudentInClub", Uczniowie.class);
        query.setParameter("idKola", Id);
        List<Uczniowie> result = query.getResultList();
        return result;


    }

    public List getStudentInCompetition(Integer Id){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.getStudentInCompetition", Uczniowie.class);
        query.setParameter("idKonkursu", Id);
        List<Uczniowie> result = query.getResultList();
        return result;


    }

    public List getGradeFromSubject(Integer Id, Date date1, Date date2){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.getGradeFromSubject", Uczniowie.class);
        query.setParameter("idPrzedmiotu", Id);
        query.setParameter("data1", date1);
        query.setParameter("data2", date2);
        List<Object> result = query.getResultList();
        return result;
    }

    public List getGradeFromStudent(Integer Id,Date date1, Date date2){
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.getGradeFromStudent", Uczniowie.class);
        query.setParameter("id", Id);
        query.setParameter("data1", date1);
        query.setParameter("data2", date2);
        List<Object> result = query.getResultList();
        return result;
    }

}
