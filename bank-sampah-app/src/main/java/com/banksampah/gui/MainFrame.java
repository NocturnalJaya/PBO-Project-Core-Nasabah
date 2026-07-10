package com.banksampah.gui;

import com.banksampah.gui.panel.KYCPanel;
import com.banksampah.gui.panel.RegistrasiPanel;
import com.banksampah.gui.panel.RFIDPanel;
import com.banksampah.gui.panel.SearchPanel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

        private JTabbedPane tabbedPane;

        public MainFrame() {

                setTitle("Bank Sampah MBG - Admin");
                setSize(1400, 900);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                tabbedPane = new JTabbedPane();

                // ---------------- REGISTRASI ----------------//

                JScrollPane registrasiScroll = new JScrollPane(new RegistrasiPanel());

                registrasiScroll.setBorder(null);
                registrasiScroll.setHorizontalScrollBarPolicy(
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                registrasiScroll.getVerticalScrollBar().setUnitIncrement(16);

                // ---------------- RFID ----------------//

                JScrollPane rfidScroll = new JScrollPane(new RFIDPanel());

                rfidScroll.setBorder(null);
                rfidScroll.setHorizontalScrollBarPolicy(
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                rfidScroll.getVerticalScrollBar().setUnitIncrement(16);

                // ---------------- KYC ----------------//

                JScrollPane kycScroll = new JScrollPane(new KYCPanel());

                kycScroll.setBorder(null);
                kycScroll.setHorizontalScrollBarPolicy(
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                kycScroll.getVerticalScrollBar().setUnitIncrement(16);

                // ---------------- SEARCH ----------------//

                JScrollPane searchScroll = new JScrollPane(new SearchPanel());

                searchScroll.setBorder(null);
                searchScroll.setHorizontalScrollBarPolicy(
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                searchScroll.getVerticalScrollBar().setUnitIncrement(16);

                // ---------------- TAB ----------------//

                tabbedPane.addTab("Registrasi", registrasiScroll);
                tabbedPane.addTab("Scan RFID", rfidScroll);
                tabbedPane.addTab("Verifikasi KYC", kycScroll);
                tabbedPane.addTab("Pencarian", searchScroll);

                add(tabbedPane, BorderLayout.CENTER);
        }

        public static void showFrame() {
                SwingUtilities.invokeLater(() -> {
                        new MainFrame().setVisible(true);
                });
        }
}