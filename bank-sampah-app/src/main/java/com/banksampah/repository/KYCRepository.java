package com.banksampah.repository;

import com.banksampah.config.DBConfig;
import com.banksampah.model.StatusKYC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.time.LocalDateTime;

public class KYCRepository implements IKYCRepository {

    @Override
    public void createPendingStatus(int nasabahId) {

        String sql = "INSERT INTO tb_status_kyc " +
                "(nasabah_id, status_kyc, verified_at, verified_by) " +
                "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nasabahId);
            ps.setString(2, "pending");
            ps.setTimestamp(3, null);
            ps.setString(4, null);

            ps.executeUpdate();

            System.out.println("Status KYC pending berhasil dibuat!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(int nasabahId, String statusKyc, String verifiedBy) {

        String sql = "UPDATE tb_status_kyc " +
                "SET status_kyc = ?, verified_at = ?, verified_by = ? " +
                "WHERE nasabah_id = ?";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, statusKyc);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, verifiedBy);
            ps.setInt(4, nasabahId);

            ps.executeUpdate();

            System.out.println("Status KYC berhasil diperbarui!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StatusKYC findByNasabahId(int nasabahId) {

        String sql = "SELECT * FROM tb_status_kyc WHERE nasabah_id = ?";

        try {
            Connection conn = DBConfig.connect();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nasabahId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                StatusKYC statusKYC = new StatusKYC();

                statusKYC.setId(rs.getInt("id"));
                statusKYC.setNasabahId(rs.getInt("nasabah_id"));
                statusKYC.setStatusKyc(rs.getString("status_kyc"));

                Timestamp verifiedAt = rs.getTimestamp("verified_at");

                if (verifiedAt != null) {
                    statusKYC.setVerifiedAt(
                            verifiedAt.toLocalDateTime());
                }

                statusKYC.setVerifiedBy(
                        rs.getString("verified_by"));

                return statusKYC;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}