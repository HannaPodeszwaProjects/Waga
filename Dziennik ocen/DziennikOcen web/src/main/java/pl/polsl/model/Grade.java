package pl.polsl.model;

/**
 * Student's grade
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
public class Grade {

    /**
     * Grade as double
     */
    double grade;

    /**
     * Constructor
     *
     */
    public Grade() {
    }

    /**
     * Constructor which takes 1 parameter
     *
     * @param grade the grade to set
     */
    public Grade(double grade) {
        this.grade = grade;
    }

    /**
     * Sets the grade of the person
     *
     * @param newGrade the person's grade to set
     */
    public void setGrade(double newGrade) {
        grade = newGrade;
    }

    /**
     * Gets the grade of the person
     *
     * @return person's grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Override method comparing two objects of class Grade
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

        //Check if o is an instance of Grade or not 
        if (!(o instanceof Grade)) {
            return false;
        }
        // typecast o to Grade 
        Grade g = (Grade) o;

        if (this.grade == g.grade) {
            return true;
        }
        return false;
    }
}
