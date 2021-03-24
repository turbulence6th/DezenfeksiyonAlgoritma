package com.otanrikulu;

import com.otanrikulu.hareket.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Uygulama {

    public static void main(String[] args) throws IOException {

        /**
         * 0 -> boş
         * 1 -> karakter  -> @
         * 2 -> hedef     -> x
         * 4 -> engel     -> #
         * 8 -> virüs     -> b
         *
         * Ek geçerli değerler: 3, 10
         */
        byte[][] harita = haritaOku();

        String sonuc = coz(harita);
        System.out.println(sonuc);
    }

    private static byte karakterdenByteDonustur(char c) {
        switch (c) {
            case ' ':
                return 0;
            case '@':
                return 1;
            case 'x':
                return 2;
            case '#':
                return 4;
            case 'b':
                return 8;
        }

        throw new RuntimeException("Hatalı karakter bulundu");
    }

    private static byte[][] haritaOku() throws IOException {
        List<String> satirListesi = Files.readAllLines(Path.of("harita.txt"));
        byte[][] harita = new byte[satirListesi.size()][];
        int virus = 0, hedef = 0, karakter = 0;
        for (int i = 0; i < satirListesi.size(); i++) {
            String satir = satirListesi.get(i);
            harita[i] = new byte[satir.length()];
            for (int c = 0; c < satir.length(); c++) {
                harita[i][c] = karakterdenByteDonustur(satir.charAt(c));
                if (harita[i][c] == 8) {
                    virus++;
                } else if (harita[i][c] == 2) {
                    hedef++;
                } else if (harita[i][c] == 1) {
                    karakter++;
                }
            }
        }

        if (virus != hedef) {
            throw new RuntimeException("x ve b sayısı eşit olmalıdır");
        } else if (karakter != 1) {
            throw new RuntimeException("1 tane karakter olmalıdır");
        }
        return harita;
    }

    public static String hashHesapla(byte[][] harita) {
        return Arrays.stream(harita)
                .map(Arrays::toString)
                .reduce("", String::concat);
    }

    public static String coz(byte[][] harita) {
        List<HareketEt> hareketListesi = List.of(new YukariGit(), new AsagiGit(), new SolaGit(), new SagaGit());
        Set<String> gecmisHaritalar = new HashSet<>();
        Koordinat karakterKonumu = null;
        disDongu: for (int y = 0; y < harita.length; y++) {
            for (int x = 0; x < harita[y].length; x++) {
                if (harita[y][x] == (byte) 1) {
                    karakterKonumu = new Koordinat(x, y);
                    break disDongu;
                }
            }
        }

        gecmisHaritalar.add(hashHesapla(harita));
        YiginDurumu yiginDurumu = new YiginDurumu(harita, karakterKonumu, hareketListesi, gecmisHaritalar, new StringBuilder());

        Deque<YiginDurumu> yigin = new ArrayDeque<>();
        yigin.push(yiginDurumu);

        NavigableMap<Integer, String> cozumler = new TreeMap<>();
        while (!yigin.isEmpty()) {
            YiginDurumu tepe = yigin.pop();
            StringBuilder sonuc = coz(tepe.harita, tepe.karakterKonumu, tepe.hareketListesi, tepe.gecmisHaritalar, tepe.mevcut, yigin);

            if (sonuc != null) {
                cozumler.put(sonuc.length(), sonuc.toString());
            }
        }

        return cozumler.firstEntry().getValue();
    }

    private static StringBuilder coz(byte[][] harita, Koordinat karakterKonumu, List<HareketEt> hareketListesi, Set<String> gecmisHaritalar, StringBuilder mevcut, Deque<YiginDurumu> yigin) {
        int haritaDurum = haritaCozulduMu(harita);

        if (haritaDurum == 2) {
            return mevcut;
        } else if (haritaDurum == 0) {
            return null;
        }

        for (HareketEt hareketEt: hareketListesi) {
            HaritaDegistir haritaDegistir = hareketEt.yerDegistir(harita, karakterKonumu);
            if (haritaDegistir == null) {
                continue;
            }

            if (!gecmisHaritalar.add(hashHesapla(haritaDegistir.getHarita()))) {
                continue;
            }

            StringBuilder mevcutKopya = new StringBuilder(mevcut);
            mevcutKopya.append(hareketEt.yon());

            yigin.push(new YiginDurumu(haritaDegistir.getHarita(), haritaDegistir.getKarakterKonumu(), hareketListesi, gecmisHaritalar, mevcutKopya));
        }

        return null;
    }

    private static int haritaCozulduMu(byte[][] harita) {
        boolean virusVar = false;
        for (int y = 0; y < harita.length; y++) {
            for (int x = 0; x < harita[y].length; x++) {
                byte bolge = harita[y][x];
                if (bolge == 8) {
                    virusVar = true;
                    int etrafaBak = 0;
                    if (harita[y - 1][x] == 4 || harita[y + 1][x] == 4) {
                        etrafaBak += 1;
                    }

                    if (harita[y][x - 1] == 4  || harita[y][x + 1] == 4 ) {
                        etrafaBak += 1;
                    }

                    if (etrafaBak == 2) {
                        return 0;
                    }
                }
            }
        }

        if (virusVar) {
            return 1;
        }

        return 2;
    }
}
