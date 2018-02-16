package com.example.fikrihaikal.moneyflow;

/**
 * Created by Fikrihaikal on 31/01/2018.
 */

public class Anggaran {
    String angId;
    String angJenis;
    int angNominal;
    String angWaktu;
    String angBukti;
    String angKeterangan;

    public Anggaran() {

    }

    public Anggaran(String angId, String angJenis, int angNominal, String angWaktu, String angBukti,String angKeterangan) {
        this.angId = angId;
        this.angJenis = angJenis;
        this.angNominal = angNominal;
        this.angWaktu = angWaktu;
        this.angBukti = angBukti;
        this.angKeterangan = angKeterangan;
    }

    public String getAngId() {
        return angId;
    }

    public String getAngJenis() {
        return angJenis;
    }

    public  int getAngNominal() {
        return angNominal;
    }

    public String getAngWaktu() {
        return angWaktu;
    }

    public String getAngBukti() {
        return angBukti;
    }

    public String getAngKeterangan() {
        return angBukti;
    }
}
