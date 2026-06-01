package com.banksampah.repository;

import com.banksampah.model.User;

public interface IUserRepository {
    void save(User user);

    User findByNik(String nik);

    User findByUid(String uid);
}