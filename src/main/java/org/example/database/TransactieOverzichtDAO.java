package org.example.database;

import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 * //TODO heb ik deze DAO nog nodig nu we displayTransactionDAO hebben?
 */

public class TransactieOverzichtDAO extends AbstractDAO{

    public TransactieOverzichtDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public TransactieOverzicht geefAlleTransacties() {
        String sql = "SELECT transactie.volgnummer, boekingsdatum, opdrachtgeversrekening, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving FROM transactie;";
        TransactieOverzicht transactieOverzicht = new TransactieOverzicht();
        try {
            setupPreparedStatement(sql);
            selectTransactieOverzicht(transactieOverzicht);
        } catch (SQLException e) {
            System.out.println("fout in geefAlleTransacties");
            System.out.println(e.getMessage());
        }
        return transactieOverzicht;

    }

    public TransactieOverzicht geefTransactiesVanafDatum(LocalDate datum) {
        String sql = "SELECT transactie.volgnummer, boekingsdatum, opdrachtgeversrekening, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving FROM transactie " +
                "WHERE boekingsdatum >= ?";
        TransactieOverzicht transactieOverzicht = new TransactieOverzicht();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, datum.toString());
            selectTransactieOverzicht(transactieOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactieOverzicht;
    }

    public TransactieOverzicht geefTransactiesTotDatum(LocalDate datum) {
        String sql = "SELECT volgnummer, boekingsdatum, opdrachtgeversrekening, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving FROM transactie " +
                "WHERE boekingsdatum <= ?";
        TransactieOverzicht transactieOverzicht = new TransactieOverzicht();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, datum.toString());
            selectTransactieOverzicht(transactieOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactieOverzicht;
    }

    public TransactieOverzicht geefTransactiesInPeriode(LocalDate vanafDatum, LocalDate totDatum) {
        String sql = "SELECT volgnummer, boekingsdatum, opdrachtgeversrekening, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving FROM transactie " +
                "WHERE boekingsdatum >= ? && boekingsdatum <= ?";
        TransactieOverzicht transactieOverzicht = new TransactieOverzicht();
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, vanafDatum.toString());
            preparedStatement.setString(2, totDatum.toString());
            selectTransactieOverzicht(transactieOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactieOverzicht;
    }

    private void selectTransactieOverzicht(TransactieOverzicht transactieOverzicht) throws SQLException {
        try (ResultSet resultSet = executeSelectStatement()) {
            while (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                transactieOverzicht.addTransactie(new Transactie(
                        resultSet.getInt("transactie.volgnummer"),
                        LocalDate.parse(resultSet.getString("boekingsdatum"), formatter),
                        resultSet.getString("opdrachtgeversrekening"),
                        resultSet.getDouble("saldo_voor_mutatie"),
                        resultSet.getDouble("transactie_bedrag"),
                        resultSet.getString("omschrijving")));
            }
        }
    }

    public LocalDate convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localdate = LocalDate.parse(dateString, formatter);
        return localdate;
    }}