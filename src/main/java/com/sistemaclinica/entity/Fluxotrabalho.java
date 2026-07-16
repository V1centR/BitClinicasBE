package com.sistemaclinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import lombok.Data;


/**
 * The persistent class for the fluxotrabalho database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Fluxotrabalho.findAll", query="SELECT f FROM Fluxotrabalho f")
public class Fluxotrabalho implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String acao;

	@Column(name = "dataregistro")
    private LocalDateTime dataRegistro;

	private String flag;

	private String nomemedico;
	
	private String nomepaciente;

	//bi-directional many-to-one association to Clinica
	@ManyToOne
	@JoinColumn(name="clinicaid")
	@JsonIgnoreProperties("fluxotrabalhos") // Nome da coleção na Clinica
	private Clinica clinica;

	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medico")
	@JsonIgnore
	private Medico medicoBean;

	//bi-directional many-to-one association to Usersatendente
	@ManyToOne
	@JoinColumn(name="user")
	@JsonIgnore
	private Usersatendente usersatendente;

	public Fluxotrabalho() {
	}

}