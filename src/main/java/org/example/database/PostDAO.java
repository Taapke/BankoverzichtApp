package org.example.database;

import org.example.model.Post;

import java.sql.SQLException;


/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class PostDAO extends AbstractDAO{
    public PostDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public void savePost(Post post) {
        String sql = "INSERT INTO post (naam) VALUES (?);";
        try {
            setupPreparedStatement(sql);
            preparedStatement.setString(1, post.getNaam());
            executeManipulateStatement();
        } catch (SQLException sqlException) {
            System.err.println("An error occurred in SQL: " + sqlException.getMessage());
        }
    }
}
