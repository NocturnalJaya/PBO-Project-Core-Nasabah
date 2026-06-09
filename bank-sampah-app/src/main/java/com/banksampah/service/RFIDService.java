package com.banksampah.service;

import com.banksampah.exception.UserNotFoundException;
import com.banksampah.model.User;
import com.banksampah.rfid.RFIDReader;

public class RFIDService {

    private RFIDReader rfidReader;
    private UserService userService;

    public RFIDService() {
        this.rfidReader = new RFIDReader();
        this.userService = new UserService();
    }

    public User scanAndFindUser() throws UserNotFoundException {
        String uid = rfidReader.scanUID();

        System.out.println("UID terbaca: " + uid);

        return userService.findByUid(uid);
    }
}