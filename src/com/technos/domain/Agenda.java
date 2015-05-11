package com.technos.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.technos.enums.TipoAgenda;

@Entity
@Table(name = "tab_agenda")
public class Agenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;

	private boolean diaTodo;

	private TipoAgenda tipoAgenda;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="evento_id",nullable=true)
	private Evento evento;
	
	// getters and setters
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date datafim) {
		this.dataFim = datafim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public boolean isDiaTodo() {
		return diaTodo;
	}

	public void setDiaTodo(boolean diaTodo) {
		this.diaTodo = diaTodo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + this.id;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Agenda other = (Agenda) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Agenda [id=" + id + ", descricao=" + descricao
				+ ", dataInicio=" + dataInicio + ", dataFim=" + dataFim
				+ ", diaTodo=" + diaTodo + ", tipoAgenda=" + tipoAgenda + "]";
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	
	
}