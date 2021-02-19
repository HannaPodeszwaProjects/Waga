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
public class OptionsPrintController implements Initializable {

    /**
     * Print all students
     */
    @FXML
    private Button buttonAll;
    /**
     * Print one class
     */
    @FXML
    private Button buttonClass;
    /**
     * Exit button
     */
    @FXML
    private Button buttonExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Window with all students
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void printAllClick(ActionEvent event) throws IOException {
        App.setRoot("register");
    }

    /**
     * Window with students from selected class
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void selectedClassClick(ActionEvent event) throws IOException {
        App.setRoot("printClass");
    }

    /**
     * Return to the previous window
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void exitClick(ActionEvent event) throws IOException {
        App.setRoot("options");
    }

}
