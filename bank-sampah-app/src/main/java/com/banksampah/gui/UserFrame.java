package com.banksampah.gui;

import com.banksampah.gui.component.CardPanel;
import com.banksampah.gui.component.RoundedButton;
import com.banksampah.gui.component.UITheme;
import com.banksampah.gui.panel.RFIDPanel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class UserFrame extends JFrame {

    public UserFrame() {
        setTitle("Bank Sampah MBG - User");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(UITheme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainContent(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);

        add(root);
    }

    private JPanel createHeader() {
        CardPanel header = new CardPanel();
        header.setLayout(new BorderLayout());

        JLabel title = new JLabel("♻ Bank Sampah MBG");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Bersih Sampah, Bersih Hati, Bersih Bumi");
        subtitle.setFont(UITheme.SUBTITLE_FONT);
        subtitle.setForeground(UITheme.TEXT);

        JPanel titleBox = new JPanel(new GridLayout(2, 1));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subtitle);

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 36));
        userIcon.setHorizontalAlignment(JLabel.RIGHT);

        header.add(titleBox, BorderLayout.WEST);
        header.add(userIcon, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setOpaque(false);

        main.add(createLeftWelcomePanel(), BorderLayout.WEST);
        main.add(createRightRFIDPanel(), BorderLayout.CENTER);

        return main;
    }

    private JPanel createLeftWelcomePanel() {
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(330, 0));
        left.setBackground(UITheme.PRIMARY);
        left.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel hello = new JLabel(
                "<html>" +
                        "<div style='color:white; font-family:Segoe UI;'>" +
                        "<h1>👋 Halo,<br>Nasabah!</h1>" +
                        "<p style='font-size:13px;'>Tempelkan kartu RFID Anda untuk melihat informasi akun nasabah.</p>"
                        +
                        "</div>" +
                        "</html>");

        JLabel illustration = new JLabel(
                "<html>" +
                        "<div style='color:white; font-family:Segoe UI; text-align:center;'>" +
                        "<br><br>" +
                        "<h1 style='font-size:54px;'>💳</h1>" +
                        "<h2>RFID Access</h2>" +
                        "<p>Pindai kartu untuk membuka data nasabah.</p>" +
                        "</div>" +
                        "</html>");

        JPanel securityBox = new JPanel(new BorderLayout());
        securityBox.setBackground(new Color(185, 160, 245));
        securityBox.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel security = new JLabel(
                "<html>" +
                        "<b style='color:white;'>🛡 Aman & Terpercaya</b><br>" +
                        "<span style='color:white;'>Data nasabah dilindungi sistem Bank Sampah MBG.</span>" +
                        "</html>");

        securityBox.add(security, BorderLayout.CENTER);

        left.add(hello);
        left.add(illustration);
        left.add(javax.swing.Box.createVerticalGlue());
        left.add(securityBox);

        return left;
    }

    private JPanel createRightRFIDPanel() {
        JPanel right = new JPanel(new BorderLayout(0, 20));
        right.setOpaque(false);

        CardPanel scanCard = new CardPanel();
        scanCard.setLayout(new BorderLayout());

        JLabel scanTitle = new JLabel("📡 Pindai Kartu RFID");
        scanTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        scanTitle.setForeground(UITheme.TEXT);

        JLabel scanSubtitle = new JLabel("Tempelkan kartu RFID Anda pada reader atau masukkan UID secara manual.");
        scanSubtitle.setFont(UITheme.SUBTITLE_FONT);
        scanSubtitle.setForeground(UITheme.TEXT);

        JPanel scanHeader = new JPanel(new GridLayout(2, 1));
        scanHeader.setOpaque(false);
        scanHeader.add(scanTitle);
        scanHeader.add(scanSubtitle);

        scanCard.add(scanHeader, BorderLayout.NORTH);
        scanCard.add(new RFIDPanel(), BorderLayout.CENTER);

        right.add(scanCard, BorderLayout.NORTH);

        CardPanel infoCard = new CardPanel();
        infoCard.setLayout(new BorderLayout());

        JLabel infoTitle = new JLabel("📋 Informasi Nasabah");
        infoTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        infoTitle.setForeground(UITheme.TEXT);

        JLabel emptyText = new JLabel(
                "<html><div style='text-align:center; color:#5a4b7a;'>" +
                        "<h2>Belum ada data</h2>" +
                        "<p>Silakan pindai kartu RFID atau masukkan UID untuk melihat informasi nasabah.</p>" +
                        "</div></html>",
                JLabel.CENTER);

        infoCard.add(infoTitle, BorderLayout.NORTH);
        infoCard.add(emptyText, BorderLayout.CENTER);

        right.add(infoCard, BorderLayout.CENTER);

        return right;
    }

    private JPanel createFooter() {
        CardPanel footer = new CardPanel();
        footer.setLayout(new BorderLayout());

        JLabel left = new JLabel("🛡 Sistem Bank Sampah MBG");
        left.setFont(UITheme.BODY_FONT);
        left.setForeground(UITheme.TEXT);

        RoundedButton backButton = new RoundedButton("Kembali ke Menu Utama", UITheme.PRIMARY);
        backButton.setPreferredSize(new Dimension(220, 42));

        backButton.addActionListener(e -> {
            dispose();
            new RoleSelectionFrame().setVisible(true);
        });

        footer.add(left, BorderLayout.WEST);
        footer.add(backButton, BorderLayout.EAST);

        return footer;
    }
}