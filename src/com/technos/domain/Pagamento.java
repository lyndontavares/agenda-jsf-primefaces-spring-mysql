package com.technos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
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
import javax.persistence.Transient;

@Entity
@Table(name="tab_pagamento")
public class Pagamento implements Serializable,BasicEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="evento_id",nullable=false)
	private Evento evento;

	@Column(length=100)
	private String nome;
	
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;

	@Temporal(TemporalType.DATE)
	private Date dataPagamento;

	@Transient
	private String dataVencimentoFormatada;

	@Transient
	private String dataPagamentoFormatada;

	@Column(length=1000)
	private String info;
 
	@Column(columnDefinition="decimal(10,2) default 0")
	private BigDecimal valor;
	
	@Column(columnDefinition="int default 0")
	private int situacao;
 	
	/* getters and setters */
	
	public int getId() {
		return id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public void setId(int id) {
		this.id = id;
	}





	public Evento getEvento() {
		return evento;
	}



	public void setEvento(Evento evento) {
		this.evento = evento;
	}



	public Date getDataVencimento() {
		return dataVencimento;
	}



	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}



	public Date getDataPagamento() {
		return dataPagamento;
	}



	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}



	public String getDataVencimentoFormatada() {
		if (getDataVencimento()==null){
			return "";
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(getDataVencimento());
		}
	}



	public void setDataVencimentoFormatada(String dataVencimentoFormatada) {
		this.dataVencimentoFormatada = dataVencimentoFormatada;
	}



	public String getDataPagamentoFormatada() {
		if (getDataPagamento()==null){
			return "";
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(getDataPagamento());
		}
	}



	public void setDataPagamentoFormatada(String dataPagamentoFormatada) {
		this.dataPagamentoFormatada = dataPagamentoFormatada;
	}



 

	public BigDecimal getValor() {
		return valor;
	}



	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}



	public int getSituacao() {
		return situacao;
	}



	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	@Override
	public String getDescricaoSingular() {
		return "Pagamento";
	}


	@Override
	public String getDescricaoPlural() {
		return "Pagamentos";
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id != other.id)
			return false;
		return true;
	}


 


	@Override
	public String toString() {
		return "Pagamento [id=" + id + ", evento=" + evento + ", nome=" + nome
				+ ", dataVencimento=" + dataVencimento + ", dataPagamento="
				+ dataPagamento + ", dataVencimentoFormatada="
				+ dataVencimentoFormatada + ", dataPagamentoFormatada="
				+ dataPagamentoFormatada + ", info=" + info + ", valor="
				+ valor + ", situacao=" + situacao  + "]";
	}


 
		

}
