package com.sistemaclinica.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.dto.FluxoDeTrabalhoDTO;
import com.sistemaclinica.response.ErrorResponse;
import com.sistemaclinica.service.FluxoTrabalhoService;

@RestController
@RequestMapping("/api/NSCpxwpStBsS")
public class FluxoTrabalhoController {
	
	@Autowired
	FluxoTrabalhoService fluxoService;
	
	@GetMapping("/data")
    public ResponseEntity<?> getFluxoTrabalho(
        @RequestHeader("X-Clinica-Key") String clinicaKey,
        @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
        ) {
        
        try {
            List<FluxoDeTrabalhoDTO> dataFluxo = fluxoService.getFluxoDeTrabalho(clinicaKey, data);
            
            if (dataFluxo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "No data"
                    ));
            }
            
            return ResponseEntity.ok(dataFluxo);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Log para debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao buscar fluxo: " + e.getMessage());
        }
    }

}
