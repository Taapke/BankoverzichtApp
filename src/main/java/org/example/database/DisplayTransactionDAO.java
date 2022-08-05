package org.example.database;

import org.example.model.DisplayTransaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class DisplayTransactionDAO extends AbstractDAO{
    public DisplayTransactionDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public ArrayList<DisplayTransaction> geefAlleDisplayTransacties() {
        String sql = "SELECT boekingsdatum, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving, post FROM transactie " +
                "LEFT JOIN transactiepost ON transactie.volgnummer = transactiepost.volgnummer;";
        ArrayList<DisplayTransaction> displayTransactionsOverzicht = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            selectTransactieDisplayOverzicht(displayTransactionsOverzicht);
        } catch (SQLException e) {
            System.out.println("fout in geefAlleTransacties");
            System.out.println(e.getMessage());
        }
        return displayTransactionsOverzicht;

    }

    public ArrayList<DisplayTransaction> geefDisplayTransactionVanafDatum(LocalDate datum) {
        String sql = "SELECT boekingsdatum, saldo_voor_mutatie, transactie_bedrag, omschrijving, post " +
                "FROM transactie " +
                "LEFT JOIN transactiepost " +
                "ON transactie.volgnummer = transactiepost.volgnummer " +
                "WHERE boekingsdatum >= ?";
        ArrayList<DisplayTransaction> displayTransactionsOverzicht = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, datum.toString());
            selectTransactieDisplayOverzicht(displayTransactionsOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return displayTransactionsOverzicht;
    }

    public ArrayList<DisplayTransaction> geefDisplayTransactionTotDatum(LocalDate datum) {
        String sql = "SELECT boekingsdatum, saldo_voor_mutatie, transactie_bedrag, omschrijving, post " +
                "FROM transactie " +
                "LEFT JOIN transactiepost " +
                "ON transactie.volgnummer = transactiepost.volgnummer " +
                "WHERE boekingsdatum <= ?";
        ArrayList<DisplayTransaction> displayTransactionsOverzicht = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, datum.toString());
            selectTransactieDisplayOverzicht(displayTransactionsOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return displayTransactionsOverzicht;
    }

    public ArrayList<DisplayTransaction> geefDisplayTransactionsInPeriode(LocalDate vanafDatum, LocalDate totDatum) {
        String sql = "SELECT boekingsdatum, saldo_voor_mutatie, transactie_bedrag, omschrijving, post " +
                "FROM transactie " +
                "LEFT JOIN transactiepost " +
                "ON transactie.volgnummer = transactiepost.volgnummer " +
                "WHERE boekingsdatum >= ? && boekingsdatum <= ?";
        ArrayList<DisplayTransaction> displayTransactionsOverzicht = new ArrayList<>();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, vanafDatum.toString());
            preparedStatement.setString(2, totDatum.toString());
            selectTransactieDisplayOverzicht(displayTransactionsOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return displayTransactionsOverzicht;
    }

    private void selectTransactieDisplayOverzicht(ArrayList<DisplayTransaction> displayTransactionOverzicht) throws SQLException {
        try (ResultSet resultSet = executeSelectStatement()) {
            while (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                displayTransactionOverzicht.add(new DisplayTransaction(
                        LocalDate.parse(resultSet.getString("boekingsdatum"), formatter),
                        resultSet.getDouble("saldo_voor_mutatie"),
                        resultSet.getDouble("transactie_bedrag"),
                        resultSet.getString("omschrijving"),
                        resultSet.getString("post")));
            }
        }
    }
}
