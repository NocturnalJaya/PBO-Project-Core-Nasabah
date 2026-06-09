package com.banksampah.controller;

import com.banksampah.dto.RegisterNasabahRequest;
import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.model.Biodata;
import com.banksampah.model.User;
import com.banksampah.service.BiodataService;
import com.banksampah.service.KYCService;
import com.banksampah.service.UserService;

import java.time.LocalDate;

public class NasabahController {

    private BiodataService biodataService;
    private UserService userService;
    private KYCService kycService;

    public NasabahController() {
        this.biodataService = new BiodataService();
        this.userService = new UserService();
        this.kycService = new KYCService();
    }

    public void registerNasabah(RegisterNasabahRequest request)
            throws DuplicateNIKException {

        validateRequest(request);

        /*
         * Cek NIK dulu sebelum simpan biodata.
         * Ini mencegah biodata yatim ketika NIK ternyata duplicate.
         */
        if (userService.isNikExists(request.getNik())) {
            throw new DuplicateNIKException("NIK sudah terdaftar");
        }

        Biodata biodata = new Biodata();
        biodata.setNamaLengkap(request.getNamaLengkap());
        biodata.setTanggalLahir(LocalDate.parse(request.getTanggalLahir()));
        biodata.setAlamat(request.getAlamat());
        biodata.setNoHp(request.getNoHp());
        biodata.setJenisKelamin(request.getJenisKelamin());

        int biodataId = biodataService.saveBiodata(biodata);

        User user = new User();
        user.setUidRfid(request.getUidRfid());
        user.setNik(request.getNik());
        user.setBiodataId(biodataId);
        user.setActive(true);

        int nasabahId = userService.registerUser(user);

        kycService.createPendingStatus(nasabahId);
    }

    private void validateRequest(RegisterNasabahRequest request) {

        if (request.getNamaLengkap() == null ||
                request.getNamaLengkap().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama lengkap tidak boleh kosong");
        }

        if (request.getTanggalLahir() == null ||
                request.getTanggalLahir().trim().isEmpty()) {
            throw new IllegalArgumentException("Tanggal lahir tidak boleh kosong");
        }

        if (request.getAlamat() == null ||
                request.getAlamat().trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tidak boleh kosong");
        }

        if (request.getNoHp() == null ||
                request.getNoHp().trim().isEmpty()) {
            throw new IllegalArgumentException("No HP tidak boleh kosong");
        }

        if (request.getNik() == null ||
                request.getNik().trim().isEmpty()) {
            throw new IllegalArgumentException("NIK tidak boleh kosong");
        }

        if (!request.getNik().matches("\\d{16}")) {
            throw new IllegalArgumentException("NIK harus terdiri dari 16 digit angka");
        }

        if (request.getUidRfid() == null ||
                request.getUidRfid().trim().isEmpty()) {
            throw new IllegalArgumentException("UID RFID tidak boleh kosong");
        }
    }
}