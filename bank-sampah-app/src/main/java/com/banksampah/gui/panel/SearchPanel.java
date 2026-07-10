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

public class SearchPanel extends JPanel {

    private JTextField nikField;
    private RoundedButton cariButton;
    private JLabel resultLabel;

    private NasabahController nasabahController;

    public SearchPanel() {
        nasabahController = new NasabahController();

        setLayout(new BorderLayout(0, 20));
        setBackground(UITheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Pencarian Nasabah");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Cari profil nasabah berdasarkan NIK yang terdaftar.");
        subtitle.setFont(UITheme.SUBTITLE_FONT);
        subtitle.setForeground(UITheme.TEXT);

        JPanel textBox = new JPanel(new GridLayout(2, 1));
        textBox.setOpaque(false);
        textBox.add(title);
        textBox.add(subtitle);

        header.add(textBox, BorderLayout.WEST);

        return header;
    }

    private JPanel createMainPanel() {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setOpaque(false);

        JPanel inputCard = new JPanel(new BorderLayout(12, 12));
        inputCard.setBackground(UITheme.WHITE);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.PRIMARY_LIGHT, 2),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)));

        nikField = new JTextField();
        nikField.setFont(UITheme.BODY_FONT);
        nikField.setPreferredSize(new Dimension(260, 40));
        nikField.setBorder(BorderFactory.createCompoundBorder(
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
        cariButton.setPreferredSize(new Dimension(160, 42));

        JPanel formRow = new JPanel(new BorderLayout(12, 0));
        formRow.setOpaque(false);
        formRow.add(nikField, BorderLayout.CENTER);
        formRow.add(cariButton, BorderLayout.EAST);

        JLabel label = new JLabel("Masukkan NIK");
        label.setFont(UITheme.BODY_FONT);
        label.setForeground(UITheme.TEXT);

        JLabel hint = new JLabel("Gunakan 16 digit NIK untuk mencari data nasabah.");
        hint.setFont(UITheme.SUBTITLE_FONT);
        hint.setForeground(UITheme.TEXT);

        inputCard.add(label, BorderLayout.NORTH);
        inputCard.add(formRow, BorderLayout.CENTER);
        inputCard.add(hint, BorderLayout.SOUTH);

        JPanel resultCard = new JPanel(new BorderLayout());
        resultCard.setBackground(UITheme.WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.PRIMARY_LIGHT, 2),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)));

        resultLabel = new JLabel(
                "<html><div style='text-align:center; color:#5a4b7a;'>" +
                        "<h2>Belum ada pencarian</h2>" +
                        "<p>Masukkan NIK lalu klik tombol Cari Nasabah.</p>" +
                        "</div></html>",
                JLabel.CENTER);
        resultLabel.setFont(UITheme.BODY_FONT);

        resultCard.add(resultLabel, BorderLayout.CENTER);

        main.add(inputCard, BorderLayout.NORTH);
        main.add(resultCard, BorderLayout.CENTER);

        cariButton.addActionListener(e -> cariNasabah());

        return main;
    }

    private void cariNasabah() {
        String nik = nikField.getText().trim();

        if (nik.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "NIK tidak boleh kosong",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!nik.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "NIK harus terdiri dari 16 digit angka",
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
                return nasabahController.findNasabahByNik(nik);
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
                                    "<p>NIK belum terdaftar di sistem.</p>" +
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