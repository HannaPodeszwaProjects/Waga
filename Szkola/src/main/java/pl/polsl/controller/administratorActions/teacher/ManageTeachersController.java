package pl.polsl.controller.administratorActions.teacher;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageTeachersController implements ParametrizedController {

    @FXML
    private TableView<Nauczyciele> tableTeachers;
    @FXML
    private TableColumn<Nauczyciele, String> nameC;
    @FXML
    private TableColumn<Nauczyciele, String> surnameC;
    @FXML
    private Label name2;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private TextField searchT;
    @FXML
    private ComboBox searchC;

    private String login;
    private ObservableList<Nauczyciele> teachers;
    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        displayTableTeachers();
        changeLabels();
        displayCategories();
        search();
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
    }

    private void search()
    {

        FilteredList<Nauczyciele> filter = new FilteredList(teachers, p -> true);
        tableTeachers.setItems(filter);
        searchC.setValue("Nazwisko");

        searchT.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchC.getValue().toString())
            {
                case "Imię":
                    filter.setPredicate(p -> p.getImie().toLowerCase().startsWith(newValue.toLowerCase().trim()));//filter table by first name
                    break;
                case "Nazwisko":
                    filter.setPredicate(p -> p.getNazwisko().toLowerCase().startsWith(newValue.toLowerCase().trim()));//filter table by last name
                    break;
            }

        });
    }
    private void displayCategories()
    {
        searchC.getItems().add("Imię");
        searchC.getItems().add("Nazwisko");
    }

    public void displayTableTeachers()
    {
        tableTeachers.getItems().clear();
        Teacher t = new Teacher();
        List l=t.getAllTeachers();
        teachers = FXCollections.observableArrayList(l);

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Object n: teachers) {
            tableTeachers.getItems().add((Nauczyciele) n);
        }
    }

    private void changeLabels()
    {
        ObservableList<Nauczyciele> selectedTeacher = tableTeachers.getSelectionModel().getSelectedItems();
        selectedTeacher.addListener(new ListChangeListener<Nauczyciele>() {
            @Override
            public void onChanged(Change<? extends Nauczyciele> change) {
                if(selectedTeacher.size()!=0) {
                    String selectedName2 = selectedTeacher.get(0).getDrugieImie();
                    String selectedEmail = selectedTeacher.get(0).getEmail();
                    String selectedPhone = selectedTeacher.get(0).getNrKontaktowy();

                    name2.setText(selectedName2);
                    email.setText(selectedEmail);
                    phone.setText(selectedPhone);
                }
                else
                {
                    name2.setText(null);
                 email.setText(null);
                 phone.setText(null);
                }
                }
        });
    }

    public void addTeacherButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("procedure","add");
        params.put("mode", mode);
        params.put("id", id);
       Main.setRoot("administratorActions/teacher/addOrUpdateTeacherForm",params, WindowSize.addOrUpdateTeacherForm);
    }

    public void updateTeacherButton() throws IOException
    {
        Nauczyciele toUpdate = tableTeachers.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modyfikacja nauczyciela");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz nauczyciela do modyfikacji.");
            alert.showAndWait();
        }
        else {
            Map<String, Object> params = new HashMap<>();
            params.put("teacher", tableTeachers.getSelectionModel().getSelectedItem());
            params.put("procedure", "update");
            params.put("mode", mode);
            params.put("id", id);
            Main.setRoot("administratorActions/teacher/addOrUpdateTeacherForm", params, WindowSize.addOrUpdateTeacherForm);
        }
    }

    public void deleteTeacherButton()
    {
        Nauczyciele toDelete = tableTeachers.getSelectionModel().getSelectedItem();

        if(toDelete==null)
        {
          chooseTeacherAlert();
        }
        else {
            List<Klasy> classTutorList = (new Teacher()).checkTutor(toDelete); //find class where this teacher is tutor

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie nauczyciela");
            String message = "Czy na pewno chcesz usunąć tego nauczyciela?";
            if(!(classTutorList.isEmpty()))
            {
                message += " To wychowawca klasy: \n";
                for (Klasy k: classTutorList) {
                    message += k.getNumer();
                    message += "\n";
                }
            }
            alert.setHeaderText(message);

            alert.setContentText(toDelete.getImie() + " " + toDelete.getNazwisko());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                deleteTeacher(toDelete, classTutorList);
                teachers.remove(toDelete);
            }
        }
    }

   private void deleteTeacher(Nauczyciele toDelete, List <Klasy> classTutorList)
   {
       //delete from class
       if(!(classTutorList.isEmpty())) {
           for (Klasy k: classTutorList) {
               k.setIdWychowawcy(null); //delete this teacher from class
               (new SchoolClass()).update(k);
           }
       }

       //delete user
       Uzytkownicy userToDelete = (new UserModel()).getUserByIdAndRole(toDelete.getID(), Roles.TEACHER);
       login = userToDelete.getLogin();
       if(userToDelete !=null)
       {
           (new UserModel()).delete(userToDelete);
       }

        deleteMessages();
        deleteSchedule(toDelete);
        deleteNote(toDelete);
        deleteCompetition(toDelete);
        deleteClub(toDelete);

       (new Teacher()).delete(toDelete);
   }

    private void deleteNote(Nauczyciele toDelete)
    {
        List<Uwagi> teacherNoteList = (new NoteModel()).findByTeacher(toDelete);
        if(!(teacherNoteList.isEmpty())) {
            for (Uwagi u: teacherNoteList) {
                u.setIdNauczyciela(null);
                (new NoteModel()).update(u);
            }
        }
    }

    private void deleteCompetition(Nauczyciele toDelete)
    {
        List<Udzialwkonkursie> teacherCompetitionList = (new CompetitionModel()).findByTeacher(toDelete);
        if(!(teacherCompetitionList.isEmpty())) {
            for (Udzialwkonkursie u: teacherCompetitionList) {
                u.setIdNauczyciela(null);
                (new CompetitionModel()).update(u);
            }
        }
    }

    private void deleteClub(Nauczyciele toDelete)
    {
        List<Kolanaukowe> teacherClubList = (new ClubModel()).findByTeacher(toDelete);
        if(!(teacherClubList.isEmpty())) {
            for (Kolanaukowe k: teacherClubList) {
                k.setIdNauczyciela(null);
                (new ClubModel()).update(k);
            }
        }
    }

    private void deleteMessages() {
        List<Wiadomosci> messagesList = (new MessageModel()).findByTeacher(login);
        if (!(messagesList.isEmpty())) {

            for (Wiadomosci w : messagesList) {
                if (w.getNadawca().equals(login)) {
                    w.setNadawca(null);
                } else {
                    w.setOdbiorca(null);
                }
                (new MessageModel()).update(w);
            }
        }
            }



    private void deleteSchedule(Nauczyciele toDelete)
    {
        List<Rozklady> teacherScheduleList = (new ScheduleModel()).findByTeacher(toDelete);
        if(!(teacherScheduleList.isEmpty())) {
            for (Rozklady r: teacherScheduleList) {
                r.setIdNauczyciela(null);
                (new ScheduleModel()).update(r);
            }
        }
    }


    private void chooseTeacherAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuwanie nauczyciela");
        alert.setHeaderText(null);
        alert.setContentText("Wybierz nauczyciela do usunięcia.");
        alert.showAndWait();
    }

    public void cancelButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        if (mode.equals(Roles.PRINCIPAL))
            Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
        else
            Main.setRoot("menu/adminMenuForm", params, WindowSize.adminMenuForm);
    }


}
