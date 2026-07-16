package com.sistemaclinica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicoResponsavelDTO {
	
	private Integer id;
    private String avatar;
    private String conselho;
    private String especialidade;
    private String nome;
    private String sexo;
    private String email;
    private Integer status;
    private String telefone;
   // private String pwdu59k7auvwyu;
    private Integer firstlogin;
    private String securitycode;
    private Integer deleted;
    private String observacoes;

}
