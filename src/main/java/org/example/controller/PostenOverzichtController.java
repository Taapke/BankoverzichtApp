package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.database.PostDAO;
import org.example.database.PostenOverzichtDAO;
import org.example.database.TransactieDAO;
import org.example.database.TransactieOverzichtDAO;
import org.example.model.Post;
import org.example.model.PostenOverzicht;
import org.example.model.Transactie;
import org.example.model.TransactieOverzicht;

import java.util.ArrayList;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class PostenOverzichtController {

    @FXML
    public ListView postListView;

    @FXML
    public TextField postTextField;

    public void backToHomeView(ActionEvent actionEvent) {
        App.loadHome();
    }

    public void showPosten() {
        PostenOverzichtDAO postenOverzichtDAO = new PostenOverzichtDAO(App.getDbAccess());
        PostenOverzicht postenOverzicht = postenOverzichtDAO.geefAllePosten();
        ObservableList<Post> posten = FXCollections.observableArrayList(postenOverzicht.getPostList());
        postListView.setItems(posten);
    }

    public void setNewPost(ActionEvent actionEvent) {
        PostDAO postDAO = new PostDAO(App.getDbAccess());
        Post post = new Post(postTextField.getText());
        postDAO.savePost(post);
        showPosten();
        App.getDbAccess().closeConnection();
    }
}
