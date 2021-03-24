package com.otanrikulu;

import com.otanrikulu.hareket.HareketEt;

import java.util.List;
import java.util.Set;

public class YiginDurumu {

    byte[][] harita;
    Koordinat karakterKonumu;
    List<HareketEt> hareketListesi;
    Set<String> gecmisHaritalar;
    StringBuilder mevcut;

    public YiginDurumu(byte[][] harita, Koordinat karakterKonumu, List<HareketEt> hareketListesi, Set<String> gecmisHaritalar, StringBuilder mevcut) {
        this.harita = harita;
        this.karakterKonumu = karakterKonumu;
        this.hareketListesi = hareketListesi;
        this.gecmisHaritalar = gecmisHaritalar;
        this.mevcut = mevcut;
    }
}
