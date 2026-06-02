package com.banksampah.app;

import com.banksampah.config.DBConfig;
import com.banksampah.model.User;
import com.banksampah.repository.UserRepository;
import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.service.UserService;

public class Main {
    public static void main(String[] args) {

        UserService service = new UserService();

        System.out.println("Pencarian pertama:");
        User user1 = service.findByNik("6713617236238");

        if (user1 != null) {
            System.out.println("User ditemukan: " + user1.getNik());
        }

        System.out.println("\nPencarian kedua:");
        User user2 = service.findByNik("6713617236238");

        if (user2 != null) {
            System.out.println("User ditemukan: " + user2.getNik());
        }
    }
}
