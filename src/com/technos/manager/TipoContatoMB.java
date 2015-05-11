package com.technos.manager;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technos.daoImpl.TipoContatoDaoImpl;
import com.technos.domain.TipoContato;

@ManagedBean(name="tipoContatoMB")
@ViewScoped
public class TipoContatoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoContato registro;
	
 	private List<TipoContato> lista;
 	
	@PostConstruct
	public void init(){
		registro = new TipoContato();
		lista = new TipoContatoDaoImpl().getAll(TipoContato.class);
	}

	public void cadastrar(){
		TipoContatoDaoImpl dao = new TipoContatoDaoImpl();
		dao.save(registro);
		registro = new TipoContato();
		lista = new TipoContatoDaoImpl().getAll(TipoContato.class);
	}
	public void excluir(){
		TipoContatoDaoImpl dao = new TipoContatoDaoImpl();
		dao.remove(registro);
		registro = new TipoContato();
		lista = new TipoContatoDaoImpl().getAll(TipoContato.class);
	}

	public TipoContato getRegistro() {
		return registro;
	}

	public void setRegistro(TipoContato registro) {
		this.registro = registro;
	}

	public List<TipoContato> getLista() {
		return lista;
	}

	public void setLista(List<TipoContato> lista) {
		this.lista = lista;
	}
	
	
}
