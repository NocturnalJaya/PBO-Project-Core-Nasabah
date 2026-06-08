package com.banksampah.app;

import java.time.LocalDate;

import com.banksampah.config.DBConfig;
import com.banksampah.model.Biodata;
import com.banksampah.model.User;
import com.banksampah.repository.BiodataRepository;
import com.banksampah.repository.UserRepository;
import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.exception.UserNotFoundException;
import com.banksampah.service.BiodataService;
import com.banksampah.service.KYCService;
import com.banksampah.service.UserService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        BiodataService biodataService = new BiodataService();
        UserService userService = new UserService();
        KYCService kycService = new KYCService();

        Biodata biodata = new Biodata();
        biodata.setNamaLengkap("Rahmat Hadi Wijaya");
        biodata.setTanggalLahir(LocalDate.of(2005, 1, 1));
        biodata.setAlamat("Malang");
        biodata.setNoHp("081234567890");
        biodata.setJenisKelamin("L");

        int biodataId = biodataService.saveBiodata(biodata);

        User user = new User();
        user.setUidRfid("TEST_UID_KYC_001");
        user.setNik("3377009988776655");
        user.setBiodataId(biodataId);
        user.setActive(true);

        try {
            int nasabahId = userService.registerUser(user);

            kycService.createPendingStatus(nasabahId);

            System.out.println("Registrasi lengkap berhasil!");
            System.out.println("Biodata ID: " + biodataId);
            System.out.println("Nasabah ID: " + nasabahId);
            System.out.println("Status KYC: pending");

        } catch (DuplicateNIKException e) {
            System.out.println("Registrasi gagal: " + e.getMessage());
        }
    }
}