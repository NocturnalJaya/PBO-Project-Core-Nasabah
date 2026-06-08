package com.banksampah.service;

import com.banksampah.model.StatusKYC;
import com.banksampah.repository.KYCRepository;

public class KYCService {

    private KYCRepository kycRepository;

    public KYCService() {
        this.kycRepository = new KYCRepository();
    }

    public void createPendingStatus(int nasabahId) {
        kycRepository.createPendingStatus(nasabahId);
    }

    public void verifyKYC(int nasabahId, String verifiedBy) {
        kycRepository.updateStatus(nasabahId, "verified", verifiedBy);
    }

    public void rejectKYC(int nasabahId, String verifiedBy) {
        kycRepository.updateStatus(nasabahId, "rejected", verifiedBy);
    }

    public StatusKYC findByNasabahId(int nasabahId) {
        return kycRepository.findByNasabahId(nasabahId);
    }
}