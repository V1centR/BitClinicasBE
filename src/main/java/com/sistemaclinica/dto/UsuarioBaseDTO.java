package com.sistemaclinica.dto;

import lombok.Data;

@Data
public abstract class UsuarioBaseDTO {
	
	private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private Integer firstlogin;
    private String clinicaNome;
    private String clinicaKey;
    private String funcaoNome;
    private int funcaoID;
    private String avatar;
    private int status;
    private String observacoes;
    private String tipo; // "MEDICO" ou "ATENDENTE"

}
