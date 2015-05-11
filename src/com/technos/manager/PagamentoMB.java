package com.technos.manager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.technos.daoImpl.EventoDaoImpl;
import com.technos.daoImpl.PagamentoDaoImpl;
import com.technos.domain.Evento;
import com.technos.domain.Pagamento;
import com.technos.enums.SituacaoPagamento;

@ManagedBean(name = "pagamentoMB")
@ViewScoped
public class PagamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pagamento registro;

	private List<Pagamento> lista;

 	private int eventoSelecionado;

	private List<Evento> listaEvento;

	private SituacaoPagamento listaSituacao[] = {SituacaoPagamento.ABERTO, SituacaoPagamento.PAGO};

	private SituacaoPagamento situacaoSelecionada = SituacaoPagamento.ABERTO;
	
	private String totalPagto;
 	
	@PostConstruct
	public void init() {
		registro = new Pagamento();
		lista = new PagamentoDaoImpl().getAll(Pagamento.class);
 		eventoSelecionado = 0;
		listaEvento = new EventoDaoImpl().getAllWhere(Evento.class," 1=1 ORDER by o.contato.nome, o.nome ");
	}

	public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }
         
        return results;
    }
	
	public void limparForm(){
		registro=new Pagamento();
		eventoSelecionado = 0;
	}

	public void cadastrar() {
		PagamentoDaoImpl dao = new PagamentoDaoImpl();
		registro.setSituacao( registro.getDataPagamento()==null? 0:1 );
 		registro.setEvento(new EventoDaoImpl().getById(Evento.class,
				Integer.valueOf(eventoSelecionado)));
		dao.save(registro);
		registro = new Pagamento();
		lista = new PagamentoDaoImpl().getAll(Pagamento.class);
	}

	public void excluir() {
		PagamentoDaoImpl dao = new PagamentoDaoImpl();
 		registro.setEvento(new EventoDaoImpl().getById(Evento.class,
				Integer.valueOf(eventoSelecionado)));
		dao.remove(registro);
		registro = new Pagamento();
		lista = new PagamentoDaoImpl().getAll(Pagamento.class);
	}

 	public Pagamento getRegistro() {
		return registro;
	}

	public void setRegistro(Pagamento registro) {
		this.registro = registro;
	}

	public List<Pagamento> getLista() {
		return lista;
	}

	public void setLista(List<Pagamento> lista) {
		this.lista = lista;
	}

	public SituacaoPagamento[] getListaSituacao() {
		return listaSituacao;
	}

	public void setListaSituacao(SituacaoPagamento listaSituacao[]) {
		this.listaSituacao = listaSituacao;
	}

	public SituacaoPagamento getSituacaoSelecionada() {
		return situacaoSelecionada;
	}

	public void setSituacaoSelecionada(SituacaoPagamento situacaoSelecionada) {
		this.situacaoSelecionada = situacaoSelecionada;
	}

	public int getEventoSelecionado() {
		return eventoSelecionado;
	}

	public void setEventoSelecionado(int eventoSelecionado) {
		this.eventoSelecionado = eventoSelecionado;
	}

	public List<Evento> getListaEvento() {
		return listaEvento;
	}

	public void setListaEvento(List<Evento> listaEvento) {
		this.listaEvento = listaEvento;
	}

	public String getTotalPagto() {
		BigDecimal total= new BigDecimal(0);
		 for(Pagamento p : lista) {
	            total = total.add( p.getValor() );
	        }
		 DecimalFormat df = new DecimalFormat();  
	        df.applyPattern("R$ #,##0.00");  
	        
		return df.format(total);
	}

	public void setTotalPagto(String totalPagto) {
		this.totalPagto = totalPagto;
	}

 	
}
