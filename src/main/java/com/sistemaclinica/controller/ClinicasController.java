package com.sistemaclinica.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.dto.ClinicaDTO;
import com.sistemaclinica.request.ClinicaUpdateRequest;
import com.sistemaclinica.request.NovoUsuarioRequest;
import com.sistemaclinica.response.ErrorResponse;
import com.sistemaclinica.service.ClinicasService;

@RestController
@RequestMapping("/api/NxBYkLUPRVmTyZamR4wf")

public class ClinicasController {
	
		@Autowired
		ClinicasService clinicaService;
		
		
		//PUT /api/NxBYkLUPRVmTyZamR4wf
		@PatchMapping("/updateclinicadata")
	    public ResponseEntity<?> updateClinica(@RequestHeader("X-Clinica-Key") String clinicaKey, @RequestBody Map<String, Object> updateData) {
			
			try {
		        clinicaService.updateClinica(clinicaKey, updateData);
		        
		        Map<String, Object> response = new HashMap<>();
		        response.put("success", true);
		        response.put("message", "Clínica atualizada com sucesso");
		        
		        return ResponseEntity.ok(response);
		        
		    } catch (RuntimeException e) {
		        Map<String, Object> error = new HashMap<>();
		        error.put("success", false);
		        error.put("message", "Um erro ocorreu na consistencia dos dados");
		        
		        return ResponseEntity.badRequest().body(error);
		    }
			
	    }
	
		// /api/NxBYkLUPRVmTyZamR4wf
		@GetMapping("/clinicadata")
		public ResponseEntity<?> getClinicaByAccessKey(@RequestHeader("X-Clinica-Key") String clinicaKey) {
			
			try {
				
	            Optional<ClinicaDTO> clinica = clinicaService.getClinicaByAccessKey(clinicaKey);
	            
	            if (clinica.isEmpty()) {
	            	return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ErrorResponse(
	                            HttpStatus.NOT_FOUND.value(),
	                            "Clinica desativada ou não existe"
	                        ));
	            }
	            
	            return ResponseEntity.ok(clinica);
	            
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
