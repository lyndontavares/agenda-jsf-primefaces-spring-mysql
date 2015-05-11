package com.technos.manager;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technos.daoImpl.FuncionalidadeDaoImpl;
import com.technos.domain.Funcionalidade;

@ManagedBean(name="funcionalidadeMB")
@ViewScoped
public class FuncionalidadeMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionalidade registro;
	
 	private List<Funcionalidade> lista;
 	
	@PostConstruct
	public void init(){
		registro = new Funcionalidade();
		lista = new FuncionalidadeDaoImpl().getAll(Funcionalidade.class);
	}

	public void cadastrar(){
		FuncionalidadeDaoImpl dao = new FuncionalidadeDaoImpl();
		dao.save(registro);
		registro = new Funcionalidade();
		lista = new FuncionalidadeDaoImpl().getAll(Funcionalidade.class);
	}
	public void excluir(){
		FuncionalidadeDaoImpl dao = new FuncionalidadeDaoImpl();
		dao.remove(registro);
		registro = new Funcionalidade();
		lista = new FuncionalidadeDaoImpl().getAll(Funcionalidade.class);
	}

	public Funcionalidade getRegistro() {
		return registro;
	}

	public void setRegistro(Funcionalidade registro) {
		this.registro = registro;
	}

	public List<Funcionalidade> getLista() {
		return lista;
	}

	public void setLista(List<Funcionalidade> lista) {
		this.lista = lista;
	}
	
	
}
