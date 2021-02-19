package pl.polsl.test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.model.Person;
import pl.polsl.model.Register;

/**
 * Test checking if adding person to register is correct
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class RegisterTest {

    private Register register;
    Person addedStudent = new Person();
    Person expectedStudent = new Person("Anna", "Kowalska", "6a");

    @BeforeEach
    public void setUp() {
        register = new Register();

    }

    /**
     * Test checking if adding person to register is correct
     *
     * @param name name of person to add
     * @param surname surname of person to add
     * @param class1 class of person to add
     */
    @ParameterizedTest
    @CsvSource({"Anna,Kowalska,6a", "Anna,Kowalska,6.5", "anna,Kowalska,6a", "Anna,kowalska,6a",
        "Anna,Kowalska,abc", " ,Kowalska,6a", "Anna, ,6a", "Anna,Kowalska, "})
    public void testAddNewStudent(String name, String surname, String class1) {
        boolean thrown = false;
        try {
            Person testStudent = new Person(name, surname, class1);

            register.addNewStudent(testStudent);
            addedStudent = register.getStudents().get(0);

            assertEquals(expectedStudent, (register.getStudents().get(0)), "Niepoprawne dane ucznia.");
        } catch (Exception e) {
            thrown = true;
            assertTrue(thrown, "Niepoprawne dane ucznia.");
        }
    }
}
