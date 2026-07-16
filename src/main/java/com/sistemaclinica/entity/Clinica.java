package com.sistemaclinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
 * The persistent class for the clinicas database table.
 * 
 */
@Data
@Entity
@Table(name="clinicas")
@NamedQuery(name="Clinica.findAll", query="SELECT c FROM Clinica c")
public class Clinica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;

	private String accessKey;

	private String cepclinica;
	
	private String cidadeclinica;
	
	private String ufclinica;

	private String cnpjclinica;

	private String descricaoclinica;

	private String discountexcp;

	private String enderecoclinica;

	private String logomarca;

	private String nomeclinica;
	
	private String bannertype;

	private Integer paymentstatus;

	private Integer promoid;

	private LocalDateTime registrodata;

	private String telefoneclinica;

	//bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy="clinica")
	private List<Agendamento> agendamentos;

	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medresponsavel")
	private Medico medico;

	//bi-directional many-to-one association to Medico
	@OneToMany(mappedBy="clinicaBean")
	private List<Medico> medicos;

	//bi-directional many-to-one association to Usersatendente
	@OneToMany(mappedBy="clinica")
	private List<Usersatendente> usersatendentes;


	/*
	@Id
	private int id;

	private String accesskey; //accesskey

	private String cepclinica;

	private String cnpjclinica;

	private String descricaoclinica;

	private String enderecoclinica;

	private String logomarca;

	private String nomeclinica;

	private String registrodata;

	private String telefoneclinica;

	//bi-directional many-to-one association to Medico
	@ManyToOne
	@JoinColumn(name="medresponsavel")
	private Medico medico;*/

	public Clinica() {
	}


}