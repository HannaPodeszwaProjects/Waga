package pl.polsl.controller.teacherActions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.model.ClubModel;
import pl.polsl.utils.WindowSize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeacherAddNewClubController implements ParametrizedController {
    Integer loggedTeacherId;

    @FXML
    private TextField nameTextField;
    @FXML
    private Label infoLabel;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        loggedTeacherId = (Integer) params.get("id");
        infoLabel.setText("");
    }

    public void clickButtonBack() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", loggedTeacherId);
        Main.setRoot("teacherActions/teacherClubForm", params, WindowSize.teacherClubForm);
    }

    public void clickButtonAdd() throws IOException {
        infoLabel.setText("");
        String name = nameTextField.getText();
        if(name.isEmpty()){
            infoLabel.setText("Nazwa koła naukowego nie może być pusta!");
        } else {
            ObservableList<Kolanaukowe> clubList = FXCollections.observableList((new ClubModel()).findAll());
            if(clubList.stream().anyMatch(c -> c.getOpis().equals(name))){
                infoLabel.setText("Błąd! Koło naukowe " + name + " już istnieje!");
            } else {
                Kolanaukowe kl = new Kolanaukowe();
                kl.setOpis(name);
                kl.setIdNauczyciela(loggedTeacherId);
                (new ClubModel()).persist(kl);
                infoLabel.setText("Koło naukowe " + name + " utworzono pomyślnie");
            }
        }
    }

}
