package pl.polsl.controller.administratorActions.teacher;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.polsl.Main;
import pl.polsl.model.email.MailSenderModel;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.CredentialsGenerator;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.Teacher;
import pl.polsl.model.UserModel;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static pl.polsl.utils.EmailMessages.*;

public class AddOrUpdateTeachersController  implements ParametrizedController, CredentialsGenerator {

    public TextField name;
    public TextField name2;
    public TextField surname;
    public TextField email;
    public TextField phone;
    public Label title;
    public Button confirm;

    private Nauczyciele toUpdate;
    public enum md {Add, Update}
    private md procedure = md.Update;
    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        name.textProperty().addListener(TextListener);
        surname.textProperty().addListener(TextListener);
        email.textProperty().addListener(TextListener);
        disableButton();
        checkPhone();
    }

    private boolean checkEmail()
    {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email.getText());
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private void checkPhone()
    {
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

    private void disableButton()
    {
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
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");

        if (params.get("procedure") == "add") {
            procedure = md.Add;
            title.setText("Dodawanie nauczyciela:");
        }
        else {
            procedure = md.Update;
           toUpdate = (Nauczyciele) params.get("teacher");
            title.setText("Modyfikacja nauczyciela:");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getImie());
            name2.setText(toUpdate.getDrugieImie());
            surname.setText(toUpdate.getNazwisko());
            email.setText(toUpdate.getEmail());
            phone.setText(toUpdate.getNrKontaktowy());
        }
    }

    public void confirmChangesButton() throws IOException
    {
        if (procedure == md.Add) {
            Uzytkownicy u = new Uzytkownicy();
            Nauczyciele n = new Nauczyciele();
            if(!setNewValues(n))
            {
                wrongEmailAlert();
                return;
            }

            (new Teacher()).persist(n);
            setNewValues(u, n.getImie(), n.getNazwisko(), n.getID());
            (new UserModel()).persist(u);
            MailSenderModel mail = new MailSenderModel();
            mail.setTopic(ACCOUNT_UPDATED_TOPIC);
            mail.setMessageText(LOGIN_READY_MESSAGE + u.getLogin() + "\n" + PASSWORD_READY_MESSAGE + u.getHaslo());
            mail.sendMail(n.getEmail());

        } else if (procedure == md.Update) {
            if(!setNewValues(toUpdate))
            {
                wrongEmailAlert();
                return;
            }
            (new Teacher()).update(toUpdate);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/teacher/manageTeachersForm", params, WindowSize.manageTeachersForm);
    }

    private void wrongEmailAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawny email");
        alert.setHeaderText("Niepoprawny format emaila!!!");
        alert.setContentText(null);
        alert.showAndWait();
    }

    private boolean setNewValues(Nauczyciele n)
    {
        n.setImie((name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
        n.setDrugieImie((name2.getText().length() >= 45 ? name2.getText().substring(0,45) : name2.getText()));
        n.setNazwisko((surname.getText().length() >= 45 ? surname.getText().substring(0,45) : surname.getText()));
        n.setNrKontaktowy((phone.getText().length() >= 45 ? phone.getText().substring(0,45) : phone.getText()));
        if(checkEmail())
        n.setEmail((email.getText().length() >= 45 ? email.getText().substring(0,45) : email.getText()));
        else
            return false;
        return true;
    }

    private void setNewValues(Uzytkownicy u, String name, String surname, Integer id)
    {
       u.setLogin(generateLogin(name,surname));
       u.setHaslo(generatePassword());
       u.setDostep(Roles.TEACHER);
       u.setID(id);
    }

    public void discardChangesButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/teacher/manageTeachersForm", params, WindowSize.manageTeachersForm);
    }

}
