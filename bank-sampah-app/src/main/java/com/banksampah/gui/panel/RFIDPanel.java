package com.banksampah.gui.panel;

import com.banksampah.controller.NasabahController;
import com.banksampah.dto.UserResponse;
import com.banksampah.gui.component.RoundedButton;
import com.banksampah.gui.component.UITheme;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class RFIDPanel extends JPanel {

        private JTextField uidField;
        private RoundedButton cariButton;
        private JLabel resultLabel;

        private NasabahController nasabahController;

        public RFIDPanel() {
                nasabahController = new NasabahController();

                setLayout(new BorderLayout(0, 20));
                setOpaque(false);

                add(createInputSection(), BorderLayout.NORTH);
                add(createResultSection(), BorderLayout.CENTER);
        }

        private JPanel createInputSection() {
                JPanel panel = new JPanel(new BorderLayout(12, 12));
                panel.setOpaque(false);

                uidField = new JTextField();
                uidField.setFont(UITheme.BODY_FONT);
                uidField.setPreferredSize(new Dimension(280, 44));
                uidField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                                UITheme.PRIMARY_LIGHT,
                                                2,
                                                true),
                                BorderFactory.createEmptyBorder(
                                                8,
                                                12,
                                                8,
                                                12)));

                cariButton = new RoundedButton("Cari Nasabah", UITheme.PRIMARY);
                cariButton.setPreferredSize(new Dimension(160, 44));

                JPanel formPanel = new JPanel(new BorderLayout(12, 0));
                formPanel.setOpaque(false);
                formPanel.add(uidField, BorderLayout.CENTER);
                formPanel.add(cariButton, BorderLayout.EAST);

                JLabel hint = new JLabel("Tempelkan kartu pada reader atau ketik UID RFID secara manual.");
                hint.setFont(UITheme.SUBTITLE_FONT);
                hint.setForeground(UITheme.TEXT);

                panel.add(formPanel, BorderLayout.CENTER);
                panel.add(hint, BorderLayout.SOUTH);

                cariButton.addActionListener(e -> cariNasabahByUid());

                return panel;
        }

        private JPanel createResultSection() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setOpaque(false);

                resultLabel = new JLabel(
                                "<html><div style='text-align:center; color:#5a4b7a;'>" +
                                                "<h2>Belum ada data</h2>" +
                                                "<p>Masukkan UID RFID untuk melihat informasi nasabah.</p>" +
                                                "</div></html>",
                                JLabel.CENTER);

                resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                panel.add(resultLabel, BorderLayout.CENTER);

                return panel;
        }

        private void cariNasabahByUid() {
                String uid = uidField.getText().trim();

                if (uid.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "UID RFID tidak boleh kosong",
                                        "Validasi",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                cariButton.setEnabled(false);

                resultLabel.setText(
                                "<html><div style='text-align:center; color:#7b61c9;'>" +
                                                "<h2>Memproses...</h2>" +
                                                "<p>Sedang mencari data nasabah.</p>" +
                                                "</div></html>");

                SwingWorker<UserResponse, Void> worker = new SwingWorker<>() {

                        @Override
                        protected UserResponse doInBackground() throws Exception {
                                return nasabahController.findNasabahByUid(uid);
                        }

                        @Override
                        protected void done() {
                                try {
                                        UserResponse response = get();
                                        resultLabel.setText(formatResult(response));

                                } catch (Exception e) {
                                        resultLabel.setText(
                                                        "<html><div style='text-align:center; color:#b00020;'>" +
                                                                        "<h2>Data tidak ditemukan</h2>" +
                                                                        "<p>UID RFID belum terdaftar di sistem.</p>" +
                                                                        "</div></html>");
                                }

                                cariButton.setEnabled(true);
                        }
                };

                worker.execute();
        }

        private String formatResult(UserResponse response) {
                return "<html>" +
                                "<div style='font-family:Segoe UI; color:#3d315f; padding:10px;'>" +
                                "<h2>Data Nasabah Ditemukan</h2>" +

                                "<table cellpadding='6'>" +
                                "<tr><td><b>ID Nasabah</b></td><td>: " + response.getNasabahId() + "</td></tr>" +
                                "<tr><td><b>Nama</b></td><td>: " + response.getNamaLengkap() + "</td></tr>" +
                                "<tr><td><b>Tanggal Lahir</b></td><td>: " + response.getTanggalLahir() + "</td></tr>" +
                                "<tr><td><b>Alamat</b></td><td>: " + response.getAlamat() + "</td></tr>" +
                                "<tr><td><b>No HP</b></td><td>: " + response.getNoHp() + "</td></tr>" +
                                "<tr><td><b>Jenis Kelamin</b></td><td>: " + response.getJenisKelamin() + "</td></tr>" +
                                "<tr><td><b>UID RFID</b></td><td>: " + response.getUidRfid() + "</td></tr>" +
                                "<tr><td><b>NIK</b></td><td>: " + response.getNik() + "</td></tr>" +
                                "<tr><td><b>Status Aktif</b></td><td>: " + response.isActive() + "</td></tr>" +
                                "<tr><td><b>Status KYC</b></td><td>: " + response.getStatusKyc() + "</td></tr>" +
                                "<tr><td><b>Verified At</b></td><td>: " + response.getVerifiedAt() + "</td></tr>" +
                                "<tr><td><b>Verified By</b></td><td>: " + response.getVerifiedBy() + "</td></tr>" +
                                "</table>" +

                                "</div>" +
                                "</html>";
        }
}