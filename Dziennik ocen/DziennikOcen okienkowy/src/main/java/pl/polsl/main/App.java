package pl.polsl.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import pl.polsl.model.Register;
import pl.polsl.controller.File1;

 /**
 * JavaFX App
 * The entry point of application
 *
 * @author Hanna Podeszwa
 * @version 3.1
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("options"), 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));

        return fxmlLoader.load();
    }

    /**
     * Main method of writing out the parameters. Creates the register and
     * starts the main menu
     *
     * @param args args[1] is name of the file with register
     */
    public static void main(String[] args) {

        String fileName = "test.txt";//"dziennik.txt";

        if (args.length != 0) {
            fileName = args[0];
        }
        Register register = new Register();
        File1 file = new File1();

        file.readFile(fileName, register);//create register
        launch();

        //fileName = "t.txt";
        file.writeFile(fileName, register); //writes the register to a file

    }

}
