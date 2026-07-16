package com.sistemaclinica.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class ClinicaUpdateRequest {
	
	  
    @NotBlank(message = "Nome da clínica é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nomeclinica;
    
    @NotBlank(message = "CNPJ é obrigatório")
    //@Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos")
    @Size(max = 15, message = "MAX 2 characteres")
    private String cnpjclinica;
    
    @NotBlank(message = "Telefone é obrigatório")
    //@Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "Telefone inválido")
    @Size(max = 15, message = "Nome deve ter no máximo 100 caracteres")
    private String telefoneclinica;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String enderecoclinica;
    
    @NotBlank(message = "Cidade é obrigatória")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidadeclinica;
    
    @NotBlank(message = "UF é obrigatória")
    //@Pattern(regexp = "[A-Z]{2}", message = "UF deve ter 2 letras maiúsculas")
    @Size(max = 2, message = "MAX 2 characteres")
    private String estadoclinica;
    
    @NotBlank(message = "CEP é obrigatório")
    //@Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
    @Size(max = 10, message = "MAX 10 characteres")
    private String cepclinica;
    
    @NotNull(message = "Médico responsável é obrigatório")
    private Integer medresponsavel;  // ID do médico responsável
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricaoclinica;
    
    @Size(max = 3, message = "MAx 3")
    private String bannertype;
    
    @Size(max = 255, message = "URL da logomarca muito longa, faça o download da imagem e tente o upload")
    private String logomarca;
    
    // Construtor padrão
    public ClinicaUpdateRequest() {
    }
	
	

}
