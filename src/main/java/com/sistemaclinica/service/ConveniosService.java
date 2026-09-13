package com.sistemaclinica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.entity.Convenio;
import com.sistemaclinica.repo.ConvenioRepo;

@Service
@Transactional
public class ConveniosService {

    @Autowired
    private ConvenioRepo convenioRepo;

    /**
     * Finds all convenios. Since agreements are global to the clinic database system
     * and do not contain clinic-specific mapping in the database schema, this returns
     * all available agreements.
     * 
     * @return list of Convenios
     */
    public List<Convenio> findAll() {
        return convenioRepo.findAll();
    }
}
