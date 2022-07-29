package org.example.model;

import java.util.ArrayList;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieOverzicht {
    private ArrayList<Transactie> transacties = new ArrayList<>();
    private double saldo;

    public TransactieOverzicht(ArrayList<Transactie> transacties) {
        this.transacties = transacties;
        this.berekenSaldo();
    }

    public TransactieOverzicht() {
        this.transacties = new ArrayList<>();
        this.saldo = 0;
    }

    public void addTransactie(Transactie transactie) {
        this.transacties.add(transactie);
    }

    public ArrayList<Transactie> getTransacties() {
        return transacties;
    }

    public double getSaldo() {
        return saldo;
    }

    public void berekenSaldo() {
        double newSaldo = 0;
        for (Transactie transactie : transacties) {
            newSaldo += transactie.getTransactieBedrag();
        }
        this.saldo = newSaldo;
    }

    @Override
    public String toString() {
        StringBuilder eenReturn = new StringBuilder();
        for (Transactie transactie : transacties) {
            eenReturn.append(String.format("%s\n", transactie.toString()));
        }
        return eenReturn.toString();
    }
}
