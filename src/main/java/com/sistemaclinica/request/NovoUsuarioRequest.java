package com.sistemaclinica.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NovoUsuarioRequest {
	
	private Integer id;
	private String nome;
	private String telefone;
	private String email;
	private String funcao;
	private Integer funcaoId;
	//private String clinicaId;
	private String observacoes;
	private Integer sexo;
	private String avatar;
	private int firstLogin;
	private Integer status;
	private boolean ismedico;
	private String conselho;
	private String especialidade;
	
	/*
	 * {
    "id": 0,
    "nome": "DR hans chucrutz",
    "telefone": "11978784545",
    "email": "fds84df79s8fd@test.com",
    "funcaoId": 2,
    "observacoes": "ok",
    "sexo": 1,
    "ismedico": true,
    "crm": "123123",
    "funcaomedico": 21
}*/
	

}
