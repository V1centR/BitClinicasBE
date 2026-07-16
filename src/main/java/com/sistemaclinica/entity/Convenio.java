package com.sistemaclinica.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the convenios database table.
 * 
 */
@Entity
@Table(name="convenios")
@NamedQuery(name="Convenio.findAll", query="SELECT c FROM Convenio c")
public class Convenio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nomeconvenio;

	//bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy="convenioBean")
	@JsonIgnore
	private List<Agendamento> agendamentos;

	public Convenio() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeconvenio() {
		return this.nomeconvenio;
	}

	public void setNomeconvenio(String nomeconvenio) {
		this.nomeconvenio = nomeconvenio;
	}

	public List<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Agendamento addAgendamento(Agendamento agendamento) {
		getAgendamentos().add(agendamento);
		agendamento.setConvenioBean(this);

		return agendamento;
	}

	public Agendamento removeAgendamento(Agendamento agendamento) {
		getAgendamentos().remove(agendamento);
		agendamento.setConvenioBean(null);

		return agendamento;
	}

}