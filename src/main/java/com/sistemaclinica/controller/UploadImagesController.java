package com.sistemaclinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistemaclinica.service.S3Service;

@RestController
@RequestMapping("/api/5GJRaLmagJAGXBnW2dUX")
public class UploadImagesController {
	
	// https://clinicaimages.s3.us-east-2.amazonaws.com
    @Value("${aws.imagelib.path}")
    private String bucketPath;
	
	@Autowired
    private S3Service s3Service;
    
    
    @PostMapping("/uploadimage")
    public ResponseEntity<?> uploadLogo(
            @RequestParam("logomarca") MultipartFile file,
            @RequestHeader("X-Clinica-Key") String clinicaKey) {
        
        try {
            // 1. Upload para S3 (retorna URL temporária)
            String urlTemporaria = s3Service.uploadLogo(file, clinicaKey);
            
            
            return ResponseEntity.ok(bucketPath + urlTemporaria);

            
        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no upload da imagem: " + e.getMessage());
        }
    }

}
