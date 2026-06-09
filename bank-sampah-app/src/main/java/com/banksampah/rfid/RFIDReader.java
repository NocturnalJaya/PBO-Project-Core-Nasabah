package com.banksampah.rfid;

import java.util.Scanner;

public class RFIDReader {

    private Scanner scanner;

    public RFIDReader() {
        this.scanner = new Scanner(System.in);
    }

    public String scanUID() {
        System.out.print("Tempelkan KTP ke RFID Reader: ");

        String uid = scanner.nextLine();

        return uid.trim();
    }
}