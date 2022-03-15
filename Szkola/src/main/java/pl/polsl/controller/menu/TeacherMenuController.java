package pl.polsl.controller.menu;

import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class TeacherMenuController implements ParametrizedController {
    
    private Integer id;
    private String login;

    @Override
    public void receiveArguments(Map<String, Object> params) {
        id = (Integer) params.get("id");
        login = (String) params.get("login");

    }


    public void clickButtonGrades() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("teacherActions/teacherGradeForm", params, WindowSize.teacherGradeForm);
    }


    public void clickButtonAbsence() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("teacherActions/teacherAbsenceForm", params, WindowSize.teacherAbsenceForm);
    }

    public void clickButtonMessages() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", "menu/teacherMenuForm");
        parameters.put("mode", Roles.TEACHER);
        parameters.put("id", id);
        Main.setRoot("common/messengerForm", parameters, WindowSize.messagerForm);
    }

    public void clickButtonSchedule() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("teacherActions/teacherScheduleForm", params, WindowSize.teacherScheduleForm);
    }

    public void clickButtonNote() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("teacherActions/teacherNoteForm", params, WindowSize.teacherNoteForm);
    }

    public void clickButtonClubs() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("teacherActions/teacherClubForm", params, WindowSize.teacherClubForm);
    }

    public void clickButtonCompetitions() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Main.setRoot("teacherActions/teacherCompetitionForm", params, WindowSize.teacherCompetitionForm);
    }

    public void clickButtonLogout() throws IOException {
        Main.setRoot("common/signIn", WindowSize.signIn);
    }


}
