package com.technos.manager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.technos.daoImpl.ContatoDaoImpl;
import com.technos.daoImpl.EventoDaoImpl;
import com.technos.daoImpl.PagamentoDaoImpl;
import com.technos.domain.Contato;
import com.technos.domain.Evento;
import com.technos.domain.Pagamento;
import com.technos.enums.SituacaoEvento;
import com.technos.enums.SituacaoPagamento;
import com.technos.util.Data;

@ManagedBean(name = "eventoMB")
@ViewScoped
public class EventoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	// evento
	private Evento registro;

	private List<Evento> lista;

	private Evento eventoSelecionado;

	private int numeroParcelas;

	private Date dataPrimeiraParcela;

	private int intervaloEntreParcela;

	private boolean primeiraPaga;
	
	private BigDecimal valorTotalPagto;

	private String totalPagto;
	
	private String totalPagtoVencido;
	
	private String totalPagtoAVencer;
	
	private String totalPagtoPago;
	
	private String totalPagtoCancelado;
	
	// contato
	private int contatoSelecionado;

	private List<Contato> listaContato;

	// pagamento
	private Pagamento registroPagto;

	private List<Pagamento> listaPagto;

	// situacao evento
	private SituacaoEvento listaSituacao[] = { SituacaoEvento.AGUARDANDO,
			SituacaoEvento.CONFIRMADO, SituacaoEvento.REALIZADO };

	private SituacaoEvento situacaoSelecionada = SituacaoEvento.AGUARDANDO;

	// situacao pagamento
	private SituacaoPagamento listaSituacaoPagto[] = SituacaoPagamento.values();

	private SituacaoPagamento situacaoSelecionadaPagto = SituacaoPagamento.ABERTO;

	@PostConstruct
	public void init() {
		registro = new Evento();
		lista = new EventoDaoImpl().getAll(Evento.class);
		eventoSelecionado = new Evento();
		// contato
		contatoSelecionado = 0;
		listaContato = new ContatoDaoImpl().getAll(Contato.class);
		// pagamento
		registroPagto = new Pagamento();
		listaPagto = new ArrayList<>();// PagamentoDaoImpl().getAll(Pagamento.class);
		// gerar parcelas
		setDataPrimeiraParcela(new Date());
		setIntervaloEntreParcela(30);
	}

	public void limparForm() {
		registro = new Evento();
		setDataPrimeiraParcela(new Date());
		setIntervaloEntreParcela(30);
	}

	public void cadastrar() {

		EventoDaoImpl dao = new EventoDaoImpl();
		registro.setContato(new ContatoDaoImpl().getById(Contato.class,
				Integer.valueOf(contatoSelecionado)));
		registro.setSituacao(situacaoSelecionada.toInt());

		try {
			dao.getEntityManager().getTransaction().begin();
			registro = dao.getEntityManager().merge(registro);
			doGerarParcelas(dao);
			dao.getEntityManager().getTransaction().commit();

			FacesMessage msg = new FacesMessage(
					"Evento cadastrado com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			dao.getEntityManager().getTransaction().rollback();
			FacesMessage msg = new FacesMessage("Erro ao Gerar Parcelas!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} finally {
		}

		registro = new Evento();
		lista = new EventoDaoImpl().getAll(Evento.class);
	}

	private void doGerarParcelas(EventoDaoImpl dao) {
		Date d = getDataPrimeiraParcela();
		if ((getNumeroParcelas() > 0) && (getDataPrimeiraParcela() != null)) {
			for (int i = 1; i <= getNumeroParcelas(); i++) {
				Pagamento p = new Pagamento();
				p.setDataVencimento(d);
				if (intervaloEntreParcela == 30) {
					d = Data.proximoMes(d);
				} else {
					d = Data.somaDias(d, intervaloEntreParcela);
				}
				p.setNome("Parcela - " + i + "/" + getNumeroParcelas());
				p.setInfo("Parcela - " + i + "/" + getNumeroParcelas());
				p.setValor(registro.getValor().divide(
						BigDecimal.valueOf(getNumeroParcelas())));
				p.setEvento(registro);
				p.setSituacao(0);
				dao.getEntityManager().merge(p);
			}
		}

	}

	public void excluir() {
		EventoDaoImpl dao = new EventoDaoImpl();
		registro.setContato(new ContatoDaoImpl().getById(Contato.class,
				Integer.valueOf(contatoSelecionado)));
		registro.setSituacao(situacaoSelecionada.toInt());
		dao.remove(registro);
		registro = new Evento();
		lista = new EventoDaoImpl().getAll(Evento.class);
	}

	/* pagamentos */

	public void cadastrarPagto() {
	}

	public void excluirPagto() {
		PagamentoDaoImpl dao = new PagamentoDaoImpl();
		registroPagto.setEvento(registro);
		dao.remove(registroPagto);
		registroPagto = new Pagamento();
		listaPagto = new PagamentoDaoImpl().getAll(Pagamento.class);
	}

	/* gettings and settings */

	public boolean isPrimeiraPaga() {
		return primeiraPaga;
	}

	public void setPrimeiraPaga(boolean primeiraPaga) {
		this.primeiraPaga = primeiraPaga;
	}

	public Evento getRegistro() {
		return registro;
	}

	public void setRegistro(Evento registro) {
		this.registro = registro;
	}

	public List<Evento> getLista() {
		return lista;
	}

	public void setLista(List<Evento> lista) {
		this.lista = lista;
	}

	public int getContatoSelecionado() {
		return contatoSelecionado;
	}

	public void setContatoSelecionado(int contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}

	public List<Contato> getListaContato() {
		return listaContato;
	}

	public void setListaContato(List<Contato> listaContato) {
		this.listaContato = listaContato;
	}

	public void setListaSituacao(SituacaoEvento[] listaSituacao) {
		this.listaSituacao = listaSituacao;
	}

	public SituacaoEvento getSituacaoSelecionada() {
		return situacaoSelecionada;
	}

	public void setSituacaoSelecionada(SituacaoEvento situacaoSelecionada) {
		this.situacaoSelecionada = situacaoSelecionada;
	}

	public SituacaoEvento[] getListaSituacao() {
		return listaSituacao;
	}

	public Pagamento getRegistroPagto() {
		return registroPagto;
	}

	public void setRegistroPagto(Pagamento registroPagto) {
		this.registroPagto = registroPagto;
	}

	public List<Pagamento> getListaPagto() {
		return listaPagto;
	}

	public void setListaPagto(List<Pagamento> listaPagto) {
		this.listaPagto = listaPagto;
	}

	public SituacaoPagamento[] getListaSituacaoPagto() {
		return listaSituacaoPagto;
	}

	public void setListaSituacaoPagto(SituacaoPagamento listaSituacaoPagto[]) {
		this.listaSituacaoPagto = listaSituacaoPagto;
	}

	public SituacaoPagamento getSituacaoSelecionadaPagto() {
		return situacaoSelecionadaPagto;
	}

	public void setSituacaoSelecionadaPagto(
			SituacaoPagamento situacaoSelecionadaPagto) {
		this.situacaoSelecionadaPagto = situacaoSelecionadaPagto;
	}

	public Evento getEventoSelecionado() {
		return eventoSelecionado;
	}

	public void setEventoSelecionado(Evento eventoSelecionado) {
		this.eventoSelecionado = eventoSelecionado;
		// listaPagto = new PagamentoDaoImpl().getAllWhere(Pagamento.class,
		// "o.evento_id = "+eventoSelecionado.getId());
		listaPagto = new EventoDaoImpl().listarPagamentos(eventoSelecionado);
		// listaPagto = new PagamentoDaoImpl().getAll(Pagamento.class);
		
		
		
	}
	

	public Date getDataPrimeiraParcela() {
		return dataPrimeiraParcela;
	}

	public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
		this.dataPrimeiraParcela = dataPrimeiraParcela;
	}

	public int getIntervaloEntreParcela() {
		return intervaloEntreParcela;
	}

	public void setIntervaloEntreParcela(int intervaloEntreParcela) {
		this.intervaloEntreParcela = intervaloEntreParcela;
	}

	public int getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(int numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public BigDecimal getValorTotalPagto() {
		return valorTotalPagto;
	}

	public void setValorTotalPagto(BigDecimal valorTotalPagto) {
		this.valorTotalPagto = valorTotalPagto;
	}

	public String getTotalPagto() {
		BigDecimal total= new BigDecimal(0);
		 for(Pagamento p : listaPagto ) {
	            total = total.add( p.getValor() );
	        }
		 DecimalFormat df = new DecimalFormat();  
	        df.applyPattern("R$ #,##0.00");  
	        
		return df.format(total);
	}

	public void setTotalPagto(String totalPagto) {
		this.totalPagto = totalPagto;
	}

	
	
	public String getTotalPagtoVencido() {
		BigDecimal total= new BigDecimal(0);
		 for(Pagamento p : listaPagto ) {
	            total = total.add( p.getValor() );
	        }
		 DecimalFormat df = new DecimalFormat();  
	        df.applyPattern("R$ #,##0.00");  
	        
		return df.format(total);
	}

	public void setTotalPagtoVencido(String totalPagtoVencido) {
		this.totalPagtoVencido = totalPagtoVencido;
	}

	public String getTotalPagtoAVencer() {
		BigDecimal total= new BigDecimal(0);
		 for(Pagamento p : listaPagto ) {
	            total = total.add( p.getValor() );
	        }
		 DecimalFormat df = new DecimalFormat();  
	        df.applyPattern("R$ #,##0.00");  
	        
		return df.format(total);
	}

	public void setTotalPagtoAVencer(String totalPagtoAVencer) {
		this.totalPagtoAVencer = totalPagtoAVencer;
	}

	public String getTotalPagtoPago() {
		BigDecimal total= new BigDecimal(0);
		 for(Pagamento p : listaPagto ) {
	            total = total.add( p.getValor() );
	        }
		 DecimalFormat df = new DecimalFormat();  
	        df.applyPattern("R$ #,##0.00");  
	        
		return df.format(total);
	}

	public void setTotalPagtoPago(String totalPagtoPago) {
		this.totalPagtoPago = totalPagtoPago;
	}

	public String getTotalPagtoCancelado() {
		BigDecimal total= new BigDecimal(0);
		 for(Pagamento p : listaPagto ) {
	            total = total.add( p.getValor() );
	        }
		 DecimalFormat df = new DecimalFormat();  
	        df.applyPattern("R$ #,##0.00");  
	        
		return df.format(total);
	}

	public void setTotalPagtoCancelado(String totalPagtoCancelado) {
		this.totalPagtoCancelado = totalPagtoCancelado;
	}

}
