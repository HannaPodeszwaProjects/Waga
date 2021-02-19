package pl.polsl.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.main.App;
import pl.polsl.model.Register;
import pl.polsl.view.View;

/**
 * FXML Controller class
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class AverageController implements Initializable {

    /**
     * Alerts
     */
    View view = new View();
    /**
     * Register with student data as Register
     */
    private Register register;

    /**
     * Student's name
     */
    @FXML
    private TextField nameId;
    /**
     * Student's surname
     */
    @FXML
    private TextField surnameId;
    /**
     * Student's class
     */
    @FXML
    private TextField classId;
    /**
     * Place to dislay average
     */
    @FXML
    private Label averageId;
    /**
     * Student's subject
     */
    @FXML
    private TextField subjectId;
    /**
     * Exit button
     */
    @FXML
    private Button exit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        register = new Register();
    }

    /**
     * Display average of grade
     *
     * @param event
     */
    @FXML
    private void action(ActionEvent event) {
        if (nameId.getText().isEmpty() && surnameId.getText().isEmpty() && classId.getText().isEmpty() && subjectId.getText().isEmpty()) {

        } else {
            double average = register.average(nameId.getText(), surnameId.getText(), classId.getText(), subjectId.getText());
            if (average != 0) {
                averageId.setText(String.valueOf(average));
            } else {
                view.alert("Nie ma podanego ucznia lub przedmiotu w dzienniku.");
            }
        }
    }

    /**
     * Return to the previous window
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void exit(ActionEvent event) throws IOException {
        App.setRoot("options");
    }

}
