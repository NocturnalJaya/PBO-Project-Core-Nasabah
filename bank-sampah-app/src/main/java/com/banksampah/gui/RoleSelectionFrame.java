package com.banksampah.gui;

import com.banksampah.gui.component.RoundedButton;
import com.banksampah.gui.component.UITheme;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class RoleSelectionFrame extends JFrame {

    public RoleSelectionFrame() {
        setTitle("Bank Sampah MBG");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(UITheme.BACKGROUND);
        root.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel title = new JLabel("Bank Sampah MBG", SwingConstants.CENTER);
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(UITheme.PRIMARY);

        JLabel subtitle = new JLabel("Silakan pilih akses aplikasi", SwingConstants.CENTER);
        subtitle.setFont(UITheme.SUBTITLE_FONT);
        subtitle.setForeground(UITheme.TEXT);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(title);
        header.add(subtitle);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        buttonPanel.setOpaque(false);

        RoundedButton adminButton = new RoundedButton("Masuk sebagai Admin", UITheme.PRIMARY);
        RoundedButton userButton = new RoundedButton("Masuk sebagai User / Nasabah", new java.awt.Color(186, 152, 255));

        buttonPanel.add(adminButton);
        buttonPanel.add(userButton);

        adminButton.addActionListener(e -> {
            dispose();
            new MainFrame().setVisible(true);
        });

        userButton.addActionListener(e -> {
            dispose();
            new UserFrame().setVisible(true);
        });

        root.add(header, BorderLayout.NORTH);
        root.add(buttonPanel, BorderLayout.CENTER);

        add(root);
    }

    public static void showFrame() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new RoleSelectionFrame().setVisible(true);
        });
    }
}