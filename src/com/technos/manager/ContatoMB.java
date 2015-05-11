package com.technos.manager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.technos.daoImpl.ContatoDaoImpl;
import com.technos.domain.Contato;
import com.technos.enums.Sexo;
import com.technos.enums.SituacaoContato;

@ManagedBean(name = "contatoMB")
@ViewScoped
public class ContatoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Contato registro;

	private List<Contato> lista;

	private SituacaoContato listaSituacao[] = { SituacaoContato.ATIVO,
			SituacaoContato.INATIVO };

	private SituacaoContato situacaoSelecionada = SituacaoContato.ATIVO;

	private Sexo listaSexo[] = Sexo.values();

	private Sexo sexoSelecionado = Sexo.HOMEM;

	@PostConstruct
	public void init() {
		registro = new Contato();
		lista = new ContatoDaoImpl().getAll(Contato.class);
		registro.setDataCadastro(new Date());
	}

	public void cadastrar() {

		/*
		 * No final do método Salvar() do seu bean adicione: RequestContext
		 * request = RequestContext.getCurrentInstance();
		 * request.addCallBackParam("sucesso", true); E no commandButton:
		 * oncomplete="if(args.sucesso == true) { dialog.hide(); }"
		 */
		ContatoDaoImpl dao = new ContatoDaoImpl();
		registro.setSituacao(situacaoSelecionada.toInt());
		dao.save(registro);
		registro = new Contato();
		lista = new ContatoDaoImpl().getAll(Contato.class);
	}

	public void excluir() {

		String msg = "";
		ContatoDaoImpl dao = new ContatoDaoImpl();
		try {

			// op.1
			// dao.remove(registro);

			// op.2
			msg = dao.callStoreProcedure("contato_cancelar", "123",
					registro.getId());

			FacesMessage fmsg = new FacesMessage(msg);
			FacesContext.getCurrentInstance().addMessage(null, fmsg);

		} catch (Exception e) {

			FacesMessage fmsg = new FacesMessage(msg);
			FacesContext.getCurrentInstance().addMessage(null, fmsg);

			registro = new Contato();
			lista = new ContatoDaoImpl().getAll(Contato.class);
		}
	}

	public Contato getRegistro() {
		return registro;
	}

	public void setRegistro(Contato registro) {
		this.registro = registro;
	}

	public List<Contato> getLista() {
		return lista;
	}

	public void setLista(List<Contato> lista) {
		this.lista = lista;
	}

	public SituacaoContato[] getListaSituacao() {
		return listaSituacao;
	}

	public void setListaSituacao(SituacaoContato listaSituacao[]) {
		this.listaSituacao = listaSituacao;
	}

	public Sexo[] getListaSexo() {
		return listaSexo;
	}

	public void setListaSexo(Sexo listaSexo[]) {
		this.listaSexo = listaSexo;
	}

	public SituacaoContato getSituacaoSelecionada() {
		return situacaoSelecionada;
	}

	public void setSituacaoSelecionada(SituacaoContato situacaoSelecionada) {
		this.situacaoSelecionada = situacaoSelecionada;
	}

	public Sexo getSexoSelecionado() {
		return sexoSelecionado;
	}

	public void setSexoSelecionado(Sexo sexoSelecionado) {
		this.sexoSelecionado = sexoSelecionado;
	}

}
