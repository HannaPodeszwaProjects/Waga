package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentAbsenceController implements ParametrizedController {

    @FXML
    private TableView<AbsenceComboBoxModel> table;
    @FXML
    private TableColumn<AbsenceComboBoxModel, Date> columnDate;
    @FXML
    private TableColumn<AbsenceComboBoxModel, String> columnHour;
    @FXML
    private TableColumn<AbsenceComboBoxModel, CheckBox> columnCheck;
    @FXML
    private TableColumn<AbsenceComboBoxModel, String> columnSubject;
    @FXML
    private ComboBox<String> comboboxChildren;

    private String mode;
    private Integer id;
    private Integer id_child;
    private List<Nieobecnosci> list;
    private final ObservableList<AbsenceComboBoxModel> data = FXCollections.observableArrayList();
    private ObservableList<Uczniowie> children;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String) params.get("mode");
        id = (Integer) params.get("id");

        if (mode.equals(Roles.PARENT)) {
            children = FXCollections.observableArrayList((new Student()).getParentsChildren(id));


            if (!children.isEmpty()) {
                for (Uczniowie act : children) {
                    comboboxChildren.getItems().add(act.getImie() + " " + act.getNazwisko());
                }
                comboboxChildren.getSelectionModel().select(0);
                id_child = children.get(0).getID();
                setTable();
            }
        }
        else {
            comboboxChildren.setVisible(false);

            id_child = id;
            setTable();
        }

        if (mode.equals(Roles.STUDENT)) {//wyłączenie możliwości usprawiedliwiania dla ucznia
            for (AbsenceComboBoxModel a : data)
                a.Usp.setDisable(true);
        }
    }

    private void setTable() {
        list = (new AbsenceModel()).displayPresent(id_child);

        for (Nieobecnosci a : list) {
            data.add(new AbsenceComboBoxModel(a));
        }

        columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnCheck.setCellValueFactory(new PropertyValueFactory<>("czyUsp"));
        columnSubject.setCellValueFactory(CellData -> {
            Integer idPrzedmiotu = CellData.getValue().getPrzedmiotyId();
            String nazwaPrzedmiotu = (new Subject()).getSubjectName(idPrzedmiotu);
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });
        columnHour.setCellValueFactory(CellData -> {
            Integer idHour = CellData.getValue().getGodzina();
            String nazwaPrzedmiotu = (new LessonTimeModel()).getNumberById(idHour).toString();
            return new ReadOnlyStringWrapper(nazwaPrzedmiotu);
        });

        table.setItems(data);
    }

    private void saveData() {
        for (AbsenceComboBoxModel a : data) {
            if (a.Usp.isSelected() == (a.czyUsprawiedliwiona == 0)) {
                for (Nieobecnosci find : list) {
                    if (a.ID.equals(find.ID)) {
                        if (a.Usp.isSelected()) {
                            find.setCzyUsprawiedliwiona(1);
                        } else {
                            find.setCzyUsprawiedliwiona(0);
                        }
                        (new AbsenceModel()).update(find);
                        break;
                    }
                }
            }
        }
    }

    public void changeComboboxChildren() {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        saveData();
        list.clear();
        data.clear();
        setTable();
    }

    public void clickButtonBack() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.STUDENT))
            Main.setRoot("menu/studentMenuForm", params, WindowSize.studentMenuForm);
        else {
            Main.setRoot("menu/studentMenuForm", params, WindowSize.parentMenuForm);
            saveData();
        }

    }
}
