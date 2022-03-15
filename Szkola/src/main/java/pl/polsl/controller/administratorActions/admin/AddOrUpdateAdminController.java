package pl.polsl.controller.administratorActions.admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.CredentialsGenerator;
import pl.polsl.entities.Administratorzy;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.AdminModel;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;
import pl.polsl.model.email.MailSenderModel;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static pl.polsl.utils.EmailMessages.*;

public class AddOrUpdateAdminController implements ParametrizedController, CredentialsGenerator {

    @FXML
    private TextField name;
    @FXML
    private TextField name2;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private Label title;
    @FXML
    private Button confirm;

    private Administratorzy toUpdate;

    public enum md {Add, Update}

    private md procedure = md.Update;
    private String mode;
    private Integer id;

    @FXML
    public void initialize() {
        name.textProperty().addListener(TextListener);
        surname.textProperty().addListener(TextListener);
        email.textProperty().addListener(TextListener);
        disableButton();
        checkPhone();
    }

    private boolean checkEmail() {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email.getText());
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private void checkPhone() {
        phone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d | \\+ | \\- | \\( | \\)*")) {
                    phone.setText(newValue.replaceAll("[^\\d & ^\\+ & ^\\- &^\\( & ^\\) *)]", ""));
                }
            }
        });
    }

    private void disableButton() {
        if (name.getText().isEmpty() || surname.getText().isEmpty() || email.getText().isEmpty())
            confirm.setDisable(true);
        else
            confirm.setDisable(false);
    }

    private ChangeListener TextListener = (observable, oldValue, newValue) -> {
        disableButton();
    };

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String) params.get("mode");
        id = (Integer) params.get("id");

        if (params.get("procedure") == "add") {
            procedure = md.Add;
            title.setText("Dodawanie administratora");
        } else {
            procedure = md.Update;
            toUpdate = (Administratorzy) params.get("admin");
            title.setText("Modyfikacja administratora");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getImie());
            name2.setText(toUpdate.getDrugieImie());
            surname.setText(toUpdate.getNazwisko());
            email.setText(toUpdate.getEmail());
            phone.setText(toUpdate.getNrKontaktowy());
        }
    }

    public void confirmChangesButton() throws IOException {
        if (procedure == md.Add) {
            Uzytkownicy u = new Uzytkownicy();
            Administratorzy a = new Administratorzy();
            if (setNewValues(a)) {
                wrongEmailAlert();
                return;
            }

            (new AdminModel()).persist(a);
            setNewValues(u, a.getImie(), a.getNazwisko(), a.getID());
            (new UserModel()).persist(u);
            MailSenderModel mail = new MailSenderModel();
            mail.setTopic(ACCOUNT_UPDATED_TOPIC);
            mail.setMessageText(LOGIN_READY_MESSAGE + u.getLogin() + "\n" + PASSWORD_READY_MESSAGE + u.getHaslo());
            mail.sendMail(a.getEmail());

        } else if (procedure == md.Update) {
            if (setNewValues(toUpdate)) {
                wrongEmailAlert();
                return;
            }
            (new Teacher()).update(toUpdate);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/admin/manageAdminForm", params, WindowSize.manageAdminForm);
    }

    private void wrongEmailAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawny email");
        alert.setHeaderText("Niepoprawny format emaila!!!");
        alert.setContentText(null);
        alert.showAndWait();
    }

    private boolean setNewValues(Administratorzy n) {
        n.setImie((name.getText().length() >= 45 ? name.getText().substring(0, 45) : name.getText()));
        n.setDrugieImie((name2.getText().length() >= 45 ? name2.getText().substring(0, 45) : name2.getText()));
        n.setNazwisko((surname.getText().length() >= 45 ? surname.getText().substring(0, 45) : surname.getText()));
        n.setNrKontaktowy((phone.getText().length() >= 45 ? phone.getText().substring(0, 45) : phone.getText()));
        if (checkEmail()) {
            n.setEmail((email.getText().length() >= 45 ? email.getText().substring(0, 45) : email.getText()));
            return false;
        }
        return true;
    }

    private void setNewValues(Uzytkownicy u, String name, String surname, Integer id) {
        u.setLogin(generateLogin(name, surname));
        u.setHaslo(generatePassword());
        u.setDostep(Roles.ADMIN);
        u.setID(id);
    }

    public void discardChangesButton() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/admin/manageAdminForm", params, WindowSize.manageAdminForm);
    }

}
