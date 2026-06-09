package com.banksampah.mapper;

import com.banksampah.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUidRfid(rs.getString("uid_rfid"));
        user.setNik(rs.getString("nik"));
        user.setBiodataId(rs.getInt("biodata_id"));
        user.setActive(rs.getBoolean("status_aktif"));

        return user;
    }
}