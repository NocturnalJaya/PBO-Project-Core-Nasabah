package com.banksampah.controller;

import com.banksampah.dto.RegisterNasabahRequest;
import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.model.Biodata;
import com.banksampah.model.User;
import com.banksampah.service.BiodataService;
import com.banksampah.service.KYCService;
import com.banksampah.service.UserService;
import com.banksampah.dto.UserResponse;
import com.banksampah.exception.UserNotFoundException;
import com.banksampah.model.StatusKYC;
import com.banksampah.cache.UserCache;

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

    public UserResponse findNasabahByUid(String uid) throws UserNotFoundException {

        User user = userService.findByUid(uid);
        Biodata biodata = biodataService.findById(user.getBiodataId());
        StatusKYC kyc = kycService.findByNasabahId(user.getId());

        return mapToUserResponse(user, biodata, kyc);
    }

    public UserResponse findNasabahByNik(String nik) throws UserNotFoundException {

        if (UserCache.contains(nik)) {
            System.out.println("[CACHE HIT] Data nasabah diambil dari HashMap");
            return UserCache.get(nik);
        }

        System.out.println("[DATABASE HIT] Data nasabah diambil dari database");

        User user = userService.findByNik(nik);
        Biodata biodata = biodataService.findById(user.getBiodataId());
        StatusKYC kyc = kycService.findByNasabahId(user.getId());

        UserResponse response = mapToUserResponse(user, biodata, kyc);

        UserCache.put(nik, response);

        return response;
    }

    private UserResponse mapToUserResponse(User user, Biodata biodata, StatusKYC kyc) {

        UserResponse response = new UserResponse();

        response.setNasabahId(user.getId());
        response.setUidRfid(user.getUidRfid());
        response.setNik(user.getNik());
        response.setActive(user.isActive());

        response.setNamaLengkap(biodata.getNamaLengkap());
        response.setTanggalLahir(biodata.getTanggalLahir().toString());
        response.setAlamat(biodata.getAlamat());
        response.setNoHp(biodata.getNoHp());
        response.setJenisKelamin(biodata.getJenisKelamin());

        if (kyc != null) {
            response.setStatusKyc(kyc.getStatusKyc());

            if (kyc.getVerifiedAt() != null) {
                response.setVerifiedAt(kyc.getVerifiedAt().toString());
            } else {
                response.setVerifiedAt("-");
            }

            if (kyc.getVerifiedBy() != null) {
                response.setVerifiedBy(kyc.getVerifiedBy());
            } else {
                response.setVerifiedBy("-");
            }
        } else {
            response.setStatusKyc("belum tersedia");
            response.setVerifiedAt("-");
            response.setVerifiedBy("-");
        }

        return response;
    }

    public StatusKYC findKycStatus(int nasabahId) {
        return kycService.findByNasabahId(nasabahId);
    }

    public void verifyKyc(int nasabahId, String verifiedBy) {
        kycService.verifyKYC(nasabahId, verifiedBy);
    }

    public void rejectKyc(int nasabahId, String verifiedBy) {
        kycService.rejectKYC(nasabahId, verifiedBy);
    }
}