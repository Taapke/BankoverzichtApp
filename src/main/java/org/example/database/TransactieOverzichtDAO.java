package org.example.database;

import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class TransactieOverzichtDAO extends AbstractDAO{

    public TransactieOverzichtDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public TransactieOverzicht geefAlleTransacties() {
        String sql = "SELECT volgnummer, boekingsdatum, opdrachtgeversrekening, " +
                "saldo_voor_mutatie, transactie_bedrag, omschrijving FROM transactie ";
        TransactieOverzicht transactieOverzicht = new TransactieOverzicht();
        try {
            setupPreparedStatement(sql);
            selectTransactieOverzicht(transactieOverzicht);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transactieOverzicht;

    }

    public TransactieOverzicht geefTransactiesInPeriode(LocalDate datum) {
        String sql = "SELECT volgnummer, boekingsdatum, opdrachtgeversrekening, " +
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

    private void selectTransactieOverzicht(TransactieOverzicht transactieOverzicht) throws SQLException {
        try (ResultSet resultSet = executeSelectStatement()) {
            while (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                transactieOverzicht.addTransactie(new Transactie(
                        resultSet.getInt("volgnummer"),
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
    }

//    public ArrayList<Afdeling> geefAfdelingen() {
//        ArrayList<Afdeling> afdelingenLijst = new ArrayList<>();
//        String sql = "SELECT * FROM afdeling";
//        // Alternatief:
//        // String sql = "SELECT afdelingsnaam, afdelingsplaats FROM afdeling;
//        try {
//            setupPreparedStatement(sql);
//            ResultSet resultSet = executeSelectStatement();
//            while (resultSet.next()) {
//                afdelingenLijst.add(new Afdeling(
//                        resultSet.getString("afdelingsnaam"),
//                        resultSet.getString("afdelingsplaats")));
//            }
//        } catch (SQLException sqlFout) {
//            System.out.println(sqlFout
//            );
//        } return afdelingenLijst;
//    }
}
