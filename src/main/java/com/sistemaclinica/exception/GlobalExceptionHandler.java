package com.sistemaclinica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sistemaclinica.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ErrorRegisterUserException.class)
    public ResponseEntity<ErrorResponse> handleEmailDuplicado(ErrorRegisterUserException e) {
        ErrorResponse erro = new ErrorResponse(HttpStatus.CONFLICT.value(),e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

}
