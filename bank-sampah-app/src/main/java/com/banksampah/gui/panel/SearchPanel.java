package com.banksampah.gui.panel;

import com.banksampah.exception.UserNotFoundException;
import com.banksampah.model.Biodata;
import com.banksampah.model.StatusKYC;
import com.banksampah.model.User;
import com.banksampah.service.BiodataService;
import com.banksampah.service.KYCService;
import com.banksampah.service.UserService;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    private JTextField nikField;
    private JButton cariButton;
    private JTextArea resultArea;

    private UserService userService;
    private BiodataService biodataService;
    private KYCService kycService;

    public SearchPanel() {
        userService = new UserService();
        biodataService = new BiodataService();
        kycService = new KYCService();

        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        nikField = new JTextField();
        cariButton = new JButton("Cari Nasabah");

        inputPanel.add(new JLabel("Masukkan NIK"));
        inputPanel.add(nikField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(cariButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        cariButton.addActionListener(e -> cariNasabah());
    }

    private void cariNasabah() {
        try {
            String nik = nikField.getText().trim();

            User user = userService.findByNik(nik);
            Biodata biodata = biodataService.findById(user.getBiodataId());
            StatusKYC kyc = kycService.findByNasabahId(user.getId());

            StringBuilder result = new StringBuilder();

            result.append("=== HASIL PENCARIAN NASABAH ===\n\n");
            result.append("Nama: ").append(biodata.getNamaLengkap()).append("\n");
            result.append("Tanggal Lahir: ").append(biodata.getTanggalLahir()).append("\n");
            result.append("Alamat: ").append(biodata.getAlamat()).append("\n");
            result.append("No HP: ").append(biodata.getNoHp()).append("\n");
            result.append("Jenis Kelamin: ").append(biodata.getJenisKelamin()).append("\n\n");

            result.append("UID RFID: ").append(user.getUidRfid()).append("\n");
            result.append("NIK: ").append(user.getNik()).append("\n");
            result.append("Status Aktif: ").append(user.isActive()).append("\n\n");

            if (kyc != null) {
                result.append("Status KYC: ").append(kyc.getStatusKyc()).append("\n");
                result.append("Verified At: ").append(kyc.getVerifiedAt()).append("\n");
                result.append("Verified By: ").append(kyc.getVerifiedBy()).append("\n");
            } else {
                result.append("Status KYC: belum tersedia\n");
            }

            resultArea.setText(result.toString());

        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Nasabah Tidak Ditemukan",
                    JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Terjadi error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}