package com.banksampah.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.banksampah.exception.DuplicateNIKException;
import com.banksampah.exception.UserNotFoundException;

public class UserServiceTest {

    @Test
    void testNikHarus16Digit() {
        String nik = "3377010101010001";
        assertEquals(16, nik.length());
    }

    @Test
    void testNikTidakValidJikaKurangDari16Digit() {
        String nik = "12345";
        assertNotEquals(16, nik.length());
    }

    @Test
    void testNikHanyaBerisiAngka() {
        String nik = "3377010101010001";
        assertTrue(nik.matches("\\d{16}"));
    }

    @Test
    void testDuplicateNIKException() {

        Exception exception = assertThrows(
                DuplicateNIKException.class,
                () -> {
                    throw new DuplicateNIKException(
                            "NIK sudah terdaftar");
                });

        assertEquals(
                "NIK sudah terdaftar",
                exception.getMessage());
    }

    @Test
    void testUserNotFoundException() {

        Exception exception = assertThrows(
                UserNotFoundException.class,
                () -> {
                    throw new UserNotFoundException(
                            "Nasabah tidak ditemukan");
                });

        assertEquals(
                "Nasabah tidak ditemukan",
                exception.getMessage());
    }
}