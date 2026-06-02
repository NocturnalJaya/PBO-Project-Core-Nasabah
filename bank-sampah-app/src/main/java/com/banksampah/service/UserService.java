package com.banksampah.service;

import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.model.User;
import com.banksampah.repository.UserRepository;

import java.util.HashMap;

public class UserService {

    private UserRepository userRepository;

    private HashMap<String, User> userMap;

    public UserService() {
        this.userRepository = new UserRepository();
        this.userMap = new HashMap<>();
    }

    public void registerUser(User user) throws DuplicateNIKException {

        User existingUser = userRepository.findByNik(user.getNik());

        if (existingUser != null) {
            throw new DuplicateNIKException("NIK sudah terdaftar");
        }

        userRepository.save(user);

        userMap.put(user.getNik(), user);
    }

    public User findByNik(String nik) {
        if (userMap.containsKey(nik)) {
            System.out.println("Data ditemukan dari HashMap");
            return userMap.get(nik);
        }

        User user = userRepository.findByNik(nik);

        if (user != null) {
            userMap.put(nik, user);
        }
        return user;
    }

    public User findByUid(String uid) {
        return userRepository.findByUid(uid);
    }

}