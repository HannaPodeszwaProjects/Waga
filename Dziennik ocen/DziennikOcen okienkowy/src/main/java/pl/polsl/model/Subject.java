package pl.polsl.model;

import java.util.ArrayList;
import pl.polsl.controller.AddStudentException;

/**
 * Represents the class subject in the program.
 *
 * @author Hanna Podeszwa
 * @version 3.1
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
     * Add grade from a file
     *
     * @param newGrade grade to add
     * @return true if adding is correct
     */
    public boolean addFromFile(Grade newGrade) {
        try {
            gradeOk(newGrade.getGrade()); //checks if grade is correct
        } catch (AddStudentException e) {
            return false;
        }
        grade.add(newGrade);//adds new grade to register
        return true;
    }

    /**
     * Adds a new grade to the register.
     *
     * @param newGrade new grade to add
     * @return true if adding is correct
     */
    public boolean addNewGrade(Grade newGrade) {

        try {
            gradeOk(newGrade.getGrade()); //checks if grade is correct
        } catch (AddStudentException e) {
            return false;
        }
        grade.add(newGrade);//adds new grade to register
        return true;
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
