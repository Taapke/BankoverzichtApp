package org.example.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 * TODO delete this class?
 * Wrapper class that holds data from Transactie and Tegenrekening and Post to display in TransactieOverzichtView
 */

public class DisplayTransaction {
    private ObjectProperty<LocalDate> boekingDatumSSP;
    private SimpleDoubleProperty saldoVoorMutatieSSP;
    private SimpleDoubleProperty transactieBedragSSP;
    private SimpleStringProperty omschrijvingSSP;
    private SimpleStringProperty postSSP;

    public DisplayTransaction(LocalDate boekingDatumSSP, Double saldoVoorMutatieSSP,
                              Double transactieBedragSSP, String omschrijvingSSP,
                              String postSSP) {
        this.boekingDatumSSP = new SimpleObjectProperty<>(boekingDatumSSP);
        this.saldoVoorMutatieSSP = new SimpleDoubleProperty(saldoVoorMutatieSSP);
        this.transactieBedragSSP = new SimpleDoubleProperty(transactieBedragSSP);
        this.omschrijvingSSP = new SimpleStringProperty(omschrijvingSSP);
        this.postSSP = new SimpleStringProperty(postSSP);
    }



    public DisplayTransaction() {
    }

    public LocalDate getBoekingDatumSSP() {
        return boekingDatumSSP.get();
    }

    public ObjectProperty<LocalDate> boekingDatumSPProperty() {
        return boekingDatumSSP;
    }

    public void setBoekingDatumSSP(LocalDate boekingDatumSSP) {
        this.boekingDatumSSP.set(boekingDatumSSP);
    }

    public Double getSaldoVoorMutatieSSP() {
        return saldoVoorMutatieSSP.get();
    }

    public SimpleDoubleProperty saldoVoorMutatieSPProperty() {
        return saldoVoorMutatieSSP;
    }

    public void setSaldoVoorMutatieSSP(Double saldoVoorMutatieSSP) {
        this.saldoVoorMutatieSSP.set(saldoVoorMutatieSSP);
    }

    public Double getTransactieBedragSSP() {
        return transactieBedragSSP.get();
    }

    public SimpleDoubleProperty transactieBedragSPProperty() {
        return transactieBedragSSP;
    }

    public void setTransactieBedragSSP(Double transactieBedragSSP) {
        this.transactieBedragSSP.set(transactieBedragSSP);
    }

    public String getOmschrijvingSSP() {
        return omschrijvingSSP.get();
    }

    public SimpleStringProperty omschrijvingSPProperty() {
        return omschrijvingSSP;
    }

    public void setOmschrijvingSSP(String omschrijvingSSP) {
        this.omschrijvingSSP.set(omschrijvingSSP);
    }

    public String getPostSSP() {
        return postSSP.get();
    }

    public SimpleStringProperty postSPProperty() {
        return postSSP;
    }

    public void setPostSSP(String postSSP) {
        this.postSSP.set(postSSP);
    }
}
