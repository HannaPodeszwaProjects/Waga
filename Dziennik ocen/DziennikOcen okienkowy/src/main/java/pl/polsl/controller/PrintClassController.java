package pl.polsl.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pl.polsl.main.App;
import pl.polsl.model.Register;
import pl.polsl.model.Person;
import pl.polsl.model.Subject;
import pl.polsl.model.Grade;

/**
 * FXML Controller class
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class PrintClassController implements Initializable {

    /**
     * Register with student data as Register
     */
    private Register register;
    /**
     * Student's name
     */
    @FXML
    private TableColumn<Person, String> nameC;
    /**
     * Student's surname
     */
    @FXML
    private TableColumn<Person, String> surnameC;
    /**
     * Student's class
     */
    @FXML
    private TableColumn<Person, String> classC;
    /**
     * Table to print students
     */
    @FXML
    private TableView<Person> registerTab;
    /**
     * Exit button
     */
    @FXML
    private Button buttonExit;
    /**
     * Student's subject
     */
    @FXML
    private TableColumn<Subject, String> subjectC;
    /**
     * Student's grade
     */
    @FXML
    private TableColumn<Grade, Double> gradeC;
    /**
     * Table to print subjects
     */
    @FXML
    private TableView<Subject> subjectTab;
    /**
     * Table to print grades
     */
    @FXML
    private TableView<Grade> gradeTab;
    /**
     * Selected class
     */
    @FXML
    private TextField selectedClass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Print subjects in table
     *
     * @param event
     */
    @FXML
    private void printSubject(MouseEvent event) {
        gradeTab.getItems().clear();
        subjectTab.getItems().clear();
        Person p = registerTab.getSelectionModel().getSelectedItem();
        subjectC.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectTab.getItems().addAll(p.getSubjects());
    }

    /**
     * Print grades in table
     *
     * @param event
     */
    @FXML
    private void printGrade(MouseEvent event) {
        gradeTab.getItems().clear();
        Subject s = subjectTab.getSelectionModel().getSelectedItem();
        gradeC.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeTab.getItems().addAll(s.getGrades());
    }

    /**
     * Return to the previous window
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void exitClick(ActionEvent event) throws IOException {
        App.setRoot("optionsPrint");
    }

    /**
     * Print students in table from selected class
     *
     * @param event
     */
    @FXML
    private void enterClass(ActionEvent event) {
        registerTab.getItems().clear();
        subjectTab.getItems().clear();
        gradeTab.getItems().clear();
        register = new Register();
        ArrayList<Person> students = register.getStudents();

        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("surname"));
        classC.setCellValueFactory(new PropertyValueFactory<>("class1"));

        students.stream()
                .filter(person -> person.getClass1().equals(selectedClass.getText()))
                .map(person -> {
                    registerTab.getItems().add(person);
                    return person;
                }).forEachOrdered(person -> {
        });
    }


}
