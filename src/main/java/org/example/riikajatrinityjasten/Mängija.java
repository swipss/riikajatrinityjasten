package org.example.riikajatrinityjasten;

public class Mängija {
    private String nimi;
    private int skoor;

    public Mängija(String nimi, int skoor) {
        this.nimi = nimi;
        this.skoor = skoor;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getSkoor() {
        return skoor;
    }

    public void setSkoor(int skoor) {
        this.skoor = skoor;
    }

    public void lisaPunkte(int punktid) {
        this.skoor += punktid;
    }
}
