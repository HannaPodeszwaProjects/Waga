package pl.polsl.test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.model.Grade;
import pl.polsl.model.Subject;

/**
 * Test checking if adding grade to subject is correct
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class SubjectTest {

    private Subject subject;
    Grade addedGrade = new Grade();
    Grade expectedGrade = new Grade(5);
    Grade expectedGrade2 = new Grade(4.5);

    @BeforeEach
    public void setUp() {
        subject = new Subject();

    }

    /**
     * Test checking if adding grade to subject is correct
     *
     * @param grade grade to add
     */
    @ParameterizedTest
    @CsvSource({"5", "123", "-5", "3.234"})
    public void testAddFromFile(double grade) {
        boolean thrown = false;
        try {
            Grade testGrade = new Grade(grade);

            subject.addFromFile(testGrade);
            addedGrade = subject.getGrades().get(0);

            assertEquals(expectedGrade, (subject.getGrades().get(0)), "Niepoprawna ocena");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawna ocena");
        }
    }

    /**
     * Test checking if adding grade to subject is correct
     *
     * @param grade grade to add
     */
    @ParameterizedTest
    @CsvSource({"4.5", "123", "-5", "3.234"})
    public void testAddFromFile2(double grade) {
        boolean thrown = false;
        try {
            Grade testGrade = new Grade(grade);

            subject.addFromFile(testGrade);
            addedGrade = subject.getGrades().get(0);

            assertEquals(expectedGrade2, (subject.getGrades().get(0)), "Niepoprawna ocena");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawna ocena");
        }
    }

    /**
     * Test checking if adding grade to subject is correct
     *
     * @param grade grade to add
     */
    @ParameterizedTest
    @CsvSource({"5", "123", "-5", "3.234"})
    public void testAddNewGrade(double grade) {
        boolean thrown = false;
        try {
            Grade testGrade = new Grade(grade);

            subject.addNewGrade(testGrade);
            addedGrade = subject.getGrades().get(0);

            assertEquals(expectedGrade, (subject.getGrades().get(0)), "Niepoprawna ocena");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawna ocena");
        }
    }

    /**
     * Test checking if adding grade to subject is correct
     *
     * @param grade grade to add
     */
    @ParameterizedTest
    @CsvSource({"4.5", "123", "-5", "3.234"})
    public void testAddNewGrade2(double grade) {
        boolean thrown = false;
        try {
            Grade testGrade = new Grade(grade);

            subject.addNewGrade(testGrade);
            addedGrade = subject.getGrades().get(0);

            assertEquals(expectedGrade2, (subject.getGrades().get(0)), "Niepoprawna ocena");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawna ocena");
        }
    }
}
