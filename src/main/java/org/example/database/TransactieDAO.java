package org.example.database;

import org.example.model.Tegenrekening;
import org.example.model.Transactie;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieDAO extends AbstractDAO {

    public TransactieDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public void saveTransactie(Transactie transactie) {
        String sql = "INSERT INTO transactie (volgnummer, boekingsdatum, opdrachtgeversrekening, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, transactie.getVolgnummer());
            preparedStatement.setString(2, transactie.getBoekingsdatum().toString());
            preparedStatement.setString(3, transactie.getOpdrachtgeverRekeningnummer());
            preparedStatement.setDouble(4, transactie.getSaldoVoorMutatie());
            preparedStatement.setDouble(5, transactie.getTransactieBedrag());
            preparedStatement.setString(6, transactie.getOmschrijving());
            executeManipulateStatement();
        } catch (SQLException sqlException) {
            System.err.println("An error occurred in SQL: " + sqlException.getMessage());
        }
    }

    public void saveTegenrekening(Tegenrekening tegenrekening) {
        String sql = "INSERT INTO tegenrekening (rekeningnummer, rekeningnaam) VALUES (?, ?);";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, tegenrekening.getRekeningnummer());
            preparedStatement.setString(2, tegenrekening.getRekeningnaam());
            executeManipulateStatement();
        } catch (SQLException sqlException) {
            System.err.println("An error occurred in SQL: " + sqlException.getMessage());
        }
    }

    public ArrayList<String> geefTransacties(String filepath) {
//        File transactieBestand = new File("src/main/resources/transacties/voorbeeldTransacties.csv");
        File transactieBestand = new File(filepath);
        ArrayList<String> transactieRegels = new ArrayList<>();
        try (Scanner invoer = new Scanner(transactieBestand)) {
            while (invoer.hasNextLine()) {
                transactieRegels.add(invoer.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        }
        return transactieRegels;
    }

    public void SaveTransactiesAndTegenrekeningToDatabase(ArrayList<String> transactieRegels) {
        ArrayList<String> regels = transactieRegels;
        Transactie newTransactie;
        for (String regel : regels) {
            newTransactie = csvRegelNaarTransactie(regel);
            saveTransactie(newTransactie);
            Tegenrekening tegenrekening = newTransactie.getTegenrekening();
            if ((!tegenrekening.getRekeningnaam().equals(""))
                    && !tegenrekening.getRekeningnaam().equals("")) {
                saveTegenrekening(newTransactie.getTegenrekening());
            }
        }

    }

    public Transactie csvRegelNaarTransactie(String csvRegel) {
        String[] kolommen = csvRegel.split(",");
        int volgnummer = Integer.parseInt(kolommen[15]);
        LocalDate boekingsdatum = convertStringToDate(kolommen[0]);
        String opdrachtgeverRekeningnummer = kolommen[1];
        double saldoVoorMutatie = Double.parseDouble(kolommen[8]);
        double transactieBedrag = Double.parseDouble(kolommen[10]);
        String omschrijving = kolommen[17];
        Tegenrekening tegenrekening = new Tegenrekening(kolommen[2], kolommen[3]);
        Transactie transactie = new Transactie(volgnummer, boekingsdatum, opdrachtgeverRekeningnummer,
                saldoVoorMutatie, transactieBedrag, omschrijving, tegenrekening);
        return transactie;
    }


    public LocalDate convertStringToDate(String dateString) { //TODO duplicate of this function in TransactieOverzichtDAO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localdate = LocalDate.parse(dateString, formatter);
        return localdate;
    }



}
