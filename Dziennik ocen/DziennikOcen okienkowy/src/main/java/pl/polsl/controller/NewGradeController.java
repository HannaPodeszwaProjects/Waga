package pl.polsl.controller;

import java.io.IOException;
import java.net.URL;
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
import pl.polsl.view.View;

/**
 * FXML Controller class
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class NewGradeController implements Initializable {

    /**
     * Alerts
     */
    View view = new View();
    /**
     * Register with student data as Register
     */
    private Register register;
    /**
     * The student who will receive a grade as Person
     */
    private Person person;
    /**
     * The subject which will receive a grade as Subject
     */
    private Subject subject;
    /**
     * Table to print students
     */
    @FXML
    private TableView<Person> registerTab;
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
     * Table to print subjects
     */
    @FXML
    private TableView<Subject> subjectTab;
    /**
     * Student's subject
     */
    @FXML
    private TableColumn<Subject, String> subjectC;
    /**
     * Table to print grades
     */
    @FXML
    private TableView<Grade> gradeTab;
    /**
     * Student's grade
     */
    @FXML
    private TableColumn<Grade, Double> gradeC;
    /**
     * Exit button
     */
    @FXML
    private Button buttonExit;
    /**
     * Add button
     */
    @FXML
    private Button addId;
    /**
     * New grade
     */
    @FXML
    private TextField newGrade;
    /**
     * New subject
     */
    @FXML
    private TextField newSubject;

    /**
     * Initializes the controller class. Print students in table
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        register = new Register();
        gradeTab.getItems().clear();

        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("surname"));
        classC.setCellValueFactory(new PropertyValueFactory<>("class1"));

        registerTab.getItems().addAll(register.getStudents());
    }

    /**
     * Print subjects in table
     *
     * @param event
     */
    @FXML
    private void printSubject(MouseEvent event) {
        displaySubjects();
    }

    /**
     * Print subjects in table
     *
     *
     */
    private void displaySubjects() {
        gradeTab.getItems().clear();
        subjectTab.getItems().clear();
        person = registerTab.getSelectionModel().getSelectedItem();
        subjectC.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectTab.getItems().addAll(person.getSubjects());
    }

    /**
     * Print grades in table
     *
     * @param event
     */
    @FXML
    private void printGrade(MouseEvent event) {
        displayGrades();
    }

    /**
     * Print grades in table
     *
     *
     */
    private void displayGrades() {
        gradeTab.getItems().clear();
        Subject tmp = subjectTab.getSelectionModel().getSelectedItem();
        if (!(tmp == null)) {
            subject = subjectTab.getSelectionModel().getSelectedItem();
        }

        gradeC.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeTab.getItems().addAll(subject.getGrades());
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

    /**
     * Add new subject and grades to register
     *
     * @param event
     */
    @FXML
    private void add(ActionEvent event) throws IOException {
        try {
            String newS = newSubject.getText();

            double newG = Double.parseDouble(newGrade.getText());
            // student has been selected
            if (person == null) {
                view.alert("Wybierz ucznia.");
            } else {
                Subject s = new Subject(newS);
                Grade g = new Grade(newG);

                Subject inList = person.subjectAlreadyIn(person, s);
                if (inList != null) {
                    subject = inList;
                    addGrade(subject, g);//add grade if it is correct

                } else if (!(person.addNewSubject(s))) { //add new subject if it is correct
                    view.alert("Niepoprawny przedmiot.");
                } else {
                    subject = s;
                    addGrade(subject, g); //add grade if it is correct
                }

            }
            displaySubjects();
            displayGrades();
            return;
        } catch (Exception e) {
            view.alert("Podaj przedmiot i ocenę.");
        }
    }

    /**
     * Add new grades to register
     *
     * @param s subject
     * @param g grade to add
     */
    private void addGrade(Subject s, Grade g) {
        if (!(s.addNewGrade(g))) {
            view.alert("Niepoprawna ocena.");
        }
    }
}
