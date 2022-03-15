package pl.polsl.controller.teacherActions;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Uwagi;
import pl.polsl.model.NoteModel;
import pl.polsl.utils.WindowSize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewNoteController implements ParametrizedController {

    @FXML
    private TextField textField;
    @FXML
    private Button buttonAdd;

    Integer id;
    Integer idStudent;
    Integer classCombobox;
    Integer studentCombobox;

    private final ChangeListener TextListener = (observable, oldValue, newValue) -> buttonAdd.setDisable(textField.getText().isEmpty());

    public void initialize() {
        textField.textProperty().addListener(TextListener);
        buttonAdd.setDisable(true);
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        id = (Integer) params.get("id");
        idStudent = (Integer) params.get("idStudent");
        classCombobox = (Integer) params.get("classCombobox");
        studentCombobox = (Integer) params.get("studentCombobox");
    }

    Map<String, Object> params() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("classCombobox", classCombobox);
        params.put("studentCombobox", studentCombobox);
        return params;
    }

    public void clickButtonCancel() throws IOException {
        Main.setRoot("teacherActions/teacherNoteForm", params(), WindowSize.teacherNoteForm);
    }

    public void clickButtonAdd() throws IOException {
        (new NoteModel()).persist(new Uwagi(textField.getText(), idStudent, id));
        Main.setRoot("teacherActions/teacherNoteForm", params(), WindowSize.teacherNoteForm);

    }


}
