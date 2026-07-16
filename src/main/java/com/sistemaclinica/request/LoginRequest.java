package com.sistemaclinica.request;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String email;
	private String encryptedpass;
	private boolean remember;
	private String execloginKey;
	

}
