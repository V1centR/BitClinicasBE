package com.sistemaclinica.exception;



public class ErrorRegisterUserException extends RuntimeException {
	
	public ErrorRegisterUserException(String email) {
        super(String.format("Já existe um usuário com email '%s'",email));
    }
	
	

}
