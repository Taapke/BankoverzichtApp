package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.PostenOverzichtController;
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

    public static void loadHome() {
        try {
            Parent parent = loadFXML("Home");
            scene.setRoot(parent);
        } catch (IOException e) {
            System.err.println("Unable to load Home screen");        }
    }

    public static void showTransactieOverzicht() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/TransactieOverzicht.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            TransactieOverzichtController transactieOverzichtController = fxmlLoader.getController();
            transactieOverzichtController.showTransactieOverzichtRegels();
            transactieOverzichtController.setMonthYearSelect();
            scene.setRoot(parent);
        } catch (IOException e) {
            System.err.println("Unable to load Transactieoverzicht screen");
            System.out.println(e.getMessage());
        }
    }

    public static void showPostenOverzicht() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/PostenOverzicht.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            PostenOverzichtController postenController = fxmlLoader.getController();
            postenController.showPosten();
            scene.setRoot(parent);
        } catch (IOException e) {
            System.err.println("Unable to load PostenOverzicht screen");
            System.out.println(e.getMessage());
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