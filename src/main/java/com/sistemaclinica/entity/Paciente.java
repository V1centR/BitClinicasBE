package com.sistemaclinica.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * The persistent class for the pacientes database table. ok
 * 
 */
@Data
@Entity
@Table(name="pacientes")
@NamedQuery(name="Paciente.findAll", query="SELECT p FROM Paciente p")
public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String alergiasdesc;

	private String cep;

	private String cidade;

	private String contatoemergenciaNome;

	private String contatoemergenciaParentesco;

	private String contatoemergenciaTelefone;

	private String cpf;

	private String datanasc;

	private String doencaspreexistentes;

	private String email;

	private String endereco;

	private String estado;

	private String medicacoesdesc;

	private String nomecompleto;

	private String observacoes;

	private String rg;

	private String sexo;

	private String telefone;

	private String tiposanguineo;

	//bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy="pacienteBean")
	@JsonIgnore
	private List<Agendamento> agendamentos;

	//bi-directional one-to-one association to Prontuario
	//@OneToMany(mappedBy="pacienteBean")
	//private Prontuario prontuario;
	
	@OneToMany(mappedBy="pacienteBean")
	private List<Prontuario> prontuarios;


	public Paciente() {
	}

}