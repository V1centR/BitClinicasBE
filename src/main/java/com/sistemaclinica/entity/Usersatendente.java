package com.sistemaclinica.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the usersatendentes database table.
 * 
 */
@Data
@Entity
@Table(name="usersatendentes")
@NamedQuery(name="Usersatendente.findAll", query="SELECT u FROM Usersatendente u")
public class Usersatendente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String avatar;

	private String cpf;

	private String email;

	private int firstlogin;

	private String nome;

	private String password;

	private String securitycode;

	private int status;
	
	private Integer deleted;

	private String telefone;
	
	private String observacoes;

	//bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy="usersatendente")
	@JsonIgnore
	private List<Agendamento> agendamentos;

	//bi-directional many-to-one association to Clinica
	@ManyToOne
	@JoinColumn(name="clinicaid")
	@JsonIgnore
	private Clinica clinica;

	//bi-directional many-to-one association to Funcaoclinica
	@ManyToOne
	@JoinColumn(name="permissao")
	@JsonIgnore
	private Funcaoclinica funcaoclinica;

	public Usersatendente() {
	}

}