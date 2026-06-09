package com.banksampah.gui;

import com.banksampah.gui.panel.RegistrasiPanel;
import com.banksampah.gui.panel.RFIDPanel;
import com.banksampah.gui.panel.KYCPanel;
import com.banksampah.gui.panel.SearchPanel;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Bank Sampah - Core Nasabah");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Registrasi", new RegistrasiPanel());
        tabbedPane.addTab("Scan RFID", new RFIDPanel());
        tabbedPane.addTab("Verifikasi KYC", new KYCPanel());
        tabbedPane.addTab("Pencarian", new SearchPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}