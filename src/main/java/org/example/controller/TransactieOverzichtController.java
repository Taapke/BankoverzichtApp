package org.example.controller;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.LocalDateStringConverter;
import org.example.App;
import org.example.database.PostDAO;
import org.example.database.PostenOverzichtDAO;
import org.example.database.TransactieOverzichtDAO;
import org.example.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieOverzichtController {




    public TableView<Post> transactieTableView;
    public TableColumn boekingDatumColumn;
    public TableColumn saldoVoorMutatieColumn;
    public TableColumn transactieBedragColumn;
    public TableColumn omschrijvingColumn;
    public TableColumn postColumn;


    public Label transactionColumnLabels;

    @FXML
    private ListView<Transactie> listview;

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private ComboBox<String> monthSelect;
    @FXML
    private ComboBox<String> yearSelect;



    // Gets all transactions from database via TransactieOverzichtDAO and shows these in listview
    // First executed when switched to "TransactieOverzicht" view
    public void showTransactieOverzichtRegels() {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht;
        transactieOverzicht = transactieOverzichtDAO.geefAlleTransacties();
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());

        showTransactionsInListView(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    // Get selection transactions after specific date via TransactieOverzichtDAO and shows these in listview
    public void showTransactieRegelsVanafDatum(LocalDate fromDate) {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesVanafDatum(fromDate);
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        showTransactionsInListView(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    // Get selection transactions until specific date via TransactieOverzichtDAO and shows these in listview
    public void showTransactieRegelsTotDatum(LocalDate toDate) {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesTotDatum(toDate);
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        showTransactionsInListView(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    // Get selection transactions between specific dates via TransactieOverzichtDAO and shows these in listview
    public void showTransactieRegelsInPeriode(LocalDate fromDate, LocalDate toDate) {
        TransactieOverzichtDAO transactieOverzichtDAO = new TransactieOverzichtDAO(App.getDbAccess());
        TransactieOverzicht transactieOverzicht = transactieOverzichtDAO.geefTransactiesInPeriode(fromDate, toDate);
        ObservableList<Transactie> transacties = FXCollections.observableArrayList(transactieOverzicht.getTransacties());
        showTransactionsInListView(transacties);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    public void showTransactionsInListView(ObservableList<Transactie> transacties){
        transactionColumnLabels.setText(String.format("%s %s %s %s %s %s %s",
                "volgnummer", "boekingsdatum", "opdrachtgeverRekeningnummer", "saldoVoorMutatie",
                "transactieBedrag", "omschrijving", "tegenrekening"));
        listview.setItems(transacties);
    }

    // Set options in month and year dropdown menu
    public void setMonthYearSelect() {
        monthSelect.getItems().setAll("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        yearSelect.getItems().setAll("2022", "2021", "2020"); //TODO add years dynamically (to year first transaction)
    }

    public void getMonthYear(ActionEvent actionEvent) throws ParseException {
        String monthString = monthSelect.getValue().toUpperCase();
        Integer year = Integer.parseInt(yearSelect.getValue());
        int monthNumber = 1;
        try{
            Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(monthString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthNumber=cal.get(Calendar.MONTH) + 1;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        YearMonth yearMonth = YearMonth.of(year, monthNumber);
        int LengthOfMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayMonth = LocalDate.of(year, monthNumber, 1);
        LocalDate lastDayMonth = LocalDate.of(year, monthNumber, LengthOfMonth);
        showTransactieRegelsInPeriode(firstDayMonth, lastDayMonth);

    }



    // Gets value from datePickers and starts show functions
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

    // When pressed sets value of date-pickers to null
    public void clearFromDate(ActionEvent actionEvent) {
        fromDatePicker.setValue(null);
    }
    public void clearToDate(ActionEvent actionEvent) {
        toDatePicker.setValue(null);
    }


    public void backToHomeView(ActionEvent actionEvent) {
        App.loadHome(); //TODO Perhaps close connection here
    }
}
