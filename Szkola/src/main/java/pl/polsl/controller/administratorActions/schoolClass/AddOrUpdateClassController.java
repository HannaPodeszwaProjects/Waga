package pl.polsl.controller.administratorActions.schoolClass;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.SchoolClass;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOrUpdateClassController  implements ParametrizedController {

    public TextField name;
    public Label title;
    public ComboBox<String> tutor;
    public ComboBox<String> leader;
    public Button confirm;

    private Klasy toUpdate;
    public enum md {Add, Update}
    private md procedure = md.Update;

    List <Nauczyciele> teachers;
    List <Uczniowie> students;

    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        displayTeachers();
        name.textProperty().addListener(TextListener);

        disableButton();
    }

    private void displayStudentsByClass()
    {
        leader.getItems().add(null);
        Student student = new Student();
        students = student.getStudentInClass(toUpdate.getID());
        for (Uczniowie u : students) {
            leader.getItems().add(u.getImie() + " " + u.getNazwisko());
        }
    }

    private void displayStudents()
    {
        leader.getItems().add(null);
        Student student = new Student();
        students = student.getAllStudents();
        for (Uczniowie u : students) {
            leader.getItems().add(u.getImie() + " " + u.getNazwisko());
        }
    }
    private void displayTeachers()
    {
        tutor.getItems().add(null);
        Teacher teacher = new Teacher();
        teachers = teacher.getAllTeachers();
        for (Nauczyciele n : teachers) {
            tutor.getItems().add(n.getImie() + " " + n.getNazwisko());
        }
    }
private void disableButton()
{
    confirm.setDisable(name.getText().isEmpty());
}
    private ChangeListener TextListener = (observable, oldValue, newValue) -> {
      disableButton();
    };

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");

        if (params.get("procedure") == "add")
        {
            procedure = md.Add;
            title.setText("Dodawanie klasy:");
leader.setDisable(true);
            displayStudents();
        }
        else {
            procedure = md.Update;
            toUpdate = (Klasy) params.get("class");
            title.setText("Modyfikacja klasy:");
            if(toUpdate.getIdWychowawcy() != null) {
                Nauczyciele selectedTutor = (new Teacher()).getTeacherById(toUpdate.getIdWychowawcy());
                tutor.getSelectionModel().select(selectedTutor.getImie() + " " + selectedTutor.getNazwisko());
            }
            if(toUpdate.getIdPrzewodniczacego() != null) {
                Uczniowie selectedLeader = (new Student()).getStudentById(toUpdate.getIdPrzewodniczacego());
                leader.getSelectionModel().select(selectedLeader.getImie() + " " + selectedLeader.getNazwisko());
            }
            displayStudentsByClass();
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNumer());
        }
    }

    public void confirmChangesButton() throws IOException
    {
        if (procedure == md.Add) {
            Klasy k = new Klasy();
            setNewValues(k);
            (new SchoolClass()).persist(k);
        } else if (procedure == md.Update) {
            setNewValues(toUpdate);
            (new SchoolClass()).update(toUpdate);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/class/manageClassForm", params, WindowSize.manageClassForm);
    }
    private void setNewValues(Klasy k)
    {
        k.setNumer((name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));

        int tutorIndex = tutor.getSelectionModel().getSelectedIndex()-1;
        Nauczyciele selectedTutor;
        if(tutorIndex>=0)
        {
           selectedTutor = teachers.get(tutorIndex);
            k.setIdWychowawcy(selectedTutor.getID());
        }
        else
            k.setIdWychowawcy(null);


        int leaderIndex = leader.getSelectionModel().getSelectedIndex()-1;
        Uczniowie selectedLeader;
        if(leaderIndex>=0)
        {
            selectedLeader = students.get(leaderIndex);
            k.setIdPrzewodniczacego(selectedLeader.getID());
        }
        k.setIdPrzewodniczacego(null);
    }

    public void discardChangesButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/class/manageClassForm", params, WindowSize.manageClassForm);
    }



}
