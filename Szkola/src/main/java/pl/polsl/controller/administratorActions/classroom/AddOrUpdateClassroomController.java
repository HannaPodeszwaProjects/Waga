package pl.polsl.controller.administratorActions.classroom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Sale;
import pl.polsl.model.Classroom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddOrUpdateClassroomController implements ParametrizedController {
    public TextField name;
    public TextField size;
    public RadioButton yes;
    public RadioButton no;
    public Label title;
    public Button confirm;

    private Sale toUpdate;
    public enum md {Add, Update}
    private md procedure = md.Update;
    private boolean selectedYes;
    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        ToggleGroup group = new ToggleGroup();
        yes.setToggleGroup(group);
        no.setToggleGroup(group);
        name.textProperty().addListener(TextListener);
        disableButton();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    if(group.getSelectedToggle().equals(yes))
                    {
                        no.setSelected(false);
                        selectedYes = true;
                    }
                    else
                    {
                        yes.setSelected(false);
                        selectedYes = false;
                    }
                }
            }
        });

        size.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    size.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void disableButton()
    {
        if (name.getText().isEmpty())
            confirm.setDisable(true);
        else
            confirm.setDisable(false);
    }
    private ChangeListener TextListener = (observable, oldValue, newValue) -> {
        disableButton();
    };

    @Override
    public void receiveArguments(Map<String, Object> params) {

        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
        if (params.get("procedure") == "add") {
            procedure = md.Add;
            title.setText("Dodawanie sali:");
        }
        else {
            procedure = md.Update;
            toUpdate = (Sale) params.get("classroom");
            title.setText("Modyfikacja sali:");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getNazwa());
            size.setText(toUpdate.getLiczbaMiejsc().toString());
            selectedYes=toUpdate.getCzyJestRzutnik();
            if(toUpdate.getCzyJestRzutnik())
                yes.setSelected(true);
        }
    }

    public void confirmChangesButton() throws IOException
    {
        if (procedure == md.Add) {
            Sale s = new Sale();
            setNewValues(s);

            (new Classroom()).persist(s);
        } else if (procedure == md.Update) {
            setNewValues(toUpdate);
            (new Classroom()).update(toUpdate);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/classroom/manageClassroomsForm", params, WindowSize.manageClassroomsForm);
    }

    private void setNewValues(Sale s)
    {
        s.setNazwa( (name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
        s.setLiczbaMiejsc(null);
        if(!(size.getText().isEmpty()))
        s.setLiczbaMiejsc(Integer.parseInt(size.getText()));
        s.setCzyJestRzutnik(selectedYes);
    }

    public void discardChangesButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/classroom/manageClassroomsForm", params, WindowSize.manageClassroomsForm);
    }
}
