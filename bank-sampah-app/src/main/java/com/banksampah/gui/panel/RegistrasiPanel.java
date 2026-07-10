package com.banksampah.gui.panel;

import com.banksampah.controller.NasabahController;
import com.banksampah.dto.RegisterNasabahRequest;
import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.gui.component.RoundedButton;
import com.banksampah.gui.component.RoundedPanel;
import com.banksampah.gui.component.UITheme;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class RegistrasiPanel extends JPanel {

    private JTextField namaField;
    private JTextField tanggalLahirField;
    private JTextField alamatField;
    private JTextField noHpField;
    private JComboBox<String> jenisKelaminBox;
    private JFormattedTextField nikField;
    private JTextField uidRfidField;
    private RoundedButton daftarButton;

    private NasabahController nasabahController;

    public RegistrasiPanel() {
        nasabahController = new NasabahController();

        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));

        add(createHeader(), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(createFormPanel());

        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel title = new JLabel("Registrasi Nasabah");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Daftarkan biodata, NIK, dan UID RFID nasabah baru.");
        subtitle.setFont(UITheme.SUBTITLE_FONT);
        subtitle.setForeground(UITheme.TEXT);

        JPanel textBox = new JPanel(new BorderLayout());
        textBox.setOpaque(false);
        textBox.add(title, BorderLayout.NORTH);
        textBox.add(subtitle, BorderLayout.SOUTH);

        header.add(textBox, BorderLayout.WEST);

        return header;
    }

    private JPanel createFormPanel() {
        RoundedPanel card = new RoundedPanel(
                UITheme.WHITE,
                30);

        card.setPreferredSize(new Dimension(860, 560));
        card.setMaximumSize(new Dimension(860, 560));
        card.setMinimumSize(new Dimension(860, 560));

        card.setLayout(new GridBagLayout());

        card.setLayout(
                new GridBagLayout());
        card.setBackground(UITheme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.PRIMARY_LIGHT, 2),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)));

        namaField = createTextField();
        tanggalLahirField = createTextField();
        tanggalLahirField.setText("2005-01-01");

        alamatField = createTextField();
        noHpField = createTextField();

        jenisKelaminBox = new JComboBox<>(new String[] { "L", "P" });
        jenisKelaminBox.setFont(UITheme.BODY_FONT);
        jenisKelaminBox.setPreferredSize(new Dimension(260, 38));

        nikField = new JFormattedTextField();
        styleTextField(nikField);

        uidRfidField = createTextField();

        daftarButton = new RoundedButton("Daftarkan Nasabah", UITheme.PRIMARY);
        daftarButton.setPreferredSize(new Dimension(220, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(card, gbc, 0, "Nama Lengkap", namaField);
        addFormRow(card, gbc, 1, "Tanggal Lahir", tanggalLahirField);
        addFormRow(card, gbc, 2, "Alamat", alamatField);
        addFormRow(card, gbc, 3, "No HP", noHpField);
        addFormRow(card, gbc, 4, "Jenis Kelamin", jenisKelaminBox);
        addFormRow(card, gbc, 5, "NIK", nikField);
        addFormRow(card, gbc, 6, "UID RFID", uidRfidField);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        card.add(daftarButton, gbc);

        daftarButton.addActionListener(e -> registerNasabah());

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;

        wrapper.add(card, c);

        return wrapper;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        styleTextField(field);
        return field;
    }

    private void styleTextField(JTextField field) {
        field.setFont(UITheme.BODY_FONT);
        field.setPreferredSize(new Dimension(260, 38));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        UITheme.PRIMARY_LIGHT,
                        2,
                        true // <-- sudut membulat
                ),
                BorderFactory.createEmptyBorder(
                        8,
                        12,
                        8,
                        12)));
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            java.awt.Component field) {
        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.BODY_FONT);
        label.setForeground(UITheme.TEXT);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.8;
        panel.add(field, gbc);
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