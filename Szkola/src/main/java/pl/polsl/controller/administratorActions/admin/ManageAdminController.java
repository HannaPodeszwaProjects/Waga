package pl.polsl.controller.administratorActions.admin;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.polsl.Main;
import pl.polsl.controller.ParametrizedController;
import pl.polsl.entities.*;
import pl.polsl.model.*;
import pl.polsl.utils.Roles;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManageAdminController implements ParametrizedController {

    @FXML
    private Button buttonChangeDate;
    @FXML
    private Button buttonAddNewAdmin;
    @FXML
    private Button buttonUpdateAdmin;
    @FXML
    private Button buttonDeleteAdmin;
    @FXML
    private TableView<Administratorzy> tableAdmin;
    @FXML
    private TableColumn<Administratorzy, String> nameC;
    @FXML
    private TableColumn<Administratorzy, String> surnameC;
    @FXML
    private Label name2;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private TextField searchT;
    @FXML
    private ComboBox<String> searchC;

    private ObservableList<Administratorzy> admin;
    private String mode;
    private Integer id;

    @FXML
    public void initialize()
    {
        displayTableAdmin();
        changeLabels();
        displayCategories();
        search();
    }

    @Override
    public void receiveArguments(Map<String, Object> params) {
        mode = (String)params.get("mode");
        id = (Integer) params.get("id");
        if(mode.equals(Roles.ADMIN)){
            buttonAddNewAdmin.setVisible(false);
            buttonDeleteAdmin.setVisible(false);
            buttonUpdateAdmin.setVisible(false);
        }
        else{
            buttonChangeDate.setVisible(false);
        }

    }

    private void search()
    {
        FilteredList<Administratorzy> filter = new FilteredList(admin, p -> true);
        tableAdmin.setItems(filter);
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

    public void displayTableAdmin()
    {
        tableAdmin.getItems().clear();
        List<Administratorzy> l=(new AdminModel()).getAllAdmin();
        admin = FXCollections.observableArrayList(l);

        nameC.setCellValueFactory(new PropertyValueFactory<>("imie"));
        surnameC.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));

        for (Administratorzy n: admin) {
            tableAdmin.getItems().add(n);
        }
    }

    private void changeLabels()
    {
        ObservableList<Administratorzy> selectedAdmin = tableAdmin.getSelectionModel().getSelectedItems();
        selectedAdmin.addListener((ListChangeListener<Administratorzy>) change -> {
            if(selectedAdmin.size()!=0) {
                String selectedName2 = selectedAdmin.get(0).getDrugieImie();
                String selectedEmail = selectedAdmin.get(0).getEmail();
                String selectedPhone = selectedAdmin.get(0).getNrKontaktowy();

                name2.setText(selectedName2);
                email.setText(selectedEmail);
                phone.setText(selectedPhone);
            }
            else{
                name2.setText("");
                email.setText("");
                phone.setText("");
            }
            });
    }

    public void addAdminButton() throws IOException
    {
        Map<String, Object> params = new HashMap<>();
        params.put("procedure","add");
        params.put("mode", mode);
        params.put("id", id);
       Main.setRoot("administratorActions/admin/addOrUpdateAdminForm",params, WindowSize.addOrUpdateAdminForm);
    }

    public void changeDateButton() throws IOException {
        Administratorzy toUpdate = (new AdminModel()).getAdminById(id);
        Map<String, Object> params = new HashMap<>();
        params.put("admin", toUpdate);
        params.put("procedure", "update");
        params.put("mode", mode);
        params.put("id", id);
        Main.setRoot("administratorActions/admin/addOrUpdateAdminForm", params, WindowSize.addOrUpdateAdminForm);
    }

    public void updateAdminButton() throws IOException
    {
        Administratorzy toUpdate = tableAdmin.getSelectionModel().getSelectedItem();
        if(toUpdate==null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modyfikacja administratora");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz administratora do modyfikacji.");
            alert.showAndWait();
        }
        else {
            Map<String, Object> params = new HashMap<>();
            params.put("admin", toUpdate);
            params.put("procedure", "update");
            params.put("mode", mode);
            params.put("id", id);
            Main.setRoot("administratorActions/admin/addOrUpdateAdminForm", params, WindowSize.addOrUpdateAdminForm);
        }
    }

    public void deleteAdminButton()
    {
        Administratorzy toDelete = tableAdmin.getSelectionModel().getSelectedItem();

        if(toDelete==null)
        {
          chooseAdminAlert();
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie administratora");
            String message = "Czy na pewno chcesz usunąć tego administratora?";
            alert.setHeaderText(message);

            alert.setContentText(toDelete.getImie() + " " + toDelete.getNazwisko());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                deleteAdmin(toDelete);
                admin.remove(toDelete);
            }
        }
    }

   private void deleteAdmin(Administratorzy toDelete)
   {
       //delete user
       Uzytkownicy userToDelete = (new UserModel()).getUserByIdAndRole(toDelete.getID(), Roles.ADMIN);
       if(userToDelete !=null)
       {
           (new UserModel()).delete(userToDelete);
       }

       (new AdminModel()).delete(toDelete);
   }



    private void chooseAdminAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuwanie administratora");
        alert.setHeaderText(null);
        alert.setContentText("Wybierz administratora do usunięcia.");
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
