package pl.polsl.controller;

/**
 * An exception when adding a new student.
 *
 * @author Hanna Podeszwa
 * @version 1.1
 */
public class AddStudentException extends Exception {

    /**
     * Non-parameter constructor
     */
    public AddStudentException() {
    }

    /**
     * Exception class constructor
     *
     * @param message display message
     */
    public AddStudentException(String message) {
        super(message);
    }
}
