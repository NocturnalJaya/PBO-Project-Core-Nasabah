package com.banksampah.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_sampah_db";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    public static Connection connect() {

        try {
            Connection conn = DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
            );
            System.out.println("Database berhasil terhubung bang:)");

            return conn;
        
        } catch (Exception e) {
            System.out.println("gagal tehubung ngabss:(");
            e.printStackTrace();

            return null
        }

    }
}
