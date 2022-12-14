package org.example.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class Post {
    private String naam;

    public Post(String naam) {
        this.naam = naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return String.format("%s", this.naam);
    }

    public String getNaam() {
        return naam;
    }

    public SimpleStringProperty naamProperty() {
        return new SimpleStringProperty((String) naam);
    }
}
