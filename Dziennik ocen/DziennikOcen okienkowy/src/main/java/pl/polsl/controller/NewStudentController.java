package pl.polsl.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import pl.polsl.main.App;
import pl.polsl.model.Person;
import pl.polsl.model.Register;
import pl.polsl.view.View;

/**
 * FXML Controller class
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class NewStudentController implements Initializable {

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
     * Adds new student to register
     *
     * @param event
     */
    @FXML
    private void action(ActionEvent event) {
        if (nameId.getText().isEmpty() && surnameId.getText().isEmpty() && classId.getText().isEmpty()) {

        } else {
            Person p = new Person(nameId.getText(), surnameId.getText(), classId.getText());

            if (!(register.addNewStudent(p))) {
                view.alert("Niepoprawne dane lub ten uczeń jest już w dzienniku.");
            } else {
                view.alert("Dodano nowego ucznia.");
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
