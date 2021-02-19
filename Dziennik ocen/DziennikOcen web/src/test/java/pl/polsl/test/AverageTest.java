package pl.polsl.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.entities.Grade1;
import pl.polsl.model.Register;

/**
 * Test checking if the average grade is correct
 *
 * @author Hanna Podeszwa
 * @version 2.1
 */
public class AverageTest {

    private Register register = new Register();
    private List<Object> l = new ArrayList();
    private List<Object> l2 = new ArrayList();

    @BeforeEach
    public void setUp() {
        l.clear();
        Grade1 g = new Grade1();
        g.setGrade(2.0);
        l.add(g);
        Grade1 g2 = new Grade1();
        g2.setGrade(3.0);
        l.add(g2);
        Grade1 g3 = new Grade1();
        g3.setGrade(5.0);
        l.add(g3);
        Grade1 g4 = new Grade1();
        g4.setGrade(6.0);
        l.add(g4);
        l2.clear();

    }

    /**
     * Test checking if the average grade is correct
     *
     *
     * @param result expected average
     */
    @ParameterizedTest
    @CsvSource({"4"})
    public void testAverage(double result) {

        double avg = register.average(l);

        assertEquals(result, avg, "Niepoprawna srednia.");

    }

    /**
     * Test checking if the average grade is correct
     *
     *
     * @param result expected average
     */
    @ParameterizedTest
    @CsvSource({"0"})
    public void testAverage2(double result) {

        double avg = register.average(l2);

        assertEquals(result, avg, "Niepoprawna srednia.");

    }

}
