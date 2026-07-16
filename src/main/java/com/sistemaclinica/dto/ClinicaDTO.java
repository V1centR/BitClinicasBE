package com.sistemaclinica.dto;

import java.time.LocalDateTime;

import com.sistemaclinica.entity.Medico;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinicaDTO {
	
	private String nomeclinica;
    private String cnpjclinica;
    private String telefoneclinica;
    private String enderecoclinica;
    private String cidadeclinica;
    private String estadoclinica;
    private String cepclinica;
    private MedicoResponseDTO medresponsavel; // ID do médico responsável
    private LocalDateTime registrodata;
    private String descricaoclinica;
    private String logomarca;
    private String accesskey;
    private String bannertype;
    
    // Construtores
    public ClinicaDTO() {
    }

}
