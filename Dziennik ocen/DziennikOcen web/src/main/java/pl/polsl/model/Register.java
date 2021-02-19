package pl.polsl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import pl.polsl.entities.Person1;

import pl.polsl.controller.AddStudentException;
import pl.polsl.entities.Grade1;

/**
 * Represents the class register in the program.
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
public class Register {

    /**
     * Register with student data as ArrayList
     */
    private static final ArrayList<Person> students = new ArrayList<>();

    /**
     * Gets the person in register
     *
     * @return person's grades
     */
    public ArrayList<Person> getStudents() {
        return students;
    }
    /**
     * Entity manager as EntityManager
     */
    EntityManager em;

    /**
     * Adds a new student to the register.
     *
     * @param newStudent new student to add
     * @param em entity manager
     * @return true if adding is correct
     */
    public boolean addNewStudent(Person newStudent, EntityManager em) {
        this.em = em;
        try {
            nameOk(newStudent.getName()); //checks if name is correct
            surnameOk(newStudent.getSurname()); //checks if surname is correct
            classOk(newStudent.getClass1()); //checks if class is correct
            studentAlreadyIn(newStudent); //checks if the new student is already in the register
        } catch (AddStudentException e) {
            return false;
        }

        Person1 person = new Person1();
        person.setName(newStudent.getName());
        person.setSurname(newStudent.getSurname());
        person.setClass1(newStudent.getClass1());

        persist(person);

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
     * Checks if a given student is already in the register.
     *
     * @param newStudent new student to be added to the registry
     *
     * @throws AddStudentException when student is already in register.
     */
    private void studentAlreadyIn(Person newStudent) throws AddStudentException {

        Query q = em.createQuery("SELECT a FROM Person1 a"); //nazwa klasy
        List l = q.getResultList();

        for (Object o : l) {
            if (((Person1) o).getName().equals(newStudent.getName())
                    && ((Person1) o).getSurname().equals(newStudent.getSurname())) {
                throw new AddStudentException("Ten student jest juz w dzienniku.");
            }
        }
    }

    /**
     * Checks if a given name is correct.
     *
     * @param name new name
     *
     * @throws AddStudentException when student's name is wrong.
     */
    private void nameOk(String name) throws AddStudentException {
        boolean isOk = Pattern.matches("[A-Z]{1}[a-z]{0,}", name);
        if (!isOk) {
            throw new AddStudentException("Niepoprawne imie.");
        }
    }

    /**
     * Checks if a given surname is correct.
     *
     * @param surname new surname
     *
     * @throws AddStudentException when student's surname is wrong.
     */
    private void surnameOk(String surname) throws AddStudentException {
        boolean isOk = Pattern.matches("[A-Z]{1}[a-z]{0,}", surname);
        if (!isOk) {
            throw new AddStudentException("Niepoprawne nazwisko.");
        }
    }

    /**
     * Checks if a given class is correct.
     *
     * @param class1 new class
     *
     * @throws AddStudentException when student's class is wrong.
     */
    private void classOk(String class1) throws AddStudentException {
        boolean isOk = Pattern.matches("[1-9]{1}[0-9]{0,}[a-z]{0,}", class1);
        if (!isOk) {
            throw new AddStudentException("Niepoprawna klasa.");
        }
    }

    /**
     * Checks if a given class is correct.
     *
     * @param l gardes
     *
     * @return average of grades
     */
    public double average(List l) {
        Average average = (sum, size) -> sum / size; //lambda expression count the average 

        double sum = 0;

        int size = l.size();
        if (size == 0) {
            return 0;
        }
        for (Object o : l) {
            sum += (((Grade1) o).getGrade());
        }
        return average.average(sum, size);
    }
}
