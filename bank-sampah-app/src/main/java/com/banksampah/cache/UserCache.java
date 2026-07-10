package com.banksampah.cache;

import com.banksampah.dto.UserResponse;

import java.util.HashMap;

public class UserCache {

    private static HashMap<String, UserResponse> userByNik = new HashMap<>();

    public static void put(String nik, UserResponse userResponse) {
        userByNik.put(nik, userResponse);
    }

    public static UserResponse get(String nik) {
        return userByNik.get(nik);
    }

    public static boolean contains(String nik) {
        return userByNik.containsKey(nik);
    }
}