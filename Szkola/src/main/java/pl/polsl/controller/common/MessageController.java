package pl.polsl.controller.common;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rodzice;
import pl.polsl.entities.Uczniowie;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.view.NotificationsInterface;
import java.io.IOException;
import java.util.*;

public class MessageController implements ParametrizedController, NotificationsInterface {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField receiverTextField;
    @FXML
    private TextField topicTextField;
    @FXML
    private TextArea messageTextArea;

    private String previousLocation;
    private Integer id;
    private String login;
    private String mode;
    private Student studentModel;
    private ParentModel parentModel;
    private Teacher teacherModel;
    private UserModel userModel;
    private MessageModel messageModel;
    private Boolean receiverSet;
    private Set<String> suggestionsSet;

    @FXML
    public void initialize() {
        receiverSet = false;
        suggestionsSet = new HashSet<>();
        studentModel = new Student();
        parentModel = new ParentModel();
        teacherModel = new Teacher();
        userModel = new UserModel();
        messageModel = new MessageModel();
        receiverTextField.textProperty().addListener(observable -> {
            errorLabel.setText("");
            receiverSet = false;
        });
        topicTextField.textProperty().addListener(observable -> errorLabel.setText(""));
        messageTextArea.textProperty().addListener(observable -> errorLabel.setText(""));
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        previousLocation = (String) params.get("previousLocation");
        id = (Integer) params.get("id");
        mode = (String) params.get("mode");
        login = userModel.getLoginByIdAndRole(id, mode);
        if (params.size() > 5) {
            receiverTextField.setText((String) params.get("correspondent"));
            receiverTextField.setDisable(true);
            topicTextField.setText((String) params.get("topic"));
            topicTextField.setDisable(true);
            receiverSet = true;
        }

        createSuggestions();
        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(receiverTextField, suggestionsSet);
        autoCompletionBinding.setOnAutoCompleted(event -> receiverSet = true);
    }

    public void cancelButtonAction() throws IOException {
        returnFromMessageWriter();
    }

    public void sendButtonAction() throws IOException {
        if (receiverTextField.getText().isEmpty())
            errorLabel.setText("Wprowadź odbiorcę");
        else if (topicTextField.getText().isEmpty())
            errorLabel.setText("Uzupełnij temat");
        else if (messageTextArea.getText().isEmpty())
            errorLabel.setText("Uzupełnij pole wiadomości");
        else {
            if (receiverSet) {
                String[] receiver = receiverTextField.getText().split(" ");
                receiver[2] = receiver[2].replace("[", "");
                String receiverLogin = receiver[2].replace("]", "");
                Uzytkownicy user = userModel.getUserByLogin(receiverLogin);
                if (user != null) {
                    if (messageTextArea.getText().length() < 600) {
                        if (sendMessage(login, user.getLogin())) {
                            showNotification("Potwierdzenie", "Wiadomość wysłana.", 2);
                            returnFromMessageWriter();
                        } else errorLabel.setText("Nie można wysłać wiadomości");
                    } else errorLabel.setText("Wiadomość jest za długa.");
                } else errorLabel.setText("Podany użytkownik nie istnieje.");
            } else errorLabel.setText("Wybierz kontakt z listy.");
        }
    }

    private Boolean sendMessage(String sender, String receiver) {
        return messageModel.insertMessage(
                topicTextField.getText(),
                messageTextArea.getText(),
                new Date(),
                receiver,
                sender
        );
    }

    private void createSuggestions() {
        switch (mode) {
            case Roles.TEACHER: {
                List<Uczniowie> students = studentModel.getAllStudents();
                List<Rodzice> parents = parentModel.getAllParents();
                for (Uczniowie student : students) {
                    suggestionsSet.add(student.getImie() + " " + student.getNazwisko() +
                            " [" + userModel.getLoginByIdAndRole(student.getID(), Roles.STUDENT) + "]");
                }
                for (Rodzice parent : parents) {
                    suggestionsSet.add(parent.getImie() + " " + parent.getNazwisko() +
                            " [" + userModel.getLoginByIdAndRole(parent.getID(), Roles.PARENT) + "]");
                }
                break;
            }
            case Roles.STUDENT:
            case Roles.PARENT: {
                List<Nauczyciele> teachers = teacherModel.getAllTeachers();
                for (Nauczyciele teacher : teachers) {
                    suggestionsSet.add(teacher.getImie() + " " + teacher.getNazwisko() +
                            " [" + userModel.getLoginByIdAndRole(teacher.getID(), Roles.TEACHER) + "]");
                }
                break;
            }
        }
    }

    private void returnFromMessageWriter() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", previousLocation);
        parameters.put("mode", mode);
        parameters.put("id", id);
        Main.setRoot("common/messengerForm", parameters, WindowSize.messagerForm);
    }

}
