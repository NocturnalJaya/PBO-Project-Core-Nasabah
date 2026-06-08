package com.banksampah.repository;

import com.banksampah.model.StatusKYC;

public interface IKYCRepository {

    void createPendingStatus(int nasabahId);

    void updateStatus(int nasabahId, String statusKyc, String verifiedBy);

    StatusKYC findByNasabahId(int nasabahId);
}