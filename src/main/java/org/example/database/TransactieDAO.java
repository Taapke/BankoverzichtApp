package org.example.database;

import org.example.model.Tegenrekening;
import org.example.model.Transactie;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieDAO extends AbstractDAO {

    public TransactieDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public void save(Transactie transactie) {
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

    public void saveTransactiesToDatabase(ArrayList<String> transactieRegels) {
        ArrayList<String> regels = transactieRegels;
        for (String regel : regels) {
            save(csvRegelNaarTransactie(regel));
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
