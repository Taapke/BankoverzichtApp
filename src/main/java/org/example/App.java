package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.controller.HomeController;
import org.example.controller.TransactieOverzichtController;
import org.example.database.DBAccess;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static DBAccess dbAccess = null;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

//    public static void loadTransactieOverzicht() {
//        try {
//            Parent parent = loadFXML("TransactieOverzicht");
//            scene.setRoot(parent);
//        } catch (IOException e) {
//            System.err.println("Unable to load TimeLog screen");        }
//    }

    public static void showTransactieOverzicht() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/TransactieOverzicht.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            TransactieOverzichtController transactieOverzichtController = fxmlLoader.getController();
            transactieOverzichtController.showTransactieOverzichtRegels();
            scene.setRoot(parent);
        } catch (IOException e) {
            System.err.println("Unable to load TimeLog screen");
        }
    }

    public static DBAccess getDbAccess() {
        if (dbAccess == null) {
            dbAccess = new DBAccess("Bankoverzicht", "user", "pwBankoverzicht");
            dbAccess.openConnection();
        }
        return dbAccess;
    }

    public static void main(String[] args) {
        launch();
    }

}