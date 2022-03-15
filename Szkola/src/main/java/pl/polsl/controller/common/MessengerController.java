package pl.polsl.controller.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessengerController implements ParametrizedController {

    @FXML
    private TableColumn<Map, String> senderRColumn;
    @FXML
    private TableColumn<Map, String> topicRColumn;
    @FXML
    private TableColumn<Map, Date> dateRColumn;
    @FXML
    private TableColumn<Map, String> receiverSColumn;
    @FXML
    private TableColumn<Map, String> topicSColumn;
    @FXML
    private TableColumn<Map, Date> dateSColumn;
    @FXML
    private TabPane messagesTabPane;
    @FXML
    private TableView<Map<String, Object>> receivedTable;
    @FXML
    private TableView<Map<String, Object>> sentTable;

    private String previousLocation;
    private Integer id;
    private String login;
    private String mode;
    private MessageModel messageModel;
    private List<Wiadomosci> receivedList;
    private List<Wiadomosci> sentList;
    private Boolean firstTabSelected;
    private UserModel userModel;

    @FXML
    public void initialize() {
        firstTabSelected = true;
        messageModel = new MessageModel();
        userModel = new UserModel();
        receivedTable.setRowFactory(table -> {
            TableRow<Map<String, Object>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Map<String, Object> item = row.getItem();
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("previousLocation", previousLocation);
                    parameters.put("mode", mode);
                    parameters.put("id", id);
                    parameters.put("topic", item.get("topicRColumn"));
                    parameters.put("date", item.get("dateRColumn"));
                    parameters.put("correspondent", item.get("senderRColumn"));
                    parameters.put("login", login);
                    parameters.put("type", "received");
                    try {
                        Main.setRoot("common/viewMessageForm", parameters, WindowSize.messageForm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        sentTable.setRowFactory(table -> {
            TableRow<Map<String, Object>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Map<String, Object> item = row.getItem();
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("previousLocation", previousLocation);
                    parameters.put("mode", mode);
                    parameters.put("id", id);
                    parameters.put("topic", item.get("topicSColumn"));
                    parameters.put("date", item.get("dateSColumn"));
                    parameters.put("correspondent", item.get("receiverSColumn"));
                    parameters.put("login", login);
                    parameters.put("type", "sent");
                    try {
                        Main.setRoot("common/viewMessageForm", parameters, WindowSize.viewMessageForm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        senderRColumn.setCellValueFactory(new MapValueFactory<>("senderRColumn"));
        topicRColumn.setCellValueFactory(new MapValueFactory<>("topicRColumn"));
        dateRColumn.setCellValueFactory(new MapValueFactory<>("dateRColumn"));
        receiverSColumn.setCellValueFactory(new MapValueFactory<>("receiverSColumn"));
        topicSColumn.setCellValueFactory(new MapValueFactory<>("topicSColumn"));
        dateSColumn.setCellValueFactory(new MapValueFactory<>("dateSColumn"));
        messagesTabPane.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    firstTabSelected = newTab.getId().equals("receivedTab");
                    if (firstTabSelected) displayReceivedMessages();
                    else displaySentMessages();
                });
        previousLocation = (String) params.get("previousLocation");
        id = (Integer) params.get("id");
        mode = (String) params.get("mode");
        login = userModel.getLoginByIdAndRole(id, mode);
        receivedList = messageModel.getReceivedMessagesByUserLogin(login);
        sentList = messageModel.getSentMessagesByUserLogin(login);
        displayReceivedMessages();
    }

    public void backButtonAction() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("mode", mode);
        WindowSize size;
        switch (mode) {
            case Roles.STUDENT:
                size = WindowSize.studentMenuForm;
                break;
            case Roles.PARENT:
                size = WindowSize.parentMenuForm;
                break;
            case Roles.TEACHER:
                size = WindowSize.teacherMenuForm;
                break;
            default:
                size = WindowSize.adminMenuForm;
                break;
        }
        Main.setRoot(previousLocation, params, size);
    }

    public void newMessageButtonAction() throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("previousLocation", previousLocation);
        parameters.put("mode", mode);
        parameters.put("id", id);
        Main.setRoot("common/messageForm", parameters, WindowSize.messagerForm);
    }

    public void refreshMessagesButtonAction() {
        if (firstTabSelected)
            displayReceivedMessages();
        else
            displaySentMessages();
    }

    private void displayReceivedMessages() {
        receivedTable.getItems().clear();
        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();
        String fullName;

        if (!receivedList.isEmpty()) {
            for (Wiadomosci message : receivedList) {
                Uzytkownicy user = userModel.getUserByLogin(message.getNadawca());
                if (user != null) {
                    fullName = getFullName(user.getDostep(), user.getID()) + " [" + message.getNadawca() + "]";
                    Map<String, Object> item = new HashMap<>();
                    item.put("senderRColumn", fullName);
                    item.put("topicRColumn", message.getTemat());
                    item.put("dateRColumn", message.getData());
                    items.add(item);
                }
            }
            receivedTable.getItems().addAll(items);
        }
    }

    private void displaySentMessages() {
        sentTable.getItems().clear();
        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();
        String fullName;

        if (!sentList.isEmpty()) {
            for (Wiadomosci message : sentList) {
                Uzytkownicy user = userModel.getUserByLogin(message.getOdbiorca());
                if (user != null) {
                    fullName = getFullName(user.getDostep(), user.getID()) + " [" + message.getOdbiorca() + "]";
                    Map<String, Object> item = new HashMap<>();
                    item.put("receiverSColumn", fullName);
                    item.put("topicSColumn", message.getTemat());
                    item.put("dateSColumn", message.getData());
                    items.add(item);
                }
            }
            sentTable.getItems().addAll(items);

        }
    }

    private String getFullName(String mode, int id) {
        switch (mode) {
            case Roles.STUDENT:
                Student studentModel = new Student();
                Uczniowie student = studentModel.getStudentById(id);
                return student.getImie() + " " + student.getNazwisko();
            case Roles.TEACHER:
                Teacher teacherModel = new Teacher();
                Nauczyciele teacher = teacherModel.getTeacherById(id);
                return teacher.getImie() + " " + teacher.getNazwisko();
            case Roles.PARENT:
                ParentModel parentModel = new ParentModel();
                Rodzice parent = parentModel.getParentById(id);
                return parent.getImie() + " " + parent.getNazwisko();
            default:
                return "";
        }
    }
}
