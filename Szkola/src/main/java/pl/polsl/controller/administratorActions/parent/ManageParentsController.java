package pl.polsl.controller.administratorActions.parent;

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

public class ManageParentsController implements ParametrizedController {

    @FXML
    private TableView<Rodzice> tableParents;
    @FXML
    private TableColumn<Rodzice, String> nameC;
    @FXML
    private TableColumn<Rodzice, String> surnameC;
    @FXML
    private Label name2;
    @FXML
    private Label email;
    @FXML
    private Label adress;
    @FXML
    private Label phone;
    @FXML
    private ListView<String> children;
    @FXML
    private TextField searchT;
    @FXML
    private ComboBox<String> searchC;

    private String login;
    private ObservableList<Rodzice> parents;
    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        displayTableParents();
        displayCategories();
        changeLabels();
        search();

    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
    }

    private void search()
    {
        FilteredList<Rodzice> filter = new FilteredList(parents, p -> true);
        tableParents.setItems(filter);
        searchC.setValue("Nazwisko");

        searchT.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (searchC.getValue())
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

    private void changeLabels()
    {
        ObservableList<Rodzice> selectedParent = tableParents.getSelectionModel().getSelectedItems();
        selectedParent.addListener(new ListChangeListener<Rodzice>() {
            @Override
            public void onChanged(Change<? extends Rodzice> change) {
                if (selectedParent.size() != 0) {
                    String selectedName2 = selectedParent.get(0).getDrugieImie();
                    String selectedEmail = selectedParent.get(0).getEmail();
                    String selectedAdress = selectedParent.get(0).getAdres();
                    String selectedPhone = selectedParent.get(0).getNrKontaktowy();

                    name2.setText(selectedName2);
                    email.setText(selectedEmail);
                    adress.setText(selectedAdress);
                    phone.setText(selectedPhone);

                    children.getItems().clear();
                    ParenthoodModel p = new ParenthoodModel();
                    List<Rodzicielstwo> l = p.findByParent(selectedParent.get(0));

                    for (Rodzicielstwo r : l) {
                        Uczniowie selectedStudent = (new Student()).getStudentById(r.getIdUcznia());
                        children.getItems().add(selectedStudent.getImie() + " " + selectedStudent.getNazwisko());
                    }
                }
                else
                {
                    name2.setText(null);
                    email.setText(null);
                    adress.setText(null);
                    phone.setText(null);

                    children.getItems().clear();
                }
            }
        });
    }

    public void displayTableParents()
    {
        tableParents.getItems().clear();
        ParentModel p = new ParentModel();
        List l=p.getAllParents();
        parents = FXCollections.observableArrayList(l);

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Object r: l) {
            tableParents.getItems().add((Rodzice) r);
        }
    }

    public void addParentButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("procedure","add");
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/parent/addOrUpdateParentForm",params,
                WindowSize.addOrUpdateParentForm);
    }

    public void updateParentButton() throws IOException
    {
        Rodzice toUpdate = tableParents.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            chooseParentAlert(true);
        }
        else {
            Map<String, Object> params = new HashMap<>();
            params.put("parent", tableParents.getSelectionModel().getSelectedItem());
            params.put("procedure", "update");
            params.put("mode", mode);
            params.put("id", id);
            Main.setRoot("administratorActions/parent/addOrUpdateParentForm", params, WindowSize.addOrUpdateParentForm);
        }
    }

    public void deleteParentButton()
    {
        Rodzice toDelete = tableParents.getSelectionModel().getSelectedItem();

        if(toDelete==null)
        {
            chooseParentAlert(false);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie rodzica");
            String message = "Czy na pewno chcesz usunąć tego rodzica?";
            alert.setHeaderText(message);

            alert.setContentText(toDelete.getImie() + " " + toDelete.getNazwisko());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                deleteUser(toDelete);
                deleteMessages();
                deleteParenthood(toDelete);

                (new ParentModel()).delete(toDelete);
                parents.remove(toDelete);
            }
        }
    }

    private void deleteUser(Rodzice toDelete)
    {
        Uzytkownicy userToDelete = (new UserModel()).getUserByIdAndRole(toDelete.getID(), Roles.PARENT);
        login = userToDelete.getLogin();
        if(userToDelete !=null)
        {
            (new UserModel()).delete(userToDelete);
        }
    }

    private void deleteMessages()
    {
        List<Wiadomosci> messagesList = (new MessageModel()).findByParent(login);
        if(!(messagesList.isEmpty())) {
            for (Wiadomosci w: messagesList) {
                if(w.getNadawca().equals(login))
                {
                    w.setNadawca(null);
                }
                else
                {
                    w.setOdbiorca(null);
                }
                (new MessageModel()).update(w);
            }
        }
    }

    private void deleteParenthood(Rodzice toDelete)
    {
        List<Rodzicielstwo> parenthoodList = (new ParenthoodModel()).findByParent(toDelete);
        if(!(parenthoodList.isEmpty())) {
            for (Rodzicielstwo r: parenthoodList) {
                (new ParenthoodModel()).delete(r);
            }
        }
    }

    private void chooseParentAlert(boolean update)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(update) {
            alert.setTitle("Modyfikacja rodzica");
            alert.setContentText("Wybierz rodzica do modyfikacji.");
        }
        else {
            alert.setTitle("Usuwanie rodzica");
            alert.setContentText("Wybierz rodzica do usunięcia.");
        }
        alert.setHeaderText(null);
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
