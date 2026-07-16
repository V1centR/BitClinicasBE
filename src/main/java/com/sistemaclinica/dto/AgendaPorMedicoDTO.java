package com.sistemaclinica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendaPorMedicoDTO {
	
	private String horario;
	private String label;
	private String value;
	private Integer ocupado;
	//private String medicoOcupado;
	
	public AgendaPorMedicoDTO() {
		
	}

}
