package com.sistemaclinica.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Funcaoclinica;
import com.sistemaclinica.entity.Usersatendente;
import com.sistemaclinica.repo.ClinicasRepo;
import com.sistemaclinica.repo.PermissoesRepo;
import com.sistemaclinica.repo.UserAtendenteRepo;
import com.sistemaclinica.request.ClinicaRegisterRequest;

@RestController
@RequestMapping("/api/register-clinica")
public class ClinicaRegisterController {

    @Autowired
    private ClinicasRepo clinicasRepo;

    @Autowired
    private UserAtendenteRepo userRepo;

    @Autowired
    private PermissoesRepo permissoesRepo;

    @PostMapping
    public ResponseEntity<?> registerClinica(@RequestBody ClinicaRegisterRequest request) {
        try {
            // 1. Validations
            if (request.getCnpj() == null || request.getCnpj().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "CNPJ é obrigatório."));
            }
            if (request.getCompanyName() == null || request.getCompanyName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Nome da clínica é obrigatório."));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email do administrador é obrigatório."));
            }

            // Check if CNPJ already registered
            if (clinicasRepo.existsByCnpjclinica(request.getCnpj())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("success", false, "message", "Este CNPJ já está cadastrado."));
            }

            // Check if email already registered for an active user
            if (userRepo.findByEmailAndDeletedIsNullAndStatus(request.getEmail(), 1).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("success", false, "message", "Este e-mail já está cadastrado em outra conta ativa."));
            }

            // 2. Generate new clinic properties
            String accessKey = UUID.randomUUID().toString().replace("-", "");
            String tempPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
            String hashedPassword = calculateHmacSha256(tempPassword, "Wza5ej5agmmzJ3HgGmRs82GkJVVjmWcs");

            // 3. Create and Save Clinica
            Clinica clinica = new Clinica();
            clinica.setNomeclinica(request.getCompanyName());
            clinica.setCnpjclinica(request.getCnpj());
            clinica.setEmail(request.getEmail());
            clinica.setEnderecoclinica("Não informado");
            clinica.setTelefoneclinica("Não informado");
            clinica.setCidadeclinica("");
            clinica.setUfclinica("");
            clinica.setCepclinica("");
            clinica.setRegistrodata(LocalDateTime.now());
            clinica.setAccessKey(accessKey);
            clinica.setPaymentstatus(1);

            Clinica savedClinica = clinicasRepo.save(clinica);

            // 4. Create and Save Admin User
            Funcaoclinica adminRole = permissoesRepo.findById(1) // Admin role is ID 1
                    .orElseThrow(() -> new RuntimeException("Função de administrador não encontrada no banco."));

            int numeroAvatar = ThreadLocalRandom.current().nextInt(1, 7);
            String avatarPath = numeroAvatar + "generic_avatar_admin.png";

            Usersatendente adminUser = new Usersatendente();
            adminUser.setNome("Administrador");
            adminUser.setEmail(request.getEmail());
            adminUser.setPassword(hashedPassword);
            adminUser.setStatus(1);
            adminUser.setFirstlogin(1);
            adminUser.setAvatar(avatarPath);
            adminUser.setClinica(savedClinica);
            adminUser.setFuncaoclinica(adminRole);

            userRepo.save(adminUser);

            // 5. Build and return response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Clínica cadastrada com sucesso!");
            response.put("accessKey", accessKey);
            response.put("email", request.getEmail());
            response.put("tempPassword", tempPassword);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Erro interno ao cadastrar clínica: " + e.getMessage()));
        }
    }

    private String calculateHmacSha256(String data, String key) {
        try {
            byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA256");
            sha256_HMAC.init(keySpec);
            byte[] macData = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            for (byte b : macData) {
                result.append(String.format("%02x", b));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular hash de senha", e);
        }
    }
}
