package com.technos.manager;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technos.daoImpl.UserDaoImpl;
import com.technos.model.auth.User;

@ManagedBean(name="usuarioMB")
@ViewScoped
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private User registro;
	
 	private List<User> lista;
 	
	@PostConstruct
	public void init(){
		registro = new User();
		lista = new UserDaoImpl().getAll(User.class);
	}

	public void cadastrar(){
		UserDaoImpl dao = new UserDaoImpl();
		dao.save(registro);
		registro = new User();
		lista = new UserDaoImpl().getAll(User.class);
	}
	public void excluir(){
		UserDaoImpl dao = new UserDaoImpl();
		dao.remove(registro);
		registro = new User();
		lista = new UserDaoImpl().getAll(User.class);
	}

	public User getRegistro() {
		return registro;
	}

	public void setRegistro(User registro) {
		this.registro = registro;
	}

	public List<User> getLista() {
		return lista;
	}

	public void setLista(List<User> lista) {
		this.lista = lista;
	}
	
	
}
