package pl.polsl.controller.menu;

import pl.polsl.Main;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import java.util.HashMap;
import java.util.Map;

public class StudentMenuController implements ParametrizedController{

    @FXML
    private Label labelTitle;
    @FXML
    private Button buttonClubs;
    @FXML
    private Button buttonCompetitions;
    @FXML
    private Button buttonLogout;

    private int id;
    private String mode;
    private String login;

    @Override
    public void receiveArguments(Map<String, Object> params) {

        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
        login = (String) params.get("login");
        if(Roles.PARENT.equals(mode)){
            labelTitle.setText("Konto rodzica");
            buttonClubs.setVisible(false);
            buttonCompetitions.setVisible(false);
            buttonLogout.setLayoutY(246);
        }
        else{
            labelTitle.setText("Konto ucznia");
        }
    }


    public void clickButtonGrades() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("studentActions/studentGradesForm", params, WindowSize.studentGradesForm);
    }

    public void clickButtonAbsence() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("studentActions/studentAbsenceForm",params, WindowSize.studentAbsenceForm);
    }


    public void buttonMessagesAction() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", "menu/studentMenuForm");
        parameters.put("id", id);
        parameters.put("mode", mode);
        Main.setRoot("common/messengerForm", parameters, WindowSize.messagerForm);
    }


    public void clickButtonSchedule() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("studentActions/studentScheduleForm", params, WindowSize.studentScheduleForm);
    }

    public void clickButtonNote() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("studentActions/studentNoteForm", params, WindowSize.studentNoteForm);
    }

    public void clickButtonClubs() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("studentActions/studentClubsForm", params, WindowSize.studentClubsForm);
    }

    public void clickButtonCompetitions() throws IOException {

        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("studentActions/studentCompetitionForm", params, WindowSize.studentCompetitionsForm);
    }

    public void clickButtonLogout() throws IOException {
        Main.setRoot("common/signIn", WindowSize.signIn);
    }
}
