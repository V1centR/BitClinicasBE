package com.sistemaclinica.dto;

import com.sistemaclinica.entity.Paciente;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PacienteSearchDto {
	
	private String cpf;
	private String nomecompleto;
	private String telefone;
	private String sexo;
	private Integer pacienteId;
	
	public PacienteSearchDto(Paciente pacienteData) {
		
		this.cpf = pacienteData.getCpf();
		this.nomecompleto = pacienteData.getNomecompleto();
		this.telefone = pacienteData.getTelefone();
		this.sexo = pacienteData.getSexo();
		this.pacienteId = pacienteData.getId();
	}

}
