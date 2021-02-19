package pl.polsl.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import pl.polsl.main.App;

/**
 * FXML Controller class
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class OptionsController implements Initializable {

    /**
     * Print register
     */
    @FXML
    private Button buttonPrint;
    /**
     * Add new student
     */
    @FXML
    private Button buttonStudent;
    /**
     * Add new grade
     */
    @FXML
    private Button buttonGrade;
    /**
     * Print average
     */
    @FXML
    private Button buttonAverage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Window with print options
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void buttonPrintClick(ActionEvent event) throws IOException {
        App.setRoot("optionsPrint");
    }

    /**
     * Window with adding new student
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void buttonStudentClick(ActionEvent event) throws IOException {
        App.setRoot("newStudent");
    }

    /**
     * Window with adding new grade
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void buttonGradeClick(ActionEvent event) throws IOException {
        App.setRoot("newGrade");
    }

    /**
     * Window with displaying average
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void buttonAverageClick(ActionEvent event) throws IOException {
        App.setRoot("average");
    }

}
