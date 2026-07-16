package com.sistemaclinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaClinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaClinicaApplication.class, args);
	}
	

}

//Validar usuários duplicados no insert de novos pacientes
//com o alcool vc fica incapaz de desenvolver, vc tentou e não consegiu!

//19/01/2026 vc parou de beber e está evoluindo

// 26-01-2026 ritalina chegou agora vai
//pagina de agendamento registrando e filtrando horas diponiveis, ajustes finos restam.

/*
 
 07/02/2026 Problema do fuso horario resolvido
 OK: Ao mudar o dia no calendário com o médico já selecionado, 
 os horários da agenda não atualizam conforme os dias selecionados. OK resolvido.
 
 08/02/2026
 TODO: Buscar pacientes registrados por dia na tela de agendamento, dados da lista estão mocados no momento. OK
 
 28/02/2026
 Mais telas prontas, REgistro de pacientes, registro de usuário incluindo medicos, atualização de informações da clinica
 muitos problemas resolvidos.
 
 
  
  
 * 
 * 
 * */