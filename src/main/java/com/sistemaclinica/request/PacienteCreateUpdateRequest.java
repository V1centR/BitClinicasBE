package com.sistemaclinica.request;

import lombok.Data;

@Data
public class PacienteCreateUpdateRequest {
	
	private String nomecompleto;
    private String cpf;
    private String rg;
    private String dataNasc;
    private String sexo;
    private String telefone;
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String tiposanguineo;
    private String alergiasdesc;
    private String medicacoesdesc;
    private String doencaspreexistentes;
    private String contatoemergenciaNome;
    private String contatoemergenciaTelefone;
    private String contatoemergenciaParentesco;
    private String observacoes;
	
	public PacienteCreateUpdateRequest() {}
		

}
