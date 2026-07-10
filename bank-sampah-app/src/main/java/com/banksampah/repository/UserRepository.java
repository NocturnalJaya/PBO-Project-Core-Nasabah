package com.banksampah.repository;

import com.banksampah.config.DBConfig;
import com.banksampah.model.User;
import com.banksampah.mapper.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserRepository implements IUserRepository {

    @Override
    public int save(User user) {

        String sql = "INSERT INTO tb_nasabah " +
                "(uid_rfid, nik, biodata_id, status_aktif) " +
                "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUidRfid());
            ps.setString(2, user.getNik());
            ps.setInt(3, user.getBiodataId());
            ps.setBoolean(4, user.isActive());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                int idNasabah = generatedKeys.getInt(1);

                System.out.println("User berhasil disimpan! ID: " + idNasabah);

                return idNasabah;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public User findByNik(String nik) {

        String sql = "SELECT * FROM tb_nasabah WHERE nik = ?";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nik);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return UserMapper.mapToUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User findByUid(String uid) {

        String sql = "SELECT * FROM tb_nasabah WHERE uid_rfid = ?";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uid);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return UserMapper.mapToUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}