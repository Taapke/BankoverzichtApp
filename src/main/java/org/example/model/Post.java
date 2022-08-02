package org.example.model;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class Post {
    String naam;

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
}
