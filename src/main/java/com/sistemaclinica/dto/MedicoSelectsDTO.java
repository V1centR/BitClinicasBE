package com.sistemaclinica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicoSelectsDTO {
	
	private String nome;
	private Integer value;
	
	public MedicoSelectsDTO() {
		
	}

}
