package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.example.App;
import org.example.database.TransactieOverzichtDAO;
import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.time.LocalDate;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieOverzichtController {

    @FXML
    private ListView<Transactie> listview;

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;


    public void showTransactieOverzichtRegels() {

        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht;
        transactieOverzicht = transactieOverzichtDAO.geefAlleTransacties();
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        listview.setItems(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    public void showTransactieRegelsVanafDatum(LocalDate fromDate) {

        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesVanafDatum(fromDate);
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        listview.setItems(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    public void showTransactieRegelsTotDatum(LocalDate toDate) {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesTotDatum(toDate);
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        listview.setItems(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    public void showTransactieRegelsInPeriode(LocalDate fromDate, LocalDate toDate) {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesInPeriode(fromDate, toDate);
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        listview.setItems(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    public void getDates(ActionEvent event) {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        try {
            fromDate = fromDatePicker.getValue();

        } catch (Exception e) {
            clearFromDate(event);
        }
        try {
            toDate = toDatePicker.getValue();
        } catch (Exception e) {
            clearToDate(event);
        }
        if (fromDate != null && toDate == null) {
            showTransactieRegelsVanafDatum(fromDate);
        } else if (fromDate == null && toDate != null) {
            showTransactieRegelsTotDatum(toDate);
        } else if (fromDate != null && toDate != null) {
            showTransactieRegelsInPeriode(fromDate, toDate);
        } else {
            showTransactieOverzichtRegels();
        }
    }

    public void clearFromDate(ActionEvent actionEvent) {
        fromDatePicker.setValue(null);
    }
    public void clearToDate(ActionEvent actionEvent) {
        toDatePicker.setValue(null);
    }


}
