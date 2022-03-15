package pl.polsl.controller.administratorActions.parent;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.model.email.MailSenderModel;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.administratorActions.CredentialsGenerator;
import pl.polsl.entities.*;
import pl.polsl.model.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.polsl.utils.EmailMessages.*;

public class AddOrUpdateParentController implements ParametrizedController, CredentialsGenerator {

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
    private TextField adress;
    @FXML
    private Label title;
    @FXML
    private Button confirm;
    @FXML
    private TableView<ParenthoodModel> tableStudents;
    @FXML
    private TableColumn<ParenthoodModel, String> nameC;
    @FXML
    private TableColumn<ParenthoodModel, String> surnameC;
    @FXML
    private TableColumn<ParenthoodModel, String> classC;
    @FXML
    private TableColumn<ParenthoodModel, CheckBox> chooseC;


    private Rodzice toUpdate;
    public enum md {Add, Update}
    private AddOrUpdateParentController.md procedure = AddOrUpdateParentController.md.Update;
    private   ObservableList<ParenthoodModel> parentchoodList = FXCollections.observableArrayList();
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

    private List <Integer> checkChildren()
    {
        List<Rodzicielstwo> parenthoodList = (new ParenthoodModel()).findByParent(toUpdate);
        List <Integer> studentsId =new ArrayList<>();
        for( Rodzicielstwo r: parenthoodList)
        {
            studentsId.add(r.getIdUcznia());
        }
        return studentsId;
    }

    private void displayStudents()
    {
        tableStudents.setEditable(true);
        tableStudents.getItems().clear();
        Student s = new Student();
        List <Uczniowie> l=s.getAllStudents();

        List <Integer> childrenId = new ArrayList<>();
        if(toUpdate!=null) {
           childrenId = checkChildren();
        }
        for (Uczniowie u : l) {
            ParenthoodModel p = new ParenthoodModel(u);
            if(toUpdate!=null && childrenId.contains(u.ID))
            {
                p.getChoose().setSelected(true);
            }
            parentchoodList.add(p);
        }

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        classC.setCellValueFactory(CellData -> {
            Integer idKlasy = CellData.getValue().getIdKlasy();
            String numerKlasy = (new SchoolClass()).getClassNumber(idKlasy);
            return new ReadOnlyStringWrapper(numerKlasy);
        });
        chooseC.setCellValueFactory(new PropertyValueFactory<ParenthoodModel, CheckBox>("choose"));

       tableStudents.setItems(parentchoodList);
    }


    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");

        if (params.get("procedure") == "add") {
            procedure = md.Add;
            title.setText("Dodawanie rodzica:");
        }
        else {
            procedure = md.Update;
            toUpdate = (Rodzice) params.get("parent");
            title.setText("Modyfikacja rodzica:");
        }

        if (toUpdate != null) {
            name.setText(toUpdate.getImie());
            name2.setText(toUpdate.getDrugieImie());
            surname.setText(toUpdate.getNazwisko());
            email.setText(toUpdate.getEmail());
            phone.setText(toUpdate.getNrKontaktowy());
            adress.setText(toUpdate.getAdres());
        }
        displayStudents();
    }

    public void confirmChangesButton() throws IOException
    {
        if (procedure == md.Add) {
            Uzytkownicy u = new Uzytkownicy();
            Rodzice r = new Rodzice();
            if(!setNewValues(r))
            {
                wrongEmailAlert();
                return;
            }

            (new ParentModel()).persist(r);
            setNewValues(u, r.getImie(), r.getNazwisko(), r.getID());
            (new UserModel()).persist(u);
            MailSenderModel mail = new MailSenderModel();
            mail.setTopic(ACCOUNT_UPDATED_TOPIC);
            mail.setMessageText(LOGIN_READY_MESSAGE + u.getLogin() + "\n" + PASSWORD_READY_MESSAGE + u.getHaslo());
            mail.sendMail(r.getEmail());
            addChildren(r);

        } else if (procedure == md.Update) {
            if(!setNewValues(toUpdate))
            {
                wrongEmailAlert();
                return;
            }
            (new ParentModel()).update(toUpdate);
            addChildren(toUpdate);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/parent/manageParentsForm", params, WindowSize.manageParentsForm);
    }

    private void wrongEmailAlert()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Niepoprawny email");
        alert.setHeaderText("Niepoprawny format emaila!!!");
        alert.setContentText(null);
        alert.showAndWait();
    }

    private void deleteChildren()
    {
        ParenthoodModel p = new ParenthoodModel((new Uczniowie()));
        List<Rodzicielstwo> l = p.findByParent(toUpdate);

        for(Rodzicielstwo r: l)
        {
            p.delete(r);
        }
    }

    private void addChildren(Rodzice parent)
    {
        if(procedure == md.Update)
        {
            deleteChildren();
        }

        for(ParenthoodModel ph : parentchoodList )
        {
            if(ph.choose.isSelected())
            {
                Rodzicielstwo r = new Rodzicielstwo();
                r.setIdRodzica(parent.getID());
                r.setIdUcznia(ph.ID);
                ph.persist(r);
            }
        }
    }

    private boolean setNewValues(Rodzice r)
    {
        r.setImie((name.getText().length() >= 45 ? name.getText().substring(0,45) : name.getText()));
        r.setDrugieImie((name2.getText().length() >= 45 ? name2.getText().substring(0,45) : name2.getText()));
        r.setNazwisko((surname.getText().length() >= 45 ? surname.getText().substring(0,45) : surname.getText()));
        r.setNrKontaktowy((phone.getText().length() >= 20 ? phone.getText().substring(0,20) : phone.getText()));
        r.setEmail((email.getText().length() >= 45 ? email.getText().substring(0,45) : email.getText()));
        if(checkEmail())
            r.setAdres((adress.getText().length() >= 45 ? adress.getText().substring(0,45) : adress.getText()));
        else
            return false;
        return true;
    }

    private void setNewValues(Uzytkownicy u, String name, String surname, Integer id)
    {
        u.setLogin(generateLogin(name,surname));
        u.setHaslo(generatePassword());
        u.setDostep(Roles.PARENT);
        u.setID(id);
    }

    public void discardChangesButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/parent/manageParentsForm", params, WindowSize.manageParentsForm);
    }

}
