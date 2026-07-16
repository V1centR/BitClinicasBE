package com.sistemaclinica.service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class S3Service {
	
	/*   
    @Value("${aws.accessKeyId}")
    private String accessKey;
    
    @Value("${aws.secretAccessKey}")
    private String secretKey;
    
    @Value("${aws.bucketName}")
    private String bucketName;*/
    
    final String AWS_ACCESS_KEY = System.getenv("AWS_ACCESS_KEY_ID");
    final String AWS_SECRET_KEY = System.getenv("AWS_SECRET_ACCESS_KEY");
    final String BUCKET_NAME = "clinicaimages";

    
    
    //private AmazonS3 s3Client;
    
    private final S3Client s3Client;
    
    @Autowired
    private ClinicasService clinicaService;
    
    public S3Service() {
    	
    	// Criar provedor de credenciais com suas chaves
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
            AWS_ACCESS_KEY, 
            AWS_SECRET_KEY
        );
        
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadFile(MultipartFile file, String clinicaKey) throws IOException {
        // Gerar nome único para o arquivo
    	
    	String extensao = getExtensao(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + extensao;
        String filePath = clinicaKey + "/" +fileName;
    	
    	
        // Criar requisição de upload
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(filePath)
                .contentType(file.getContentType())
                .build();

        // Fazer upload
        PutObjectResponse response = s3Client.putObject(request,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        
        // 2. Salvar no banco (opcional - se quiser a URL permanente)
        clinicaService.updateLogo(clinicaKey, fileName);

        // Retornar a URL do arquivo
        return filePath;
    }
    
    private String getExtensao(String nomeOriginal) {
        if (nomeOriginal == null || !nomeOriginal.contains(".")) {
            return ""; // Sem extensão
        }
        return nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
    }

    public String uploadLogo(MultipartFile file, String clinicaKey) throws IOException {
        return uploadFile(file, clinicaKey);
    }

    public String uploadAvatar(MultipartFile file, String usuarioId) throws IOException {
        return uploadFile(file, "avatares/" + usuarioId);
    }

}
