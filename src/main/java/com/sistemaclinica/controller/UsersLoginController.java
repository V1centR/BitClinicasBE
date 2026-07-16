package com.sistemaclinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.dto.UsersatendenteResponseDTO;
import com.sistemaclinica.request.LoginRequest;
import com.sistemaclinica.service.UsersService;

@RestController
@RequestMapping("/api/cTakHhTPYtMsXUgNDGhP")
public class UsersLoginController {
	
	private String authKey = "2jhKvUpnkHB8XHkrABVLvWcNr9e7Q4K79cKqNHVZ";
	
	@Autowired
	private UsersService userService;
	
	@PostMapping
	public ResponseEntity<?> execLogin(@RequestBody LoginRequest request) {
		
		
		System.out.println("LOGIN EXEC ############## ");
		System.out.println(request);
		
		if(request.getExecloginKey().contentEquals(authKey)) {
			
			System.out.println("ACCEPTED VALIDATION ######### ");
			
			try {
				UsersatendenteResponseDTO user = userService.getUserLogin(request);
				return ResponseEntity.ok(user);
				
			} catch (Exception e) {
			
				 return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                    .body(e.getMessage());
			}
		
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}
	

	
	
	@GetMapping("/LnkfNVazhycp3Ckn2Fdd")
	public ResponseEntity<?> grantLogin() {
		
		// 2jhKvUpnkHB8XHkrABVLvWcNr9e7Q4K79cKqNHVZ
		
		
		return ResponseEntity.ok("{\"execloginKey\":\"2jhKvUpnkHB8XHkrABVLvWcNr9e7Q4K79cKqNHVZ\"}");
		
	}

}
