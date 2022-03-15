package pl.polsl.controller.studentActions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.NoteModel;
import pl.polsl.model.Student;
import pl.polsl.model.Teacher;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentNoteController implements ParametrizedController {

    @FXML
    private TableView<Uwagi> table;
    @FXML
    private TableColumn<Uwagi, String> columnTeacher;
    @FXML
    private TableColumn<Uwagi, String>  columnDesc;
    @FXML
    private ComboBox<String>  comboboxChildren;

    private String mode;
    private Integer id;
    private Integer id_child;
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

    }

    public Integer getWitdh(String text){
        return (int)(new Text(text)).getBoundsInLocal().getWidth();
    }

    public String wrapString(String wraping,Integer wid){
        Integer width = wid-15;
        if(getWitdh(wraping) < width) {
            return wraping;
        }
        StringBuilder result = new StringBuilder();
        String[] words = wraping.split(" ");
        List<Integer> sizewords = new ArrayList<>();

        for(String word : words){
            sizewords.add(getWitdh(word));
        }
        Integer act = 0;
        int i = 0;
        for(Integer size : sizewords) {
            if(size + act < width){
                result.append(words[i]).append(" ");
                act +=size+3;
            }
            else if (size + act >= width) {
                result.append("\n").append(words[i]).append(" ");
                act=size+3;
            }
            else {
                result.append("\n").append(words[i]).append("\n");
                act = 0;
            }
            i++;
        }


        return result.toString();
    }

    private void setTable(){
        ObservableList<Uwagi>  list = FXCollections.observableArrayList(new NoteModel().getStudentNote(id_child));

        columnDesc.setCellValueFactory(CellData -> {
            String tym = CellData.getValue().getTresc();
            return new ReadOnlyStringWrapper(wrapString(tym, (int)columnDesc.getWidth()));
        });

        columnTeacher.setCellValueFactory(CellData -> {
            Integer idTeacher = CellData.getValue().getIdNauczyciela();
            Nauczyciele act = (new Teacher()).getTeacherById(idTeacher);
            if (act != null)
                return new ReadOnlyStringWrapper(act.getImie() + " " + act.getNazwisko());
            else
                return new ReadOnlyStringWrapper("");
        });

        table.setItems(list);
    }

    public void changeComboboxChildren() {
        id_child = children.get(comboboxChildren.getSelectionModel().getSelectedIndex()).getID();
        setTable();
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
