package com.otanrikulu.hareket;

import com.otanrikulu.HaritaDegistir;
import com.otanrikulu.Koordinat;

public abstract class HareketEt {

    protected abstract Koordinat konumHesapla(Koordinat koordinat);
    public abstract char yon();

    /**
     * Eğer geçerli bir durumsa yeni koordinatı döner. Değilse null döner.
     */
    public HaritaDegistir yerDegistir(byte[][] harita, Koordinat koordinat) {
        Koordinat sonrakiKonum = konumHesapla(koordinat);
        int skx = sonrakiKonum.getX();
        int sky = sonrakiKonum.getY();

        if (sky < 0 || skx < 0 || sky >= harita.length || skx >= harita[sky].length) {
            return null;
        }

        byte skb = harita[sky][skx];
        if (skb == 0 || skb == 2) {
            byte[][] yeniHarita = haritaKopyala(harita);
            yeniHarita[koordinat.getY()][koordinat.getX()] -= 1;
            yeniHarita[sonrakiKonum.getY()][sonrakiKonum.getX()] += 1;
            return new HaritaDegistir(yeniHarita, sonrakiKonum);
        }

        Koordinat dahaSonrakiKonum = konumHesapla(sonrakiKonum);
        int dsx = dahaSonrakiKonum.getX();
        int dsy = dahaSonrakiKonum.getY();

        if (dsy < 0 || dsx < 0 || dsy >= harita.length || dsx >= harita[dsy].length) {
            return null;
        }

        byte dsb = harita[dsy][dsx];
        if ((dsb == 0 || dsb == 2) && sahipMi(skb, (byte) 8)) {
            byte[][] yeniHarita = haritaKopyala(harita);
            yeniHarita[koordinat.getY()][koordinat.getX()] -= 1;
            yeniHarita[sonrakiKonum.getY()][sonrakiKonum.getX()] -= 7;
            yeniHarita[dahaSonrakiKonum.getY()][dahaSonrakiKonum.getX()] += 8;
            return new HaritaDegistir(yeniHarita, sonrakiKonum);
        }

        return null;
    }

    private static byte[][] haritaKopyala(byte[][] harita) {
        byte[][] kopyaHarita = new byte[harita.length][];
        for (int y = 0; y < harita.length; y++) {
            kopyaHarita[y] = new byte[harita[y].length];
            for (int x = 0; x < harita[y].length; x++) {
                kopyaHarita[y][x] = harita[y][x];
            }
        }
        return kopyaHarita;
    }

    private boolean sahipMi(byte bolge, byte deger) {
        return (bolge & deger) != 0;
    }
}
