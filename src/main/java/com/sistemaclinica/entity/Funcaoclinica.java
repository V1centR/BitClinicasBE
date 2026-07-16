package com.sistemaclinica.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import lombok.Data;


/**
 * The persistent class for the funcaoclinica database table. OK
 * 
 */
@Data
@Entity
@NamedQuery(name="Funcaoclinica.findAll", query="SELECT f FROM Funcaoclinica f")
public class Funcaoclinica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;

	private String descricao;

	private Integer level;

	private String nomefuncao;

	//bi-directional many-to-one association to Medico
	@OneToMany(mappedBy="funcaoclinica")
	@JsonIgnore
	private List<Medico> medicos;

	//bi-directional many-to-one association to Usersatendente
	@OneToMany(mappedBy="funcaoclinica")
	private List<Usersatendente> usersatendentes;

	public Funcaoclinica() {
	}
	
/*
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNomefuncao() {
		return this.nomefuncao;
	}

	public void setNomefuncao(String nomefuncao) {
		this.nomefuncao = nomefuncao;
	}

	public List<Medico> getMedicos() {
		return this.medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public Medico addMedico(Medico medico) {
		getMedicos().add(medico);
		medico.setFuncaoclinica(this);

		return medico;
	}

	public Medico removeMedico(Medico medico) {
		getMedicos().remove(medico);
		medico.setFuncaoclinica(null);

		return medico;
	}

	public List<Usersatendente> getUsersatendentes() {
		return this.usersatendentes;
	}

	public void setUsersatendentes(List<Usersatendente> usersatendentes) {
		this.usersatendentes = usersatendentes;
	}

	public Usersatendente addUsersatendente(Usersatendente usersatendente) {
		getUsersatendentes().add(usersatendente);
		usersatendente.setFuncaoclinica(this);

		return usersatendente;
	}

	public Usersatendente removeUsersatendente(Usersatendente usersatendente) {
		getUsersatendentes().remove(usersatendente);
		usersatendente.setFuncaoclinica(null);

		return usersatendente;
	}*/

}