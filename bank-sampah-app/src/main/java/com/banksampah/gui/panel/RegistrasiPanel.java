package com.banksampah.gui.panel;

import com.banksampah.controller.NasabahController;
import com.banksampah.dto.RegisterNasabahRequest;
import com.banksampah.exception.DuplicateNIKException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class RegistrasiPanel extends JPanel {

    private JTextField namaField;
    private JTextField tanggalLahirField;
    private JTextField alamatField;
    private JTextField noHpField;
    private JComboBox<String> jenisKelaminBox;
    private JFormattedTextField nikField;
    private JTextField uidRfidField;
    private JButton daftarButton;

    private NasabahController nasabahController;

    public RegistrasiPanel() {
        nasabahController = new NasabahController();

        setLayout(new GridLayout(8, 2, 10, 10));

        namaField = new JTextField();
        tanggalLahirField = new JTextField("2005-01-01");
        alamatField = new JTextField();
        noHpField = new JTextField();
        jenisKelaminBox = new JComboBox<>(new String[] { "L", "P" });
        nikField = new JFormattedTextField();
        uidRfidField = new JTextField();
        daftarButton = new JButton("Daftarkan Nasabah");

        add(new JLabel("Nama Lengkap"));
        add(namaField);

        add(new JLabel("Tanggal Lahir (yyyy-mm-dd)"));
        add(tanggalLahirField);

        add(new JLabel("Alamat"));
        add(alamatField);

        add(new JLabel("No HP"));
        add(noHpField);

        add(new JLabel("Jenis Kelamin"));
        add(jenisKelaminBox);

        add(new JLabel("NIK"));
        add(nikField);

        add(new JLabel("UID RFID"));
        add(uidRfidField);

        add(new JLabel(""));
        add(daftarButton);

        daftarButton.addActionListener(e -> registerNasabah());
    }

    private void registerNasabah() {
        try {
            RegisterNasabahRequest request = new RegisterNasabahRequest();

            request.setNamaLengkap(namaField.getText().trim());
            request.setTanggalLahir(tanggalLahirField.getText().trim());
            request.setAlamat(alamatField.getText().trim());
            request.setNoHp(noHpField.getText().trim());
            request.setJenisKelamin(jenisKelaminBox.getSelectedItem().toString());
            request.setNik(nikField.getText().trim());
            request.setUidRfid(uidRfidField.getText().trim());

            nasabahController.registerNasabah(request);

            JOptionPane.showMessageDialog(
                    this,
                    "Registrasi berhasil!\nStatus KYC: pending",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            clearForm();

        } catch (DuplicateNIKException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Registrasi Gagal",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Validasi Gagal",
                    JOptionPane.WARNING_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Terjadi error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        namaField.setText("");
        tanggalLahirField.setText("2005-01-01");
        alamatField.setText("");
        noHpField.setText("");
        nikField.setText("");
        uidRfidField.setText("");
        jenisKelaminBox.setSelectedIndex(0);
    }
}