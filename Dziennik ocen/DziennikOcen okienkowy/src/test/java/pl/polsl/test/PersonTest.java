package pl.polsl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.model.Person;
import pl.polsl.model.Subject;

/**
 * Test checking if adding subject to person is correct
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class PersonTest {

    private Person person;
    private Person person2;
    private Subject addedSubject = new Subject();
    private Subject expectedSubject = new Subject("wf");

    @BeforeEach
    public void setUp() {
        person = new Person();

        person2 = new Person();
        person2.getSubjects().add(expectedSubject);

    }

    /**
     * Test checking if adding subject to person is correct
     *
     * @param name name of subject to add
     */
    @ParameterizedTest
    @CsvSource({"wf", "123", "Wf", "wf123"})
    public void testAddFromFile(String name) {
        boolean thrown = false;
        try {
            Subject testSubject = new Subject(name);

            person.addFromFile(testSubject);
            addedSubject = person.getSubjects().get(0);

            assertEquals(expectedSubject, (person.getSubjects().get(0)), "Niepoprawna nazwa przedmiotu.");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawna nazwa przedmiotu.");
        }

    }

    /**
     * Test checking if adding subject to person is correct
     *
     * @param name name of subject to add
     */
    @ParameterizedTest
    @CsvSource({"wf", "123", "Wf", "wf123"})
    public void testAddNewSubject(String name) {
        boolean thrown = false;
        try {
            Subject testSubject = new Subject(name);

            person.addNewSubject(testSubject);
            addedSubject = person.getSubjects().get(0);

            assertEquals(expectedSubject, (person.getSubjects().get(0)), "Niepoprawna nazwa przedmiotu.");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawna nazwa przedmiotu.");
        }

    }

    /**
     * Test checking if adding subject is already in list
     *
     * @param name name of subject to add
     */
    @ParameterizedTest
    @CsvSource({"wf",})
    public void testSubjectAlreadyIn(String name) {

        Subject testSubject = new Subject(name);

        Subject s = person2.subjectAlreadyIn(person2, testSubject);

        assertEquals(expectedSubject, s, "Tego przedmiotu nie ma w liście.");

    }

    /**
     * Test checking if adding subject is already in list
     *
     * @param name name of subject to add
     */
    @ParameterizedTest
    @CsvSource({"polski"})
    public void testSubjectAlreadyIn2(String name) {

        Subject testSubject = new Subject(name);

        Subject s = person2.subjectAlreadyIn(person2, testSubject);

        assertNull(s, "Tego przedmiotu nie ma w liście.");

    }
}
