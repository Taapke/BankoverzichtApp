package org.example.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 * TODO delete this class?
 * Wrapper class that holds data from Transactie and Tegenrekening and Post to display in TransactieOverzichtView
 */

public class DisplayTransaction {
    private SimpleStringProperty boekingDatumSSP;
    private SimpleStringProperty saldoVoorMutatieSSP;
    private SimpleStringProperty transactieBedragSSP;
    private SimpleStringProperty omschrijvingSSP;
    private SimpleStringProperty postSSP;

    public DisplayTransaction(SimpleStringProperty boekingDatumSSP, SimpleStringProperty saldoVoorMutatieSSP,
                              SimpleStringProperty transactieBedragSSP, SimpleStringProperty omschrijvingSSP,
                              SimpleStringProperty postSSP) {
        this.boekingDatumSSP = boekingDatumSSP;
        this.saldoVoorMutatieSSP = saldoVoorMutatieSSP;
        this.transactieBedragSSP = transactieBedragSSP;
        this.omschrijvingSSP = omschrijvingSSP;
        this.postSSP = postSSP;
    }



    public DisplayTransaction() {
    }

    public String getBoekingDatumSSP() {
        return boekingDatumSSP.get();
    }

    public SimpleStringProperty boekingDatumSSPProperty() {
        return boekingDatumSSP;
    }

    public void setBoekingDatumSSP(String boekingDatumSSP) {
        this.boekingDatumSSP.set(boekingDatumSSP);
    }

    public String getSaldoVoorMutatieSSP() {
        return saldoVoorMutatieSSP.get();
    }

    public SimpleStringProperty saldoVoorMutatieSSPProperty() {
        return saldoVoorMutatieSSP;
    }

    public void setSaldoVoorMutatieSSP(String saldoVoorMutatieSSP) {
        this.saldoVoorMutatieSSP.set(saldoVoorMutatieSSP);
    }

    public String getTransactieBedragSSP() {
        return transactieBedragSSP.get();
    }

    public SimpleStringProperty transactieBedragSSPProperty() {
        return transactieBedragSSP;
    }

    public void setTransactieBedragSSP(String transactieBedragSSP) {
        this.transactieBedragSSP.set(transactieBedragSSP);
    }

    public String getOmschrijvingSSP() {
        return omschrijvingSSP.get();
    }

    public SimpleStringProperty omschrijvingSSPProperty() {
        return omschrijvingSSP;
    }

    public void setOmschrijvingSSP(String omschrijvingSSP) {
        this.omschrijvingSSP.set(omschrijvingSSP);
    }

    public String getPostSSP() {
        return postSSP.get();
    }

    public SimpleStringProperty postSSPProperty() {
        return postSSP;
    }

    public void setPostSSP(String postSSP) {
        this.postSSP.set(postSSP);
    }
}
