package pl.polsl.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import pl.polsl.controller.AddStudentException;
import pl.polsl.entities.Grade1;
import pl.polsl.entities.Person1;
import pl.polsl.entities.PersonGrade;
import pl.polsl.entities.Subject1;

/**
 * Represents the class subject in the program.
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
public class Subject {

    /**
     * Subject's name as String
     */
    private String name;
    /**
     * Person's grades as array list
     */
    private final ArrayList<Grade> grade = new ArrayList<>();
    /**
     * Entity manager
     */
    EntityManager em;

    /**
     * Constructor
     */
    public Subject() {
    }

    /**
     * Constructor which takes 1 parameter
     *
     * @param name the subject's name to set
     */
    public Subject(String name) {
        this.name = name;
    }

    /**
     * Sets the name of the subject
     *
     * @param newName the subject's name to set
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Sets the grades of the subject
     *
     * @param newGrade the subject's grade to set
     */
    public void setGrade(Grade newGrade) {
        grade.add(newGrade);
    }

    /**
     * Gets the name of the subject
     *
     * @return subject's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the grades of the person
     *
     * @return person's grades
     */
    public ArrayList<Grade> getGrades() {
        return grade;
    }

    /**
     * Adds a new grade to the register.
     *
     * @param newGrade new grade to add
     * @param p selected student
     * @param s selected subject
     * @param em entity manager
     * @return true if adding is correct
     */
    public boolean addNewGrade(Person1 p, Subject1 s, Grade newGrade, EntityManager em) {
        this.em = em;
        try {
            gradeOk(newGrade.getGrade()); //checks if grade is correct
        } catch (AddStudentException e) {
            return false;
        }
        //adds new grade to register
        //find grade if it is in database
        Query q = em.createQuery("SELECT a FROM Grade1 a WHERE a.grade=:grade")
                .setParameter("grade", newGrade.getGrade());
        List l = q.getResultList();

        Grade1 g = new Grade1();
        if (l.isEmpty()) {
            g.setGrade(newGrade.getGrade());// create new garde
            persist(g);
        } else {
            g = ((Grade1) l.get(0));
        }
        PersonGrade pg = new PersonGrade();
        pg.setPersonId(p);
        pg.setSubjectId(s);
        pg.setGradeId(g);
        persist(pg);

        return true;
    }

    /**
     * Insert new object to database
     *
     * @param object new object
     */
    public void persist(Object object) {
        try {
            em.getTransaction().begin();

            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    /**
     * Checks if a given grade is correct.
     *
     * @param newGrade new grade
     *
     * @throws AddStudentException when student's grade is wrong.
     */
    private void gradeOk(double newGrade) throws AddStudentException {

        if (newGrade == 1 || newGrade == 1.5 || newGrade == 2 || newGrade == 2.5 || newGrade == 3 || newGrade == 3.5 || newGrade == 4
                || newGrade == 4.5 || newGrade == 5 || newGrade == 5.5 || newGrade == 6) {
            return;
        } else {
            throw new AddStudentException("Niepoprawna ocena.");
        }
    }

    /**
     * Override method comparing two objects of class Subject
     *
     * @param o object to compare
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true   
        if (o == this) {
            return true;
        }

        // Check if o is an instance of Subject or not 
        if (!(o instanceof Subject)) {
            return false;
        }
        // typecast o to Subject 
        Subject s = (Subject) o;

        if (this.name.equals(s.name)) {
            return true;
        }
        return false;
    }
}
