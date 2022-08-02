package org.example.model;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class Tegenrekening {

    private static final String DEFAULT_NAAM = "";
    private static final String DEFAULT_REKENINGNUMMER = "";

    private String rekeningnaam;
    private String rekeningnummer;

    public Tegenrekening(String rekeningnaam, String rekeningnummer) {
        this.rekeningnaam = rekeningnaam;
        this.rekeningnummer = rekeningnummer;
    }

    public Tegenrekening() {
        this(DEFAULT_NAAM, DEFAULT_REKENINGNUMMER);
    }

    public String getRekeningnaam() {
        return rekeningnaam;
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }
}
