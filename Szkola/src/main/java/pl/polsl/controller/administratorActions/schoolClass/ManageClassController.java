package pl.polsl.controller.administratorActions.schoolClass;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageClassController implements ParametrizedController {

    @FXML
    private TableView<Klasy> tableClass;
    @FXML
    private TableColumn<Klasy, String> nameC;
    @FXML
    private Label tutor;
    @FXML
    private Label leader;

    private String mode;
    private Integer id;

    @FXML
    public void initialize() {
        displayTableClass();
        changeLabels();
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
    }

    private void displayTableClass() {
        tableClass.getItems().clear();
        SchoolClass c = new SchoolClass();
        List l = c.displayClass();
        nameC.setCellValueFactory(new PropertyValueFactory<>("numer"));

        for (Object k : l) {
            tableClass.getItems().add((Klasy) k);
        }
    }

    private void changeLabels()
    {
        ObservableList<Klasy> selectedClass = tableClass.getSelectionModel().getSelectedItems();
        selectedClass.addListener(new ListChangeListener<Klasy>() {
            @Override
            public void onChanged(Change<? extends Klasy> change) {
                Integer selectedtutor = selectedClass.get(0).getIdWychowawcy();
                Integer selectedleader = selectedClass.get(0).getIdPrzewodniczacego();

                if (selectedtutor != null) {
                    Nauczyciele tutorClass = (new Teacher()).getTeacherById(selectedtutor);
                    tutor.setText(tutorClass.getImie() + " " + tutorClass.getNazwisko());
                }
                else
                    tutor.setText("");
                if (selectedleader != null) {
                    Uczniowie leaderClass = (new Student()).getStudentById(selectedleader);
                    leader.setText(leaderClass.getImie() + " " + leaderClass.getNazwisko());
                }
                else
                    leader.setText("");
            }
        });
    }

    public void addClassButton() throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put("procedure", "add");
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/class/addOrUpdateClassForm", params, WindowSize.addOrUpdateClassForm);
    }

    public void updateClassButton() throws IOException {
        Klasy toUpdate = tableClass.getSelectionModel().getSelectedItem();
        if (toUpdate == null) {
            chooseClassAlert(true);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("class", tableClass.getSelectionModel().getSelectedItem());
            params.put("procedure", "update");
            params.put("mode", mode);
            params.put("id", id);
            Main.setRoot("administratorActions/class/addOrUpdateClassForm", params, WindowSize.addOrUpdateClassForm);
        }
    }

    public void deleteClassButton() {
        Klasy toDelete = tableClass.getSelectionModel().getSelectedItem();
        if (toDelete == null) {
           chooseClassAlert(false);
        } else {
            List<Klasy> classStudents = (new SchoolClass()).getStudentsByClass(toDelete);
            if (!(classStudents.isEmpty())) {
               classWithStudentsAlert();
            } else {


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Usuwanie klasy");
                alert.setHeaderText("Czy na pewno chcesz usunąć tą klasę?");
                alert.setContentText(toDelete.getNumer() +
                        "\nSpowoduje to też usunięcie wszystkich rozkładów z tą klasą.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    //delete schedule with class
                  deleteSchedule(toDelete);

                    (new SchoolClass()).delete(toDelete);
                    displayTableClass();
                }
            }

        }
    }

    private void deleteSchedule(Klasy toDelete)
    {
        List<Rozklady> classScheduleList = (new ScheduleModel()).findByClass(toDelete);
        if(!(classScheduleList.isEmpty())) {
            for (Rozklady r: classScheduleList) {
                (new ScheduleModel()).delete(r);
            }
        }
    }

    private void classWithStudentsAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Usuwanie klasy");
        alert.setHeaderText("Nie możesz usunąć klasy, która ma przypisanych uczniów!!!");
        alert.setContentText("Przenieś uczniów do innej klasy lub ich usuń.");
        alert.showAndWait();
    }

    private void chooseClassAlert(boolean update)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(update) {
            alert.setTitle("Modyfikacja klasy");
            alert.setContentText("Wybierz klasę do modyfikacji.");
        }
        else {
            alert.setTitle("Usuwanie klasy");
            alert.setContentText("Wybierz klasę do usunięcia.");
        }
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void cancelButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.PRINCIPAL))
            Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
        else
            Main.setRoot("menu/adminMenuForm", params, WindowSize.adminMenuForm);
    }
}
