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
import java.util.List;

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

    public void showTransactieOverzichtRegels() {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht;
        if (fromDatePicker.getValue() != null) {
            System.out.println("not null");
//            transactieOverzicht = transactieOverzichtDAO.geefTransactiesInPeriode(fromDatePicker.getValue());
            transactieOverzicht = transactieOverzichtDAO.geefTransactiesInPeriode(fromDatePicker.getValue());
        } else {
            transactieOverzicht = transactieOverzichtDAO.geefAlleTransacties();
        }
        List<Transactie> transacties = transactieOverzicht.getTransacties();
        for (Transactie transactie : transacties) {
            listview.getItems().add(transactie.toString());
        } //TODO verder met transacties ophalen vanaf gegeven datum
        App.getDbAccess().closeConnection();
    }

    public void getDates(ActionEvent event) {
        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = fromDatePicker.getValue();
            System.out.println(fromDate.toString());
        } catch (Exception e) {
            clearFromDate(event);
        }
        try {
            toDate = toDatePicker.getValue();
            System.out.println(toDate.toString());
        } catch (Exception e) {
            clearToDate(event);
        }
        System.out.println("eindde van getDates functie");
        showTransactieOverzichtRegels();
    }

    public void clearFromDate(ActionEvent actionEvent) {
        fromDatePicker.setValue(null);
    }
    public void clearToDate(ActionEvent actionEvent) {
        toDatePicker.setValue(null);
    }
}
