package com.sistemaclinica.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FluxoTrabalhoDTO {
	
	private String atendenteNome;
	private String acao;
	private String flag;
	private LocalDateTime dataRegistro;
	
	/*{
	"atendentenome": "Kotarine Carvalho"
	"acao": "Novo agendamento",
	"flag": "new"
	"dataRegistro": [
            2026,
            3,
            10,
            19,
            49,
            30
        ]
}*/

}
