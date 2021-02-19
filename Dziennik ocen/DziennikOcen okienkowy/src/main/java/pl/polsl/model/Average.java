package pl.polsl.model;

/**
 * Interface used to cout average
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
@FunctionalInterface
public interface Average {

    /**
     * Method counts the average
     *
     * @param sum sum of grade
     * @param size number of grades
     * @return average grade
     */
    double average(double sum, int size);
}
