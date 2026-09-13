package com.sistemaclinica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.entity.Tipoconsulta;
import com.sistemaclinica.repo.TipoConsultaRepo;

@Service
@Transactional
public class TipoConsultasService {

    @Autowired
    private TipoConsultaRepo tipoConsultaRepo;

    /**
     * Retrieves all types of consultations from the database.
     * 
     * @return list of Tipoconsulta
     */
    public List<Tipoconsulta> findAll() {
        return tipoConsultaRepo.findAll();
    }
}
