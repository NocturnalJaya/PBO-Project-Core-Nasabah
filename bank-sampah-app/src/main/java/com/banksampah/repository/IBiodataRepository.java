package com.banksampah.repository;

import com.banksampah.model.Biodata;

public interface IBiodataRepository {

    int save(Biodata biodata);

    Biodata findById(int id);
}