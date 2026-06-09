package com.banksampah.gui.panel;

import com.banksampah.model.StatusKYC;
import com.banksampah.service.KYCService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class KYCPanel extends JPanel {

    private JTextField nasabahIdField;
    private JTextField adminField;
    private JButton cekButton;
    private JButton verifyButton;
    private JButton rejectButton;
    private JTextArea resultArea;

    private KYCService kycService;

    public KYCPanel() {
        kycService = new KYCService();

        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        nasabahIdField = new JTextField();
        adminField = new JTextField("Admin MBG");
        cekButton = new JButton("Cek Status KYC");
        verifyButton = new JButton("Verifikasi");
        rejectButton = new JButton("Tolak KYC");

        inputPanel.add(new JLabel("ID Nasabah"));
        inputPanel.add(nasabahIdField);

        inputPanel.add(new JLabel("Diverifikasi Oleh"));
        inputPanel.add(adminField);

        inputPanel.add(cekButton);
        inputPanel.add(verifyButton);

        inputPanel.add(new JLabel(""));
        inputPanel.add(rejectButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(resultArea, BorderLayout.CENTER);

        cekButton.addActionListener(e -> cekStatus());
        verifyButton.addActionListener(e -> updateStatus("verified"));
        rejectButton.addActionListener(e -> updateStatus("rejected"));
    }

    private void cekStatus() {
        try {
            int nasabahId = Integer.parseInt(nasabahIdField.getText().trim());

            StatusKYC kyc = kycService.findByNasabahId(nasabahId);

            if (kyc == null) {
                resultArea.setText("Status KYC belum tersedia untuk nasabah ID: " + nasabahId);
                return;
            }

            tampilkanStatus(kyc);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Input ID Nasabah tidak valid",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatus(String status) {
        try {
            int nasabahId = Integer.parseInt(nasabahIdField.getText().trim());
            String verifiedBy = adminField.getText().trim();

            if (verifiedBy.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nama admin tidak boleh kosong",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            kycService.findByNasabahId(nasabahId);

            if (status.equals("verified")) {
                kycService.verifyKYC(nasabahId, verifiedBy);
            } else {
                kycService.rejectKYC(nasabahId, verifiedBy);
            }

            StatusKYC updatedKyc = kycService.findByNasabahId(nasabahId);
            tampilkanStatus(updatedKyc);

            JOptionPane.showMessageDialog(
                    this,
                    "Status KYC berhasil diubah menjadi: " + status,
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID Nasabah harus berupa angka",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Terjadi error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilkanStatus(StatusKYC kyc) {
        resultArea.setText(
                "=== STATUS KYC ===\n\n" +
                        "ID Status: " + kyc.getId() + "\n" +
                        "ID Nasabah: " + kyc.getNasabahId() + "\n" +
                        "Status: " + kyc.getStatusKyc() + "\n" +
                        "Verified At: " + kyc.getVerifiedAt() + "\n" +
                        "Verified By: " + kyc.getVerifiedBy());
    }
}