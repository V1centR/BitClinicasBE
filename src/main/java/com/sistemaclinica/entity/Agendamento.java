package com.sistemaclinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * The persistent class for the agendamentos database table.
 * 
 */
@Entity
@Table(name="agendamentos")
@Data
@NamedQuery(name="Agendamento.findAll", query="SELECT a FROM Agendamento a")
public class Agendamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//private String cpf;

	@Column(name = "dataagendamento", nullable = false)
    private LocalDateTime dataAgendamento;

	@Column(name = "dataregistro")
    private LocalDateTime dataRegistro;

	private String observacoes;

	private String sexo;

	private String status;

	private String telefone;

	private String cpf;

	//bi-directional many-to-one association to Usersatendente
	@ManyToOne
	@JoinColumn(name="operadoratendente")
	@JsonIgnore
	private Usersatendente usersatendente;

	//bi-directional many-to-one association to Clinica
	@ManyToOne
	@JoinColumn(name="clinicaid")
	@JsonIgnore
	private Clinica clinica;

	//bi-directional many-to-one association to Convenio
	@ManyToOne
	@JoinColumn(name="convenio")
	private Convenio convenioBean;

	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medico")
	@JsonIgnore
	private Medico medicoBean;

	//bi-directional many-to-one association to Paciente
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente pacienteBean;

	//bi-directional many-to-one association to Tipoconsulta
	@ManyToOne
	@JoinColumn(name="tipoconsulta")
	@JsonIgnore
	private Tipoconsulta tipoconsultaBean;
	
	
	
/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cpf;

	//private String nomepaciente;

	private String observacoes;

	private String sexo;

	private String status;

	private String telefone;
	
	
	@Column(name = "dataagendamento", nullable = false)
    private LocalDateTime dataAgendamento;  // SEM @Temporal!
    
    @Column(name = "dataregistro")
    private LocalDateTime dataRegistro;

	//bi-directional many-to-one association to Usersatendente
	@ManyToOne
	@JoinColumn(name="operadoratendente")
	@JsonIgnore
	private Usersatendente usersatendente;

	//bi-directional many-to-one association to Convenio
	@ManyToOne
	@JoinColumn(name="convenio")
	private Convenio convenioBean;

	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medico")
	private Medico medico;

	//bi-directional many-to-one association to Tipoconsulta
	@ManyToOne
	@JoinColumn(name="tipoconsulta")
	@JsonIgnore
	private Tipoconsulta tipoconsultaBean;
	
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente pacienteBean; */

	public Agendamento() {
	}

	

}