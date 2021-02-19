package pl.polsl.model;

import java.util.ArrayList;
import java.util.regex.Pattern;
import pl.polsl.controller.AddStudentException;

/**
 * Student in register
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class Person {

    /**
     * Person's name as String
     */
    private String name;
    /**
     * Person's surname as String
     */
    private String surname;

    /**
     * Person's class as integer
     */
    private String class1;
    /**
     * Person's subject as array list
     */
    private final ArrayList<Subject> subject = new ArrayList<>();

    /**
     * Constructor which takes 3 parameteres
     *
     * @param name the person's name to set
     * @param surname the person's surname to set
     * @param class1 the person's class to set
     */
    public Person(String name, String surname, String class1) {
        this.name = name;
        this.surname = surname;
        this.class1 = class1;
    }

    /**
     * Constructor
     */
    public Person() {

    }

    /**
     * Sets the name of the person
     *
     * @param newName the person's name to set
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Sets the surname of the person
     *
     * @param newSurname the person's name to set
     */
    public void setSurname(String newSurname) {
        surname = newSurname;
    }

    /**
     * Sets the class of the person
     *
     * @param newClass the person's class to set
     */
    public void setClass(String newClass) {
        class1 = newClass;
    }

    /**
     * Sets the subject of the person
     *
     * @param newSubject the person's subject to set
     */
    public void setSubject(Subject newSubject) {
        subject.add(newSubject);
    }

    /**
     * Gets the name of the person
     *
     * @return person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the surname of the person
     *
     * @return person's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the class of the person
     *
     * @return person's class
     */
    public String getClass1() {
        return class1;
    }

    /**
     * Gets the subject of the person
     *
     * @return person's subjects
     */
    public ArrayList<Subject> getSubjects() {
        return subject;
    }

    /**
     * Add subject from a file
     *
     * @param newSubject subject to add
     * @return true if adding is correct
     */
    public boolean addFromFile(Subject newSubject) {
        try {
            subjectOk(newSubject.getName());
        } catch (AddStudentException e) {
            return false;
        }
        subject.add(newSubject);
        return true;
    }

    /**
     * Add new subject
     *
     * @param newSubject subject to add
     * @return true if adding is correct
     */
    public boolean addNewSubject(Subject newSubject) {
        try {
            subjectOk(newSubject.getName());
        } catch (AddStudentException e) {
            return false;
        }
        subject.add(newSubject);
        return true;
    }

    /**
     * Checks if a given subject is correct.
     *
     * @param name new subject
     *
     * @throws AddStudentException when student's subject is wrong.
     */
    private void subjectOk(String name) throws AddStudentException {

        boolean isOk = Pattern.matches("[a-z]{1,}", name);
        if (!isOk) {
            throw new AddStudentException("Wrong subject's name.");
        }
    }

    /**
     * Override method comparing two objects of class Person
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
        // Check if o is an instance of Person or not 
        if (!(o instanceof Person)) {
            return false;
        }
        // typecast o to Person
        Person p = (Person) o;

        if (this.name.equals(p.name) && this.surname.equals(p.surname) && this.class1.equals(p.class1)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a given subject is already in this student's subjects.
     *
     * @param person person in register
     * @param newSubject new subject to be added to the person
     * @return null if this subject isn't in list and this subject from list if
     * already is
     */
    public Subject subjectAlreadyIn(Person person, Subject newSubject) {
        for (Subject subject1 : person.getSubjects()) {
            if (subject1.getName().equals(newSubject.getName())) {
                return subject1;
            }
        }
        return null;
    }
}
