package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.example.App;
import org.example.database.TransactieDAO;
import org.example.database.TransactieOverzichtDAO;
import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieOverzichtController {


    @FXML
    private ListView listview;

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;

    public void showTransactieOverzicht() {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        LocalDate localDate = LocalDate.of(2022, 6, 2);
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesInPeriode(localDate);
        ArrayList<Transactie> transacties = transactieOverzicht.getTransacties();
        for (Transactie transactie : transacties) {
            listview.getItems().add(transactie.toString());
        }
        App.getDbAccess().closeConnection();
//        listview.getItems().add(selectedFile.getAbsolutePath()); //TODO dit gebruiken voor het tonen van transacties
    }

    public void getDates(ActionEvent event) {
        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = fromDatePicker.getValue();
        } catch (Exception e) {
            System.out.println("No valid date");
            throw new RuntimeException(e);
        }
        try {
            toDate = toDatePicker.getValue();
        } catch (Exception e) {
            System.out.println("No valid date");
            throw new RuntimeException(e);
        }

        System.out.println(fromDate.toString());
        System.out.println(toDate.toString());



    }




}
