package com.banksampah.model;

import java.time.LocalDate;

public class Biodata {

    private int id;
    private String namaLengkap;
    private LocalDate tanggalLahir;
    private String alamat;
    private String noHp;
    private String jenisKelamin;

    public Biodata() {
    }

    public int getId() {
        return id;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}