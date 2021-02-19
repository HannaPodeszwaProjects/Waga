package pl.polsl.test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.model.Person;
import pl.polsl.model.Register;
import pl.polsl.model.Subject;
import pl.polsl.model.Grade;

/**
 * Test checking if the average grade is correct
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class AverageTest {

    private Register register;

    @BeforeEach
    public void setUp() {
        register = new Register();
        Person p = new Person("Anna", "Kowalska", "6a");
        Subject s = new Subject("wf");
        Grade g1 = new Grade(2);
        Grade g2 = new Grade(3);
        Grade g3 = new Grade(5);
        Grade g4 = new Grade(6);
        register.getStudents().add(p);
        register.getStudents().get(0).getSubjects().add(s);
        register.getStudents().get(0).getSubjects().get(0).getGrades().add(g1);
        register.getStudents().get(0).getSubjects().get(0).getGrades().add(g2);
        register.getStudents().get(0).getSubjects().get(0).getGrades().add(g3);
        register.getStudents().get(0).getSubjects().get(0).getGrades().add(g4);

    }

    /**
     * Test checking if the average grade is correct
     *
     * @param name selected person's name
     * @param surname selected person's surname
     * @param class1 selected person's class
     * @param subject selected subject
     * @param result expected average
     */
    //@Disabled
    @ParameterizedTest
    @CsvSource({"Anna,Kowalska,6a,wf,4", "anna,Kowalska,6a,wf,0", "Anna,jakas,6a,wf,0", "Anna,Kowalska,2,wf,0",
        "Anna,Kowalska,6a,abc,0"})
    public void testAverage(String name, String surname, String class1, String subject, double result) {

        double avg = register.average(name, surname, class1, subject);

        assertEquals(result, avg, "Niepoprawna srednia.");

    }
}
