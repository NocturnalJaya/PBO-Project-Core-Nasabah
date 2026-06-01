package com.banksampah.repository;

import com.banksampah.config.DBConfig;
import com.banksampah.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepository {

    @Override
    public void save(User user) {

        String sql = "INSERT INTO tb_nasabah " +
                "(uid_rfid, nik, biodata_id, status_aktif) " +
                "VALUES (?, ?, ?, ?)";

        try {

            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUidRfid());
            ps.setString(2, user.getNik());
            ps.setInt(3, user.getBiodataId());
            ps.setBoolean(4, user.isActive());

            ps.executeUpdate();

            System.out.println("User berhasil disimpan!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByNik(String nik) {
        return null;
    }

    @Override
    public User findByUid(String uid) {
        return null;
    }
}