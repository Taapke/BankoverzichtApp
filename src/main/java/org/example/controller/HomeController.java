package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.database.TransactieDAO;
import org.example.model.Tegenrekening;
import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class HomeController {

    @FXML
    private TextField testTextField;
    @FXML
    private Button testButton;
    @FXML
    private Button addCSVButton;

    @FXML
    private ListView listview;

    @FXML
    private Button toTransactieOverzichtView;



    public void testTextButton(ActionEvent actionEvent) {
        System.out.println(testTextField.getText());

    }


    // Function to upload by user of CSV file to database
    public void addCSVButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
//            listview.getItems().add(selectedFile.getAbsolutePath()); //TODO dit gebruiken voor het tonen van transacties
            TransactieDAO transactieDAO = new TransactieDAO(App.getDbAccess());
            ArrayList<String> transactieRegels = transactieDAO.geefTransacties(selectedFile.getAbsolutePath());
            transactieDAO.saveTransactiesToDatabase(transactieRegels);
            App.getDbAccess().closeConnection();
        } else {
            System.out.println("File is not valid");
        }
    }

    public void showTransactieOverzichtView(ActionEvent actionEvent) {
        App.showTransactieOverzicht();
    }
}
