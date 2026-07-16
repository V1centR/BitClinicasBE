package com.sistemaclinica.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.request.AusenciaMedicoRequest;
import com.sistemaclinica.service.AusenciasMedicoService;

@RestController
@RequestMapping("/api/7DKjYeWXBLGkvN844XZe")
public class AgendaMedicoController {

    @Autowired
    private AusenciasMedicoService ausenciasMedicoService;


    @PostMapping("/setausencia")
    public ResponseEntity<?> setAusenciaMedico(@RequestHeader("X-Clinica-Key") String clinicaKey, @RequestBody AusenciaMedicoRequest ausenciaMedicoRequest) {

        ausenciasMedicoService.setAusenciaMedico(ausenciaMedicoRequest, clinicaKey);
       


        return null;
    }

    @GetMapping("/ausenciasmedico")
	public ResponseEntity<?> getAusenciasMedico(@RequestHeader("X-Clinica-Key") String clinicaKey, @RequestBody AusenciaMedicoRequest ausenciaMedicoRequest) {


        return null;
    }
    
}
