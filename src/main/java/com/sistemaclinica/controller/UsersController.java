package com.sistemaclinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.dto.MedicoResponseDTO;
import com.sistemaclinica.dto.UsersatendenteResponseDTO;
import com.sistemaclinica.dto.UsuarioBaseDTO;
import com.sistemaclinica.request.NovoUsuarioRequest;
import com.sistemaclinica.response.ErrorResponse;
import com.sistemaclinica.service.MedicosService;
import com.sistemaclinica.service.UsersService;

@RestController
@RequestMapping("/api/MxxFYRGkkh84e98mR6fp9pGY")
public class UsersController {
	
	/*
	 * Classe responsavel pelo gerenciamento de usuários corporativos.
	 * devappvicente@gmail.com 20260120 16:58
	 */
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private MedicosService medicoService;
	
	
	@DeleteMapping("/usuariosclinica/{mailUser}")
	public ResponseEntity<?> deleteUser(@RequestHeader("X-Clinica-Key") String clinicaKey, @PathVariable("mailUser") String mailUser) {
		
		try {
			userService.deleteUser(clinicaKey, mailUser);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			// TODO: handle exception
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	//PUT /api/agendamentos/hoje
	@PutMapping("/updateuserdata")
    public ResponseEntity<?> updateUser(@RequestHeader("X-Clinica-Key") String clinicaKey, @RequestBody NovoUsuarioRequest editRequest) {
		
		Object dataSaved;
		
		try {
			
			if(editRequest.isIsmedico()) {				
				System.out.println("UPDATE user MEDICO ::::::::::::: " + editRequest);
				dataSaved = medicoService.saveMedico(editRequest, clinicaKey);
			} else {
				System.out.println("UPDATE user USUARIO ::::::::::::: " + editRequest);
				dataSaved = userService.saveUser(editRequest,clinicaKey);
			}
            
			return ResponseEntity.status(HttpStatus.CREATED).body(dataSaved);
            
        } catch (Exception e) {
        	
        	ErrorResponse erro = new ErrorResponse(HttpStatus.CONFLICT.value(),e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
        	
        }
		
    }
	
	
	@PostMapping("/registerusuario")
	public ResponseEntity<?> registerUser(@RequestHeader("X-Clinica-Key") String clinicaKey, @RequestBody NovoUsuarioRequest  request) {
		
		Object dataSaved;
		
		try {
			
			if(request.isIsmedico()) {				
				System.out.println("REgistrando medico ::::::::::::: ");
				dataSaved = medicoService.saveMedico(request, clinicaKey);
			} else {
				System.out.println("REgistrando usuario ############# ");
				dataSaved = userService.saveUser(request,clinicaKey);
			}
            
			return ResponseEntity.status(HttpStatus.CREATED).body(dataSaved);
            
        } catch (Exception e) {
        	
        	ErrorResponse erro = new ErrorResponse(HttpStatus.CONFLICT.value(),e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
        	
        }
		
	}
	
	// /api/MxxFYRGkkh84e98mR6fp9pGY/usuariosclinica/{clinicaKey}
	@GetMapping("/usuariosclinica/{clinicaKey}")
	public ResponseEntity<?> getUsuariosbyClinica(@PathVariable("clinicaKey") String clinicaKey) {
		
		try {
			
            List<UsuarioBaseDTO> usuarios = userService.getUsuariosByClinica(clinicaKey);
            
            if (usuarios.isEmpty()) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(
                            HttpStatus.NOT_FOUND.value(),
                            "Nenhum usuário encontrado para esta clínica"
                        ));
            }
            
            return ResponseEntity.ok(usuarios);
            
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
