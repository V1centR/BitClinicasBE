package com.sistemaclinica.response;

public record LoginResponse(String accessToken, Long expiresIn, String role) {
    
}
