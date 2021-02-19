package pl.polsl.model;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pl.polsl.controller.AddStudentException;

/**
 * Represents the class register in the program.
 *
 * @author Hanna Podeszwa
 * @version 3.1
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
     * Add student from a file
     *
     * @param newStudent student to add
     * @return true if adding is correct
     */
    public boolean addFromFile(Person newStudent) {
        try {
            nameOk(newStudent.getName()); //checks if name is correct
            surnameOk(newStudent.getSurname()); //checks if surname is correct
            classOk(newStudent.getClass1()); //checks if class is correct
        } catch (AddStudentException e) {
            return false;
        }
        students.add(newStudent); //adds new student to registerS
        return true;
    }

    /**
     * Adds a new student to the register.
     *
     * @param newStudent new student to add
     * @return true if adding is correct
     */
    public boolean addNewStudent(Person newStudent) {

        try {
            nameOk(newStudent.getName()); //checks if name is correct
            surnameOk(newStudent.getSurname()); //checks if surname is correct
            classOk(newStudent.getClass1()); //checks if class is correct
            studentAlreadyIn(this, newStudent); //checks if the new student is already in the register
        } catch (AddStudentException e) {
            return false;
        }
        students.add(newStudent); //adds new student to register
        return true;
    }

    /**
     * Checks if a given student is already in the register.
     *
     * @param register register with students
     * @param newStudent new student to be added to the registry
     *
     * @throws AddStudentException when student is already in register.
     */
    private void studentAlreadyIn(Register register, Person newStudent) throws AddStudentException {
        for (Person person : register.getStudents()) {
            if (person.getName().equals(newStudent.getName())
                    && person.getSurname().equals(newStudent.getSurname())) {
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
        boolean isOk = Pattern.matches("[A-ZŻŹĆĄŚĘŁÓŃ]{1}[a-zżźćńółęąś]{0,}", name);
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
        boolean isOk = Pattern.matches("[A-ZŻŹĆĄŚĘŁÓŃ]{1}[a-zżźćńółęąś]{0,}", surname);
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
     * @param name selected person's name
     * @param surname selected person's surname
     * @param class1 selected person's class
     * @param subject selected subject
     *
     * @return average of grades
     */
    public double average(String name, String surname, String class1, String subject) {
        Average average = (sum, size) -> sum / size; //lambda expression count the average 

        double sum = 0;
        int size = 0;
        for (Person person : students) {
            if (person.getName().equals(name) && person.getSurname().equals(surname) && person.getClass1().equals(class1)) {
                for (Subject s : person.getSubjects()) {
                    if (s.getName().equals(subject)) {
                        size = s.getGrades().size();
                        for (Grade g : s.getGrades()) {
                            sum += g.getGrade();
                        }
                    }
                }
            }
        }
        if (size == 0) {
            return 0;
        }
        return average.average(sum, size);
    }
}
