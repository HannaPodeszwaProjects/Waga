package pl.polsl.controller.studentActions;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Udzialwkole;
import pl.polsl.model.ClubModel;
import pl.polsl.model.ClubParticipationModel;
import pl.polsl.model.Teacher;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentClubsController implements ParametrizedController {

    @FXML
    private Label clubDescription;
    @FXML
    private Label clubTeacher;
    @FXML
    private ComboBox<Kolanaukowe> comboBoxClubs;
    @FXML
    private TableView<Kolanaukowe> clubsTable;
    @FXML
    private TableColumn<Kolanaukowe, String> clubsColumn;
    @FXML
    private Button buttonApply;

    private Integer id;
    private String mode;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String) params.get("mode");
        id = (Integer) params.get("id");
        clubsTable.getItems().addAll((new ClubModel()).findByStudentId(id));
    }

    public void initialize() {
        buttonApply.setDisable(true);
        clubsColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
        comboBoxClubs.getItems().addAll((new ClubModel()).findAll());
        comboBoxClubs.getSelectionModel().selectedItemProperty().addListener ((observable, oldSelection, newSelection) -> {
            Nauczyciele teacher = (new Teacher()).getTeacherById(newSelection.getIdNauczyciela());
            clubTeacher.setText(teacher.getImie() + " " + teacher.getNazwisko() + "\n" + teacher.getEmail());
            clubDescription.setText(newSelection.getOpis());
            buttonApply.setDisable(false);
        });
    }

    public void clickButtonLeave() {
        Kolanaukowe club = clubsTable.getSelectionModel().getSelectedItem();
        if (club != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz opuścić koło naukowe \"" + club.getOpis() + "\"?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Opuszczanie koła");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
                Udzialwkole u = (new ClubParticipationModel()).findByBoth(id,clubsTable.getSelectionModel().getSelectedItem().getID());
                (new ClubModel()).delete(u);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Opuszczono koło.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Opuszczanie koła");
                done.showAndWait();
                refreshTable();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nie wybrano żadnego koła z tabeli.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Opuszczanie koła");
            alert.showAndWait();
        }
    }

    public void clickButtonApply() {
        Integer clubId = comboBoxClubs.getSelectionModel().getSelectedItem().getID();

        //Student is already participating in selected club
        if (clubsTable.getItems().stream().anyMatch(c -> c.getID().equals(clubId))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Jesteś już zapisany/a do tego koła naukowego.", ButtonType.OK);
            alert.setHeaderText("");
            alert.setTitle("Zapis do koła");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zapisać się do koła naukowego \"" + comboBoxClubs.getSelectionModel().getSelectedItem().getOpis() + "\"?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Zapis do koła");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK){
                Udzialwkole u = new Udzialwkole();
                u.setDataDolaczenia(new Date(System.currentTimeMillis()));
                u.setIdKola(clubId);
                u.setIdUcznia(id);
                (new ClubModel()).persist(u);
                Alert done = new Alert(Alert.AlertType.INFORMATION, "Zapisano się do koła.", ButtonType.OK);
                done.setHeaderText("");
                done.setTitle("Zapis do koła");
                done.showAndWait();
                refreshTable();
            }
        }
    }

    private void refreshTable() {
        int index = clubsTable.getSelectionModel().getSelectedIndex();
        clubsTable.getItems().clear();
        clubsTable.getItems().addAll((new ClubModel()).findByStudentId(id));
        clubsTable.getSelectionModel().select(index);
        clubsTable.getFocusModel().focus(index);
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.STUDENT))
            Main.setRoot("menu/studentMenuForm", params, WindowSize.studentMenuForm);
        else
            Main.setRoot("menu/studentMenuForm", params,WindowSize.parentMenuForm);
    }
}
