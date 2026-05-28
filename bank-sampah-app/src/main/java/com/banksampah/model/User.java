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

}