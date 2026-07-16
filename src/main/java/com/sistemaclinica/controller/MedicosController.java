package com.sistemaclinica.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.dto.AgendaPorMedicoDTO;
import com.sistemaclinica.dto.MedicoSelectsDTO;
import com.sistemaclinica.dto.MedicosProfileDTO;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.service.AgendamentoService;
import com.sistemaclinica.service.MedicosService;

@RestController
@RequestMapping("api/MdzaDbr3YvCJv44fdf4")
public class MedicosController {
	
	private static final Logger LOGGER = LogManager.getLogger(MedicosController.class);
	
	// chave mocada para testes
	@Value("${app.clinica.unica.id}")
    private String accessKeyDevTests;
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private MedicosService medicoService;
	
	@GetMapping("agendamedico/{idMedico}/{date}")
	public List<AgendaPorMedicoDTO> agendaMedico(@PathVariable("idMedico") Long idMedico, @PathVariable("date") LocalDate date) {		
		return agendamentoService.getHorariosPorMedico(idMedico, date);
	}
	
	// /api/MdzaDbr3YvCJv44fdf4/medicos
	@GetMapping("medicos")
	public ResponseEntity<?> getMedicosbyClinica(@RequestHeader("X-Clinica-Key") String clinicaKey) {
		
		try {
            List<MedicoSelectsDTO> medicos = medicoService.getMedicosByClinica(clinicaKey);
            
            if (medicos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            
            return ResponseEntity.ok(medicos);
            
        } catch (RuntimeException e) {
            // Clínica não encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar médicos: " + e.getMessage());
        }
	}

	// /api/MdzaDbr3YvCJv44fdf4/medicosprofile/2026-06-14
	@GetMapping("medicosprofile/{date}")
	public ResponseEntity<?> getMedicosProfilebyClinica(@RequestHeader("X-Clinica-Key") String clinicaKey, @PathVariable("date") LocalDate date) {
		
		try {
            List<MedicosProfileDTO> medicos = medicoService.getMedicosProfileByClinica(clinicaKey, date);
            
            if (medicos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            
            return ResponseEntity.ok(medicos);
            
        } catch (RuntimeException e) {
            // Clínica não encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar médicos: " + e.getMessage());
        }
	}

}
