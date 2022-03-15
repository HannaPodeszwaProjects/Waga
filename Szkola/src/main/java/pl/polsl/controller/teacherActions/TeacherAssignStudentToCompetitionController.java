package pl.polsl.controller.teacherActions;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Udzialwkonkursie;
import pl.polsl.model.CompetitionModel;
import pl.polsl.model.SchoolClass;
import pl.polsl.utils.WindowSize;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherAssignStudentToCompetitionController implements ParametrizedController {

    @FXML
    private ComboBox<String> comboboxClass;
    @FXML
    private TableView<Uczniowie> table;
    @FXML
    private TableColumn<Uczniowie, String> columnName;
    @FXML
    private TableColumn<Uczniowie, String> columnSurname;
    @FXML
    private Label infoLabel;
    @FXML
    private Button buttonAdd;
    @FXML
    private TextField achievementText;

    Integer competitionId;
    Integer loggedTeacherId;
    ObservableList<Klasy> classList;
    ObservableList<Uczniowie> studentsList;
    List<Udzialwkonkursie> studentAdded;
    private Integer numberCompetition;

    private final ListChangeListener<? extends TablePosition> tableListener = (
            ListChangeListener.Change<? extends TablePosition> change) -> {
        if(table.getSelectionModel().getSelectedIndex()==-1) {
            buttonAdd.setDisable(true);
            infoLabel.setText("");
        }
        else if(studentAdded.stream().anyMatch(c -> c.getIdUcznia().equals(table.getSelectionModel().getSelectedItem().getID()))) {
            buttonAdd.setDisable(true);
            infoLabel.setText("Ten uczeń jest już przypisany do tego koła!");
        }
        else {
            buttonAdd.setDisable(false);
            infoLabel.setText("");
        }
    };

    @Override
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");
        competitionId = (Integer) params.get("competitionId");
        numberCompetition = (Integer) params.get("numberCompetition");

        classList = FXCollections.observableList((new SchoolClass()).displayClass());
        if (!classList.isEmpty()) {
            for (Klasy cl : classList) {
                comboboxClass.getItems().add(cl.getNumer());
            }
            comboboxClass.getSelectionModel().select(0);
            Klasy k = classList.get(0);
            setStudents((k));
        }
        infoLabel.setText("");
        buttonAdd.setDisable(true);
        table.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) tableListener);

        studentAdded = (new CompetitionModel()).findByCompetitionId(competitionId);

    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        params.put("numberCompetition", numberCompetition);
        Main.setRoot("teacherActions/teacherCompetitionForm", params, WindowSize.teacherCompetitionForm);
    }



    public void clickButtonAdd() throws IOException {
        infoLabel.setText("");
        String achievement = achievementText.getText();
        Integer uid = table.getSelectionModel().getSelectedItem().getID();
        Udzialwkonkursie parcomp = new Udzialwkonkursie();
        parcomp.setIdUcznia(uid);
        parcomp.setIdNauczyciela(loggedTeacherId);
        parcomp.setOsiagniecie(achievement);
        parcomp.setIdKonkursu(competitionId);
        (new CompetitionModel()).persist(parcomp);
        infoLabel.setText("Sukces! Uczeń został\nprzypisany do konkursu!");
        buttonAdd.setDisable(true);
    }





    public void changeComboboxClass() {
        studentsList.clear();
        Klasy k = classList.get(comboboxClass.getSelectionModel().getSelectedIndex());
        setStudents(k);
    }

    private void setStudents(Klasy k){
        studentsList = FXCollections.observableList((new SchoolClass()).getStudentsByClass(k));

        columnName.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getImie()));
        columnSurname.setCellValueFactory(CellData -> new SimpleStringProperty(CellData.getValue().getNazwisko()));


        table.setItems(studentsList);
    }
}
