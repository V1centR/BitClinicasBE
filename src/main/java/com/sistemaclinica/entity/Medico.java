package com.sistemaclinica.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * The persistent class for the medicos database table. OK
 * 
 */
@Data
@Entity
@Table(name="medicos")
@NamedQuery(name="Medico.findAll", query="SELECT m FROM Medico m")
public class Medico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;

	private String avatar;

	//private String crocrm;
	
	private String conselho;

	private String especialidade;

	private String nome;

	private String sexo;
	
	private String email;

	private int status;

	private String telefone;
	
	private String pwdu59k7auvwyu;
    
    private Integer firstlogin;
    
    private String securitycode;
    
    private Integer deleted;
    
    private String observacoes;
	

	//bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy="medicoBean")
	private List<Agendamento> agendamentos;

	//bi-directional many-to-one association to Clinica
	//@OneToMany(mappedBy="medico")
	//private List<Clinica> clinicas;

	//bi-directional many-to-one association to Clinica
	@ManyToOne
	@JoinColumn(name="clinica")
	@JsonIgnore
	private Clinica clinicaBean;

	//bi-directional many-to-one association to Funcaoclinica
	@ManyToOne
	@JoinColumn(name="permissao")
	private Funcaoclinica funcaoclinica;

	//bi-directional many-to-one association to Prontuario
	@OneToMany(mappedBy="medicoBean")
	private List<Prontuario> prontuarios;

	public Medico() {
	}


}