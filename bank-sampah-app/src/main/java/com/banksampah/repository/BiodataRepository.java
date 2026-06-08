package com.banksampah.repository;

import com.banksampah.config.DBConfig;
import com.banksampah.model.Biodata;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BiodataRepository implements IBiodataRepository {

    @Override
    public int save(Biodata biodata) {

        String sql = "INSERT INTO tb_biodata " +
                "(nama_lengkap, tanggal_lahir, alamat, no_hp, jenis_kelamin) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, biodata.getNamaLengkap());
            ps.setDate(2, Date.valueOf(biodata.getTanggalLahir()));
            ps.setString(3, biodata.getAlamat());
            ps.setString(4, biodata.getNoHp());
            ps.setString(5, biodata.getJenisKelamin());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                int idBaru = generatedKeys.getInt(1);
                System.out.println("Biodata berhasil disimpan dengan ID: " + idBaru);
                return idBaru;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public Biodata findById(int id) {

        String sql = "SELECT * FROM tb_biodata WHERE id = ?";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Biodata biodata = new Biodata();

                biodata.setId(rs.getInt("id"));
                biodata.setNamaLengkap(rs.getString("nama_lengkap"));
                biodata.setTanggalLahir(rs.getDate("tanggal_lahir").toLocalDate());
                biodata.setAlamat(rs.getString("alamat"));
                biodata.setNoHp(rs.getString("no_hp"));
                biodata.setJenisKelamin(rs.getString("jenis_kelamin"));

                return biodata;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}