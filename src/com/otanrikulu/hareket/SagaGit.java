package com.otanrikulu.hareket;

import com.otanrikulu.Koordinat;

public class SagaGit extends HareketEt {

    @Override
    protected Koordinat konumHesapla(Koordinat koordinat) {
        return new Koordinat(koordinat.getX() + 1, koordinat.getY());
    }

    @Override
    public char yon() {
        return 'R';
    }
}
