package com.banksampah.app;

import com.banksampah.config.DBConfig;
import com.banksampah.model.User;
import com.banksampah.repository.UserRepository;;

public class Main {
    public static void main(String[] args) {

        // test koneksi
        DBConfig.connect();

        // test insert user
        User user = new User();

        user.setUidRfid("TEST_UID_001");
        user.setNik("6713617236238");
        user.setBiodataId(1);
        user.setActive(true);

        UserRepository repo = new UserRepository();

        repo.save(user);

    }
}
