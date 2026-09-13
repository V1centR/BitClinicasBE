package com.sistemaclinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.entity.Tipoconsulta;
import com.sistemaclinica.service.TipoConsultasService;

@RestController
@RequestMapping("/api/Wq3yC5G8xJ2bV4fT6rP0s")
public class TipoConsultasController {

    @Autowired
    private TipoConsultasService tipoConsultasService;

    @GetMapping("/tipoConsultas")
    public ResponseEntity<List<Tipoconsulta>> getTipoConsultas() {
        List<Tipoconsulta> tiposConsultas = tipoConsultasService.findAll();
        return ResponseEntity.ok(tiposConsultas);
    }
}
