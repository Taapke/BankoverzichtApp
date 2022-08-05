package org.example.database;

import javafx.geometry.Pos;
import org.example.model.Post;
import org.example.model.PostenOverzicht;
import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class PostenOverzichtDAO extends AbstractDAO {
    public PostenOverzichtDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public PostenOverzicht geefAllePosten() {
        String sql = "SELECT naam FROM Post";
        PostenOverzicht postenOverzicht = new PostenOverzicht();
        try {
            setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                postenOverzicht.addPost(new Post(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            System.out.println("fout in geefAlleTransacties");
            System.out.println(e.getMessage());
        }
        return postenOverzicht;
    }
}