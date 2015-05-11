package com.technos.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.technos.model.auth.User;

@Entity
@Table(name="tab_permissao")
public class Permissao implements Serializable {

	private static final long serialVersionUID = 1L;

 
	@Id
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
    private User user;
    
	@Id
	@ManyToOne
	@JoinColumn(name="funcionalidade_id",nullable=false)
    private Funcionalidade funcionalidade;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	@Override
	public String toString() {
		return "Permissao [user=" + user + ", funcionalidade=" + funcionalidade
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((funcionalidade == null) ? 0 : funcionalidade.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Permissao other = (Permissao) obj;
		if (funcionalidade == null) {
			if (other.funcionalidade != null)
				return false;
		} else if (!funcionalidade.equals(other.funcionalidade))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
