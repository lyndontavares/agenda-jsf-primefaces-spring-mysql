package com.technos.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tab_funcionalidade")
public class Funcionalidade implements Serializable,BasicEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

		
	private String nome;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Funcinalidade [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionalidade other = (Funcionalidade) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String getDescricaoSingular() {
		return "Funcionalidade";
	}

	@Override
	public String getDescricaoPlural() {
		return "Funionalidade";
	}

	
	
}
