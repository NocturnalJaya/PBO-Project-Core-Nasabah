package com.banksampah.model;

import java.time.LocalDateTime;

public class StatusKYC {

    private int id;
    private int nasabahId;
    private String statusKyc;
    private LocalDateTime verifiedAt;
    private String verifiedBy;

    public StatusKYC() {
    }

    public int getId() {
        return id;
    }

    public int getNasabahId() {
        return nasabahId;
    }

    public String getStatusKyc() {
        return statusKyc;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNasabahId(int nasabahId) {
        this.nasabahId = nasabahId;
    }

    public void setStatusKyc(String statusKyc) {
        this.statusKyc = statusKyc;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
}