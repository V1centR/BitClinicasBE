package com.sistemaclinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.entity.Convenio;
import com.sistemaclinica.service.ConveniosService;

@RestController
@RequestMapping("/api/K7XycDvqG23AeBH9ZZ")
public class ConveniosController {

    @Autowired
    private ConveniosService convenioService;

    @GetMapping("/convenios")
    public ResponseEntity<List<Convenio>> getConveniosDaClinica() {
        List<Convenio> convenios = convenioService.findAll();
        return ResponseEntity.ok(convenios);
    }
}
