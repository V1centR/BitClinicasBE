package com.sistemaclinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.dto.PacienteSearchDto;
import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.request.PacienteCreateUpdateRequest;
import com.sistemaclinica.response.ApiResponse;
import com.sistemaclinica.service.PacientesService;

@RestController
@RequestMapping("/api/CGzaDbr3YvCJv5RcZF")
public class PacientesController {
	
	@Autowired
	private PacientesService pacienteService;
	
	@PostMapping
	public ResponseEntity<?> testeAgendamento(@RequestBody PacienteCreateUpdateRequest  request) {
		
		try {
			
            System.out.println("Recebendo dados do paciente: " + request);
            Paciente pacienteSalvo = pacienteService.salvarPacientes(request);
            
            return ResponseEntity.ok(pacienteSalvo);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao salvar novo paciente: " + e.getMessage());
        }
		
	}
	
	//GET /api/agendamentos/checarpaciente/{cpf}
    @GetMapping("/checarpaciente/{cpf}")
    public ResponseEntity<ApiResponse<PacienteSearchDto>> getPaciente(@PathVariable("cpf") String cpf) {
    	
        Paciente paciente = pacienteService.searchPaciente(cpf);
        
        if (paciente == null) {
            return ResponseEntity.ok().body(ApiResponse.error("Paciente não encontrado com CPF: " + cpf + ", fazer registro rápido."));
        }
        
        PacienteSearchDto pacienteResponse = new PacienteSearchDto(paciente);
        return ResponseEntity.ok(ApiResponse.success(pacienteResponse));
    }

}
