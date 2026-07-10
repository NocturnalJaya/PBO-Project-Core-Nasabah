package com.banksampah.gui.panel;

import com.banksampah.controller.NasabahController;
import com.banksampah.gui.component.RoundedButton;
import com.banksampah.gui.component.UITheme;
import com.banksampah.model.StatusKYC;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class KYCPanel extends JPanel {

    private JTextField nasabahIdField;
    private JTextField adminField;
    private RoundedButton cekButton;
    private RoundedButton verifyButton;
    private RoundedButton rejectButton;
    private JTextArea resultArea;

    private NasabahController nasabahController;

    public KYCPanel() {
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

        JLabel title = new JLabel("Verifikasi KYC");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Kelola status verifikasi identitas nasabah.");
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

        JPanel inputCard = new JPanel(new GridLayout(4, 2, 10, 12));
        inputCard.setBackground(UITheme.WHITE);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.PRIMARY_LIGHT, 2),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)));

        nasabahIdField = createTextField();
        adminField = createTextField();
        adminField.setText("Admin MBG");

        cekButton = new RoundedButton("Cek Status", UITheme.PRIMARY);
        verifyButton = new RoundedButton("Verifikasi", UITheme.PRIMARY);
        rejectButton = new RoundedButton("Tolak KYC", new java.awt.Color(210, 90, 120));

        cekButton.setPreferredSize(new Dimension(160, 40));
        verifyButton.setPreferredSize(new Dimension(160, 40));
        rejectButton.setPreferredSize(new Dimension(160, 40));

        inputCard.add(createLabel("ID Nasabah"));
        inputCard.add(nasabahIdField);

        inputCard.add(createLabel("Diverifikasi Oleh"));
        inputCard.add(adminField);

        inputCard.add(cekButton);
        inputCard.add(verifyButton);

        inputCard.add(new JLabel(""));
        inputCard.add(rejectButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(UITheme.BODY_FONT);
        resultArea.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        resultArea.setText("Belum ada data KYC yang dipilih.");

        JPanel resultCard = new JPanel(new BorderLayout());
        resultCard.setBackground(UITheme.WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.PRIMARY_LIGHT, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        resultCard.add(resultArea, BorderLayout.CENTER);

        main.add(inputCard, BorderLayout.NORTH);
        main.add(resultCard, BorderLayout.CENTER);

        cekButton.addActionListener(e -> cekStatus());
        verifyButton.addActionListener(e -> updateStatus("verified"));
        rejectButton.addActionListener(e -> updateStatus("rejected"));

        return main;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(UITheme.BODY_FONT);
        field.setPreferredSize(new Dimension(260, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        UITheme.PRIMARY_LIGHT,
                        2,
                        true),
                BorderFactory.createEmptyBorder(
                        8,
                        12,
                        8,
                        12)));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.BODY_FONT);
        label.setForeground(UITheme.TEXT);
        return label;
    }

    private void cekStatus() {
        runKycTask("cek");
    }

    private void updateStatus(String status) {
        runKycTask(status);
    }

    private void runKycTask(String action) {
        setButtonsEnabled(false);
        resultArea.setText("Memproses data KYC...");

        SwingWorker<StatusKYC, Void> worker = new SwingWorker<>() {

            @Override
            protected StatusKYC doInBackground() throws Exception {
                int nasabahId = Integer.parseInt(nasabahIdField.getText().trim());
                String verifiedBy = adminField.getText().trim();

                if (verifiedBy.isEmpty()) {
                    throw new IllegalArgumentException("Nama admin tidak boleh kosong");
                }

                if (action.equals("verified")) {
                    nasabahController.verifyKyc(nasabahId, verifiedBy);
                } else if (action.equals("rejected")) {
                    nasabahController.rejectKyc(nasabahId, verifiedBy);
                }

                return nasabahController.findKycStatus(nasabahId);
            }

            @Override
            protected void done() {
                try {
                    StatusKYC kyc = get();

                    if (kyc == null) {
                        resultArea.setText("Status KYC belum tersedia.");
                    } else {
                        tampilkanStatus(kyc);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            KYCPanel.this,
                            "Terjadi error: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                setButtonsEnabled(true);
            }
        };

        worker.execute();
    }

    private void setButtonsEnabled(boolean enabled) {
        cekButton.setEnabled(enabled);
        verifyButton.setEnabled(enabled);
        rejectButton.setEnabled(enabled);
    }

    private void tampilkanStatus(StatusKYC kyc) {
        resultArea.setText(
                "=== STATUS KYC ===\n\n" +
                        "ID Status     : " + kyc.getId() + "\n" +
                        "ID Nasabah    : " + kyc.getNasabahId() + "\n" +
                        "Status        : " + kyc.getStatusKyc().toUpperCase() + "\n" +
                        "Verified At   : " + kyc.getVerifiedAt() + "\n" +
                        "Verified By   : " + kyc.getVerifiedBy());
    }
}