package com.banksampah.service;

import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.exception.UserNotFoundException;
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

    public int registerUser(User user) throws DuplicateNIKException {

        User existingUser = userRepository.findByNik(user.getNik());

        if (existingUser != null) {
            throw new DuplicateNIKException("NIK sudah terdaftar");
        }

        int nasabahId = userRepository.save(user);

        user.setId(nasabahId);

        userMap.put(user.getNik(), user);

        return nasabahId;
    }

    public User findByNik(String nik)
            throws UserNotFoundException {

        if (userMap.containsKey(nik)) {
            System.out.println("Data diambil dari CACHE");
            return userMap.get(nik);
        }

        User user = userRepository.findByNik(nik);

        if (user == null) {
            throw new UserNotFoundException(
                    "Nasabah dengan NIK " + nik + " tidak ditemukan");
        }

        userMap.put(nik, user);

        System.out.println("Data diambil dari DATABASE");

        return user;
    }

    public User findByUid(String uid)
            throws UserNotFoundException {

        User user = userRepository.findByUid(uid);

        if (user == null) {
            throw new UserNotFoundException(
                    "Nasabah dengan UID " + uid +
                            " tidak ditemukan");
        }

        return user;
    }

    public boolean isNikExists(String nik) {
        return userRepository.findByNik(nik) != null;
    }

}