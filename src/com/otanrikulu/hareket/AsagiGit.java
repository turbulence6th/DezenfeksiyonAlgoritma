package com.otanrikulu.hareket;

import com.otanrikulu.Koordinat;

public class AsagiGit extends HareketEt {

    @Override
    protected Koordinat konumHesapla(Koordinat koordinat) {
        return new Koordinat(koordinat.getX(), koordinat.getY() + 1);
    }

    @Override
    public char yon() {
        return 'D';
    }
}
