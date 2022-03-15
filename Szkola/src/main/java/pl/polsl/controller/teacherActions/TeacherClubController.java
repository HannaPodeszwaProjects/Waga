package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherClubController implements ParametrizedController {

    @FXML
    private ComboBox<String> comboboxClubs;
    @FXML
    private TableColumn<Uczniowie, String> columnStudent;
    @FXML
    private TableColumn<Uczniowie, String> columnClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private Label infoLabel;
    @FXML
    private Button buttonUnassign;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonAssign;

    Integer loggedTeacherId;
    ObservableList<Kolanaukowe> clubsList;
    ObservableList<Uczniowie> studentList = FXCollections.observableArrayList();

    private void refreshClubList(Integer position){
        if(clubsList.isEmpty()) {
            comboboxClubs.getSelectionModel().select(-1);
            buttonAssign.setDisable(true);
            buttonDelete.setDisable(true);
        } else {
            comboboxClubs.getItems().clear();
            for(Kolanaukowe knl : clubsList) {
                comboboxClubs.getItems().add(knl.getOpis());
            }
            comboboxClubs.getSelectionModel().select(position);
            setParticipants(position);
            buttonAssign.setDisable(false);
            buttonDelete.setDisable(false);
        }
    }
    @Override
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");

        Integer numberClub = (Integer) params.get("numberClub");
        if(numberClub == null)
            numberClub = 0;

        Nauczyciele n = (new Teacher()).getTeacherById(loggedTeacherId);
        clubsList = FXCollections.observableArrayList((new ClubModel()).findByTeacher(n));
        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) deleteGrade);
        refreshClubList(numberClub);

        infoLabel.setText("");
    }

    private final ListChangeListener<? extends TablePosition> deleteGrade = (
            javafx.collections.ListChangeListener.Change<? extends TablePosition> change) -> {
        Uczniowie tym = table.getSelectionModel().getSelectedItem();
        buttonUnassign.setDisable(tym == null);
    };

    public void clickButtonBack() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
    }

    public void clickButtonAdd() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherAddNewClubForm", params, WindowSize.teacherAddNewClubForm);
    }

    public void clickButtonAssign() throws IOException {
        Map<String, Object> params = new HashMap<>();
        int tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
        Kolanaukowe k =  clubsList.get(tmpId);
        Integer clubId = k.getID();
        params.put("id", loggedTeacherId);
        params.put("clubId", clubId);
        params.put("numberClub", comboboxClubs.getSelectionModel().getSelectedIndex());
        Main.setRoot("teacherActions/teacherAssignStudentToClubForm", params, WindowSize.TeacherAssignStudentToClubForm);
    }

    public void clickButtonDelete() {
        if(studentList.isEmpty()){
            int tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
            Kolanaukowe k =  clubsList.get(tmpId);
            comboboxClubs.getItems().remove(k);
            clubsList.remove(k);
            (new ClubModel()).delete(k);
            refreshClubList(0);

        } else {
            infoLabel.setText("Usuń wszystkich\nuczestników przed\nusunięciem koła\nnaukowego!");
        }
    }

    public void clickButtonUnassign() {
        int tmpId = comboboxClubs.getSelectionModel().getSelectedIndex();
        Kolanaukowe k =  clubsList.get(tmpId);
        List<Udzialwkole> participantsList = (new ClubParticipationModel()).findByClub(k);

        Integer uid = table.getSelectionModel().getSelectedItem().getID();


        participantsList.stream().filter(p -> p.getIdUcznia().equals(uid)).findAny().ifPresent(tmp -> (new ClubParticipationModel()).delete(tmp));
        studentList.clear();
        refreshClubList(tmpId);
        setParticipants(tmpId);
    }

    public void changeComboboxClubs() {
        studentList.clear();
        int index = comboboxClubs.getSelectionModel().getSelectedIndex();
        if (index != -1)
            setParticipants(comboboxClubs.getSelectionModel().getSelectedIndex());
    }

    private void setParticipants(int index) {

        Integer clubId = clubsList.get(index).getID();

        studentList = FXCollections.observableArrayList((new Student()).getStudentInClub(clubId));

        columnClass.setCellValueFactory(CellData -> new SimpleStringProperty((new SchoolClass()).getClassById(CellData.getValue().getIdKlasy()).getNumer()));
        columnStudent.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie() + " " + CellData.getValue().getNazwisko()));

        table.setItems(studentList);
    }



}
