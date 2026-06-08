package com.banksampah.service;

import com.banksampah.model.Biodata;
import com.banksampah.repository.BiodataRepository;

public class BiodataService {

    private BiodataRepository biodataRepository;

    public BiodataService() {
        this.biodataRepository = new BiodataRepository();
    }

    public int saveBiodata(Biodata biodata) {
        return biodataRepository.save(biodata);
    }

    public Biodata findById(int id) {
        return biodataRepository.findById(id);
    }
}