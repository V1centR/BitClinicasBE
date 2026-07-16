package com.sistemaclinica.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


/**
 * The persistent class for the tipoconsulta database table.
 * 
 */
@Entity
@NamedQuery(name="Tipoconsulta.findAll", query="SELECT t FROM Tipoconsulta t")
public class Tipoconsulta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nomeconsulta;

	//bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy="tipoconsultaBean")
	@JsonIgnore
	private List<Agendamento> agendamentos;

	public Tipoconsulta() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeconsulta() {
		return this.nomeconsulta;
	}

	public void setNomeconsulta(String nomeconsulta) {
		this.nomeconsulta = nomeconsulta;
	}

	public List<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Agendamento addAgendamento(Agendamento agendamento) {
		getAgendamentos().add(agendamento);
		agendamento.setTipoconsultaBean(this);

		return agendamento;
	}

	public Agendamento removeAgendamento(Agendamento agendamento) {
		getAgendamentos().remove(agendamento);
		agendamento.setTipoconsultaBean(null);

		return agendamento;
	}

}