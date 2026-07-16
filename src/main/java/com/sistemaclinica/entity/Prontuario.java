package com.sistemaclinica.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import lombok.Data;


/**
 * The persistent class for the prontuario database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Prontuario.findAll", query="SELECT p FROM Prontuario p")
public class Prontuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int clinica;

	private String observacoes;


	private String receituario;
	
	//private int medico;
	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medico")
	private Medico medicoBean;

	//bi-directional one-to-one association to Paciente
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente pacienteBean;

	public Prontuario() {
	}
	
	/*
	 *@Id
	private int id;

	private int clinica;

	private String observacoes;

	private String receituario;

	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medico")
	private Medico medicoBean;

	//bi-directional many-to-one association to Paciente
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente pacienteBean;*/

	

}