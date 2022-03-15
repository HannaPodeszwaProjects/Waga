
package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;
import pl.polsl.model.Grade;
import pl.polsl.model.Student;
import pl.polsl.model.Subject;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import java.io.IOException;
import java.util.*;

public class StudentGradesController implements ParametrizedController {

    @FXML
    private ComboBox<String> comboboxSubject;
    @FXML
    private TableView<Oceny> table;
    @FXML
    private TableColumn<Oceny, String> columnSubject;
    @FXML
    private TableColumn<Oceny, Integer> columnGrade;
    @FXML
    private TableColumn<Oceny, Date> columnDate;
    @FXML
    private TableColumn<Oceny, String> columnDescription;
    @FXML
    private TableColumn<Oceny, Integer> columnWeight;
    @FXML
    private ComboBox<String> comboboxChildren;

    private Integer id;
    private String mode;
    private Integer id_child;
    private ObservableList<Uczniowie> children;
    private List<Oceny> gradeList;
    private ObservableList<Przedmioty> subjectList;

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
                setSubject();
            }
        }
        else {
            comboboxChildren.setVisible(false);
            id_child = id;
            setSubject();
        }
    }

    void setSubject(){

        subjectList = FXCollections.observableArrayList((new Subject()).getSubjectForStudent(id_child));
        if(!subjectList.isEmpty()) {
            comboboxSubject.getItems().add("Wszystkie");
            for (Przedmioty act : subjectList) {
                comboboxSubject.getItems().add(act.getNazwa());
            }
            comboboxSubject.getSelectionModel().select(0);
            initTable();
        }
    }

    void tableSetItem(List<Oceny> Items){
        for(Oceny grade : Items)
            table.getItems().add(grade);
    }

    void initTable(){

        gradeList = FXCollections.observableArrayList((new Grade()).getGradeByStudent(id_child));
        columnGrade.setCellValueFactory(new PropertyValueFactory<>("ocena"));
        columnWeight.setCellValueFactory(new PropertyValueFactory<>("waga"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("opis"));
        columnSubject.setCellValueFactory(CellData -> {
            Integer idSubject = CellData.getValue().getIdPrzedmiotu();
            Optional<Przedmioty> opt = subjectList.stream().filter(a -> a.getID().equals(idSubject)).findFirst();
            Przedmioty subject = opt.orElse(null);
            String result = "";
            if (subject != null)
                result = subject.getNazwa();
            return new ReadOnlyStringWrapper(result);
        });

        tableSetItem(gradeList);
    }

    void setTable(){

        if(comboboxSubject.getSelectionModel().getSelectedIndex() == 0){
            tableSetItem(gradeList);
        }
        else{
            Integer subject = subjectList.get(comboboxSubject.getSelectionModel().getSelectedIndex()-1).getID();
            for(Oceny grade : gradeList) {
                if (grade.getIdPrzedmiotu().equals(subject))
                    table.getItems().add(grade);
            }
        }

    }

    public void changeComboboxChildren() {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        gradeList.clear();
        subjectList.clear();
        table.getItems().clear();
        comboboxSubject.getItems().clear();
        setSubject();
    }


    public void changeComboboxSubject() {
        if(comboboxSubject.getSelectionModel().getSelectedIndex()!=-1) {
            table.getItems().clear();
            setTable();
        }
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