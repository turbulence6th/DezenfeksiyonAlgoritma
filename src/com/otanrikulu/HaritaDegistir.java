package com.otanrikulu;

public class HaritaDegistir {

    private byte[][] harita;
    private Koordinat karakterKonumu;

    public HaritaDegistir(byte[][] harita, Koordinat karakterKonumu) {
        this.harita = harita;
        this.karakterKonumu = karakterKonumu;
    }

    public byte[][] getHarita() {
        return harita;
    }

    public Koordinat getKarakterKonumu() {
        return karakterKonumu;
    }
}
