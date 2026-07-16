package com.sistemaclinica.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sistemaclinica.entity.Agendamento;
import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.entity.Usersatendente;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AgendamentoPorDataDTO {
	
	
	private String nomecompleto;
	private String observacoes;
	//private String cpf;
	private String sexo;
	private String telefone;
	private String email;
	private String datanasc;
	private int idAgendamento;
	private String status;
	private String medico;
	private String convenio;
    private LocalDateTime dataAgendamento;
    private LocalDateTime dataRegistro;
    private String atendente;
    private String atendenteAvatar;
	
	public AgendamentoPorDataDTO(Agendamento agendaData) {
		
		this.nomecompleto = agendaData.getPacienteBean() != null ? agendaData.getPacienteBean().getNomecompleto() : null;
		this.observacoes = agendaData.getObservacoes();
		//this.cpf = agendaData.getCpf();
		this.sexo = agendaData.getSexo();
		this.telefone = agendaData.getTelefone();
		this.email = agendaData.getPacienteBean() != null ? agendaData.getPacienteBean().getEmail() : null;
		this.datanasc = agendaData.getPacienteBean() != null ? agendaData.getPacienteBean().getDatanasc() : null;
		this.status = agendaData.getStatus();
		this.medico = agendaData.getMedicoBean() != null ? agendaData.getMedicoBean().getNome() : null;
		this.idAgendamento = agendaData.getId();
		this.convenio = agendaData.getConvenioBean() != null ? agendaData.getConvenioBean().getNomeconvenio() : null;
		this.dataAgendamento = agendaData.getDataAgendamento();
		this.dataRegistro = agendaData.getDataRegistro();
		this.atendente = agendaData.getUsersatendente() != null ? agendaData.getUsersatendente().getNome() : null;
		this.atendenteAvatar = agendaData.getUsersatendente() != null ? agendaData.getUsersatendente().getAvatar() : null;
		
	}
	
	

}
