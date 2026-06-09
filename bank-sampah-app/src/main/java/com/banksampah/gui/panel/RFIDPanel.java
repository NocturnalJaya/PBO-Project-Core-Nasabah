package com.banksampah.gui.panel;

import com.banksampah.model.Biodata;
import com.banksampah.model.StatusKYC;
import com.banksampah.model.User;
import com.banksampah.service.BiodataService;
import com.banksampah.service.KYCService;
import com.banksampah.service.UserService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class RFIDPanel extends JPanel {

    private JTextField uidField;
    private JButton cariButton;
    private JTextArea resultArea;

    private UserService userService;
    private BiodataService biodataService;
    private KYCService kycService;

    public RFIDPanel() {
        userService = new UserService();
        biodataService = new BiodataService();
        kycService = new KYCService();

        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        uidField = new JTextField();
        cariButton = new JButton("Cari Nasabah dari UID");

        inputPanel.add(new JLabel("UID RFID"));
        inputPanel.add(uidField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(cariButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(resultArea, BorderLayout.CENTER);

        cariButton.addActionListener(e -> cariNasabahByUid());
    }

    private void cariNasabahByUid() {

        cariButton.setEnabled(false);

        resultArea.setText(
                "Mencari data nasabah...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {

            @Override
            protected String doInBackground()
                    throws Exception {

                String uid = uidField.getText().trim();

                User user = userService.findByUid(uid);

                Biodata biodata = biodataService.findById(
                        user.getBiodataId());

                StatusKYC kyc = kycService.findByNasabahId(
                        user.getId());

                StringBuilder result = new StringBuilder();

                result.append(
                        "=== DATA NASABAH ===\n\n");

                result.append(
                        "Nama: ").append(
                                biodata.getNamaLengkap())
                        .append(
                                "\n");

                result.append(
                        "Tanggal Lahir: ").append(
                                biodata.getTanggalLahir())
                        .append(
                                "\n");

                result.append(
                        "Alamat: ").append(
                                biodata.getAlamat())
                        .append(
                                "\n");

                result.append(
                        "No HP: ").append(
                                biodata.getNoHp())
                        .append(
                                "\n");

                result.append(
                        "Jenis Kelamin: ").append(
                                biodata.getJenisKelamin())
                        .append(
                                "\n\n");

                result.append(
                        "UID RFID: ").append(
                                user.getUidRfid())
                        .append(
                                "\n");

                result.append(
                        "NIK: ").append(
                                user.getNik())
                        .append(
                                "\n");

                result.append(
                        "Status Aktif: ").append(
                                user.isActive())
                        .append(
                                "\n\n");

                if (kyc != null) {

                    result.append(
                            "Status KYC: ").append(
                                    kyc.getStatusKyc())
                            .append(
                                    "\n");

                    result.append(
                            "Verified At: ").append(
                                    kyc.getVerifiedAt())
                            .append(
                                    "\n");

                    result.append(
                            "Verified By: ").append(
                                    kyc.getVerifiedBy())
                            .append(
                                    "\n");
                }

                return result.toString();
            }

            @Override
            protected void done() {

                try {

                    resultArea.setText(
                            get());

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            RFIDPanel.this,
                            e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);

                }

                cariButton.setEnabled(true);
            }
        };

        worker.execute();
    }
}