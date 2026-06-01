package com.banksampah.model;

public class User {
    private int id;
    private String uidRfid;
    private String nik;
    private int biodataId;
    private boolean active;

    public User() {

    }

    public User(
            int id,
            String uidRfid,
            String nik,
            int biodataId,
            boolean active) {
        this.id = id;
        this.uidRfid = uidRfid;
        this.nik = nik;
        this.biodataId = biodataId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public String getNik() {
        return nik;
    }

    public int getBiodataId() {
        return biodataId;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public void setBiodataId(int biodataId) {
        this.biodataId = biodataId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}