package pl.polsl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.polsl.controller.*;
import pl.polsl.model.UserModel;
import pl.polsl.utils.WindowSize;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static Scene scene;
    private static Stage stage;
    private static final double defaultWidth = 600;
    private static final double defaultHeight =600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        scene = new Scene(loadFXML("common/signIn"), WindowSize.signIn.getWidth()-15,WindowSize.signIn.getHeight()-40);
        primaryStage.setTitle("Szkola");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String fxml, Map<String, Object> params, double... size) throws IOException {
        scene.setRoot(loadFXML(fxml, params));
        resizeScene(size);
    }
    public static void setRoot(String fxml,double... size) throws IOException {
        scene.setRoot(loadFXML(fxml));
        resizeScene(size);
    }

    public static void setRoot(String fxml, Map<String, Object> params, WindowSize size) throws IOException {
        scene.setRoot(loadFXML(fxml, params));
        resizeScene(size);
    }
    public static void setRoot(String fxml, WindowSize size) throws IOException {
        scene.setRoot(loadFXML(fxml));
        resizeScene(size);
    }

    public static void resizeScene(double[] size) {
        if (size.length >= 2) {
            stage.setWidth(size[0]);
            stage.setHeight(size[1]);
        } else {
            stage.setWidth(defaultWidth);
            stage.setHeight(defaultHeight);
         }
    }

    public static void resizeScene(WindowSize size) {
        stage.setWidth(size.getWidth());
        stage.setHeight(size.getHeight());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        return fxmlLoader.load();
    }

    private static Parent loadFXML(String fxml, Map<String, Object> params) throws IOException {
        URL url = Main.class.getResource("/view/" + fxml + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent ret = fxmlLoader.load();
        Object ob = fxmlLoader.getController();
        if (ob != null && ob instanceof ParametrizedController)
            ((ParametrizedController)ob).receiveArguments(params);
        return ret;
    }

    public static void main(String[] args) {
        MyManager m = MyManager.getInstance();
        launch(args);
        m.finalize();
    }
}
