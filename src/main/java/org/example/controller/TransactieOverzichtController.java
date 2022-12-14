package org.example.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import org.example.App;
import org.example.database.DisplayTransactionDAO;
import org.example.database.PostDAO;
import org.example.database.PostenOverzichtDAO;
import org.example.database.TransactieOverzichtDAO;
import org.example.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieOverzichtController {


    public TableView<DisplayTransaction> transactieTableView;
    public TableColumn<DisplayTransaction, String>  boekingDatumColumn;
    public TableColumn<DisplayTransaction, String>  saldoVoorMutatieColumn;
    public TableColumn<DisplayTransaction, String>  transactieBedragColumn;
    public TableColumn<DisplayTransaction, String>  omschrijvingColumn;
    public TableColumn<DisplayTransaction, String> postColumn;
    public TableColumn<DisplayTransaction, DisplayTransaction> editPostColumn;






    public Label transactionColumnLabels;

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private ComboBox<String> monthSelect;
    @FXML
    private ComboBox<String> yearSelect;


    private void populateDisplayTransactionTable(ObservableList<DisplayTransaction> displayTransactions) {
        ObservableList<DisplayTransaction> observableList = FXCollections.observableArrayList();
        for (DisplayTransaction displayTransaction : displayTransactions) {
            LocalDate boekingDatum = displayTransaction.getBoekingDatumSSP();
            Double saldoVoorMutatie = displayTransaction.getSaldoVoorMutatieSSP();
            Double transactieBedrag = displayTransaction.getTransactieBedragSSP();
            String omschrijving = displayTransaction.getOmschrijvingSSP();
            String post = displayTransaction.getPostSSP();
            observableList.add(new DisplayTransaction(boekingDatum, saldoVoorMutatie, transactieBedrag, omschrijving, post));
        }
        transactieTableView.setItems(observableList);
        boekingDatumColumn.setCellValueFactory(new PropertyValueFactory<>("BoekingDatumSSP"));
        saldoVoorMutatieColumn.setCellValueFactory(new PropertyValueFactory<>("SaldoVoorMutatieSSP"));
        transactieBedragColumn.setCellValueFactory(new PropertyValueFactory<>("TransactieBedragSSP"));
        omschrijvingColumn.setCellValueFactory(new PropertyValueFactory<>("OmschrijvingSSP"));
        postColumn.setCellValueFactory(new PropertyValueFactory<>("PostSSP"));
        editPostColumn.setCellValueFactory(new PropertyValueFactory<>("button"));


    }




    // Gets all transactions from database via TransactieOverzichtDAO and shows these in listview
    // First executed when switched to "TransactieOverzicht" view
    public void showTransactieOverzichtRegels() {
          DisplayTransactionDAO displayTransactionDAO = new DisplayTransactionDAO(App.getDbAccess());
        ArrayList<DisplayTransaction> displayTransactionOverzicht = displayTransactionDAO.geefAlleDisplayTransacties();
        ObservableList<DisplayTransaction> displayTransactions = FXCollections.observableArrayList(displayTransactionOverzicht);
        populateDisplayTransactionTable(displayTransactions);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    // Get selection transactions after specific date via TransactieOverzichtDAO and shows these in listview
    public void showTransactieRegelsVanafDatum(LocalDate fromDate) {
        DisplayTransactionDAO displayTransactionDAO = new DisplayTransactionDAO(App.getDbAccess());
        ArrayList<DisplayTransaction> displayTransactionOverzicht = displayTransactionDAO.geefDisplayTransactionVanafDatum(fromDate);
        ObservableList<DisplayTransaction> displayTransactions = FXCollections.observableArrayList(displayTransactionOverzicht);
        populateDisplayTransactionTable(displayTransactions);


//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    // Get selection transactions until specific date via TransactieOverzichtDAO and shows these in listview
    public void showTransactieRegelsTotDatum(LocalDate toDate) {
        DisplayTransactionDAO displayTransactionDAO = new DisplayTransactionDAO(App.getDbAccess());
        ArrayList<DisplayTransaction> displayTransactionOverzicht = displayTransactionDAO.geefDisplayTransactionTotDatum(toDate);
        ObservableList<DisplayTransaction> displayTransactions = FXCollections.observableArrayList(displayTransactionOverzicht);
        populateDisplayTransactionTable(displayTransactions);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
    }

    // Get selection transactions between specific dates via TransactieOverzichtDAO and shows these in listview
    public void showTransactieRegelsInPeriode(LocalDate fromDate, LocalDate toDate) {
        DisplayTransactionDAO displayTransactionDAO = new DisplayTransactionDAO(App.getDbAccess());
        ArrayList<DisplayTransaction> displayTransactionOverzicht = displayTransactionDAO.geefDisplayTransactionsInPeriode(fromDate, toDate);
        ObservableList<DisplayTransaction> displayTransactions = FXCollections.observableArrayList(displayTransactionOverzicht);
        populateDisplayTransactionTable(displayTransactions);
//        App.getDbAccess().closeConnection(); //TODO close connection somewhere else
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

    // Back to home view
    public void backToHomeView(ActionEvent actionEvent) {
        App.loadHome(); //TODO Perhaps close connection here
    }
}
