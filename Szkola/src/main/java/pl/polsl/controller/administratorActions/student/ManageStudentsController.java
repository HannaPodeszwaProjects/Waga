package pl.polsl.controller.administratorActions.student;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentsController implements ParametrizedController {

    @FXML
    private Button buttonDeleteStudent;
    @FXML
    private Button buttonUpdateStudent;
    @FXML
    private ComboBox<String> searchC;
    @FXML
    private TextField searchT;
    @FXML
    private TableView<Uczniowie> tableStudents;
    @FXML
    private TableColumn<Uczniowie, String> nameC;
    @FXML
    private TableColumn<Uczniowie, String> surnameC;
    @FXML
    private TableColumn<Uczniowie, String> classC;


    private ObservableList<Uczniowie> students;
    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        Student s= new Student();
        List<Uczniowie> l=s.getAllStudents();

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        classC.setCellValueFactory(CellData -> {
            Integer idKlasy = CellData.getValue().getIdKlasy();
            String numerKlasy = (new SchoolClass()).getClassNumber(idKlasy);
            return new ReadOnlyStringWrapper(numerKlasy);
        });

        for (Uczniowie u: l) {
            tableStudents.getItems().add(u);
        }
        students = FXCollections.observableArrayList(l);

        buttonDeleteStudent.setDisable(true);
        buttonUpdateStudent.setDisable(true);

        tableStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                buttonDeleteStudent.setDisable(false);
                buttonUpdateStudent.setDisable(false);
            }
            else {
                buttonDeleteStudent.setDisable(true);
                buttonUpdateStudent.setDisable(true);
            }
        });

        search();
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
    }

    private void search()
    {
        searchC.getItems().add("Imię");
        searchC.getItems().add("Nazwisko");
        searchC.getItems().add("Klasa");

        FilteredList<Uczniowie> filter = new FilteredList(students, p -> true);
        tableStudents.setItems(filter);
        searchC.setValue("Nazwisko");

        searchT.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchC.getValue())
            {
                case "Imię":
                    filter.setPredicate(p -> p.getImie().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Nazwisko":
                    filter.setPredicate(p -> p.getNazwisko().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
                case "Klasa":
                    filter.setPredicate(p -> (new SchoolClass()).getClassNumber(p.getIdKlasy()).toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
                    break;
            }
        });
    }



    public void addStudentsButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();

        params.put("procedure","add");
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/student/addOrUpdateStudentForm",params, WindowSize.addOrUpdateStudentForm);
    }

    public void updateStudentsButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();

        params.put("student", tableStudents.getSelectionModel().getSelectedItem());
        params.put("procedure","update");
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/student/addOrUpdateStudentForm",params, WindowSize.addOrUpdateStudentForm);
    }

    public void deleteStudentsButton()
    {

        Uczniowie u = tableStudents.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz usunąć z listy uczniów " + u.getImie() + " " + (u.getDrugieImie() != null ? u.getDrugieImie() + " " : "") + u.getNazwisko() + "?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {

            List<Object> toUpdate = new ArrayList<>();
            List<Object> toDelete = new ArrayList<>();

            //toDelete.add(u);

            Uzytkownicy usr = (new UserModel()).getUserByIdAndRole(u.getID(), Roles.STUDENT);
            toDelete.add(usr);

            Klasy k = (new SchoolClass()).getClassByLeader(u.getID());
            if (k != null) {
                k.setIdPrzewodniczacego(null);
                toUpdate.add(k);
            }

            List<Nieobecnosci> absences = (new AbsenceModel()).displayPresent(u.getID());
            if (absences != null) {
                toDelete.addAll(absences);
            }

            List<Rodzicielstwo> parenthood = (new ParenthoodModel()).findByStudent(u);
            if (parenthood != null) {
                toDelete.addAll(parenthood);
            }

            List<Udzialwkonkursie> competitionParticipation = (new CompetitionModel()).findByStudent(u);
            if (competitionParticipation != null) {
                toDelete.addAll(competitionParticipation);
            }

            List<Udzialwkole> clubParticipation = (new ClubParticipationModel()).findByStudent(u);
            if (clubParticipation != null) {
                toDelete.addAll(clubParticipation);
            }

            List<Uwagi> studentNotes = (new NoteModel()).getStudentNote(u.getID());
            if (studentNotes != null) {
                toDelete.addAll(studentNotes);
            }

            List<Oceny> studentGrades = (new Grade()).getGradeByStudent(u.getID());
            if (studentGrades != null) {
                toDelete.addAll(studentGrades);
                //(new Grade()).delete(studentGrades);
            }

            (new Student()).applyChanges(toUpdate, new ArrayList<>(), toDelete);
            (new Student()).delete(u);

            students.remove(u);
        }
    }


    public void goBackButtonClick() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.PRINCIPAL))
            Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
        else
            Main.setRoot("menu/adminMenuForm", params, WindowSize.adminMenuForm);
    }
}
