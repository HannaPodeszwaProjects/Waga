package pl.polsl.controller.common;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import pl.polsl.Main;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.model.UserModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInController {

    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label errorLabel;

    private UserModel userModel;

    @FXML
    public void initialize() {
        userModel = new UserModel();
        loginTextField.textProperty().addListener((observable -> errorLabel.setText("")));

        passwordTextField.textProperty().addListener((observable -> errorLabel.setText("")));
    }

    public void signInAction() throws IOException {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            String login = loginTextField.getText();
            String password = passwordTextField.getText();
            Uzytkownicy user = userModel.getUserByLoginAndPassword(login, password);

            if (user != null) {
                Map<String, Object> params = new HashMap<>();
                switch (user.getDostep()) {
                    case Roles.STUDENT:
                        params.put("mode", Roles.STUDENT);
                        params.put("id", user.getID());
                        Main.setRoot("menu/studentMenuForm", params, WindowSize.studentMenuForm);
                        break;
                    case Roles.TEACHER:
                        params.put("id", user.getID());
                        Main.setRoot("menu/teacherMenuForm", params, WindowSize.teacherMenuForm);
                        break;
                    case Roles.PARENT:
                        params.put("mode", Roles.PARENT);
                        params.put("id", user.getID());
                        Main.setRoot("menu/studentMenuForm", params, WindowSize.parentMenuForm);
                        break;
                    case Roles.ADMIN:
                        params.put("mode", Roles.ADMIN);
                        params.put("id", user.getID());
                        Main.setRoot("menu/adminMenuForm", params, WindowSize.adminMenuForm);
                        break;
                    case Roles.PRINCIPAL:
                        params.put("mode", Roles.PRINCIPAL);
                        Main.setRoot("menu/adminMenuForm", params, WindowSize.principalMenuForm);
                        break;
                }
            } else errorLabel.setText("Błedny login lub hasło");

        } else errorLabel.setText("Wypełnij wszystkie pola");
    }

    public void resetPasswordAction() throws Exception {
        Main.setRoot("common/resetPasswordForm", WindowSize.resetPasswordForm);
    }

    public void textChanged() {
        errorLabel.setText("");
    }
}
