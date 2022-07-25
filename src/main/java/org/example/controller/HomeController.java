package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class HomeController {

    @FXML
    private TextField testTextField;
    @FXML
    private Button testButton;

    public void testTextButton(ActionEvent actionEvent) {
        System.out.println(testTextField.getText());
    }

}
