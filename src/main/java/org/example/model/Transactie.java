package org.example.model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class Transactie {
    private int volgnummer;
    private LocalDate boekingsdatum;
    private String opdrachtgeverRekeningnummer;
    private double saldoVoorMutatie;
    private double transactieBedrag;
    private String omschrijving;
    private Tegenrekening tegenrekening;

    public Transactie(int volgnummer, LocalDate boekingsdatum,
                      String opdrachtgeverRekeningnummer, double saldoVoorMutatie,
                      double transactieBedrag,
                      String omschrijving, Tegenrekening tegenrekening) {
        this.volgnummer = volgnummer;
        this.boekingsdatum = boekingsdatum;
        this.opdrachtgeverRekeningnummer = opdrachtgeverRekeningnummer;
        this.saldoVoorMutatie = saldoVoorMutatie;
        this.transactieBedrag = transactieBedrag;
        this.omschrijving = omschrijving;
        this.tegenrekening = tegenrekening;
    }

    public Transactie(int volgnummer, LocalDate boekingsdatum, String opdrachtgeverRekeningnummer,
                      double saldoVoorMutatie, double transactieBedrag, String omschrijving) {
        this(volgnummer, boekingsdatum, opdrachtgeverRekeningnummer,
                saldoVoorMutatie, transactieBedrag, omschrijving,
                new Tegenrekening());
    }

    public double getTransactieBedrag() {
        return transactieBedrag;
    }

    public int getVolgnummer() {
        return volgnummer;
    }

    public LocalDate getBoekingsdatum() {
        return boekingsdatum;
    }

    public String getOpdrachtgeverRekeningnummer() {
        return opdrachtgeverRekeningnummer;
    }

    public double getSaldoVoorMutatie() {
        return saldoVoorMutatie;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Tegenrekening getTegenrekening() {
        return tegenrekening;
    }


    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s",
                this.volgnummer, this.boekingsdatum, this.opdrachtgeverRekeningnummer, this.saldoVoorMutatie,
                this.transactieBedrag, this.omschrijving, this.tegenrekening);
    }
}
