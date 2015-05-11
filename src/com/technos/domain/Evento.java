package com.technos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.technos.util.ValorExtenso;

@Entity
@Table(name = "tab_evento")
public class Evento implements Serializable, BasicEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contato_id", nullable = false)
	private Contato contato;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Pagamento> pagamentos;

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	@Column(columnDefinition = "varchar(200)")
	private String nome;

	@Column(length = 100)
	private String info;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataInicio", nullable = false)
	private Date dataInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataTermino")
	private Date dataTermino;

	@Column(columnDefinition = "int default 0")
	private int situacao;

	@Transient
	private String dataInicioFormatada;

	@Transient
	private String dataTerminoFormatada;

	@Transient
	private String valorExtenso;

	private BigDecimal valor;

	/* getters and setters */

	public String getValorExtenso() {
		if (getValor() == null) {
			return "";//new ValorExtenso().write(getValor());
		} else {
			return "";
		}
	}

	public String getDataInicioFormatada() {
		if (getDataInicio() == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(getDataInicio());
		}
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public String getDataTerminoFormatada() {
		if (getDataTermino() == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(getDataTermino());
		}
	}

	public void setDataTerminoFormatada(String dataTerminoFormatada) {
		this.dataTerminoFormatada = dataTerminoFormatada;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

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
		return "Evento [id=" + id + ", contato=" + contato + ", nome=" + nome
				+ ", info=" + info + ", dataInicio=" + dataInicio
				+ ", dataTermino=" + dataTermino + "]";
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
		Evento other = (Evento) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String getDescricaoSingular() {
		return "Evento";
	}

	@Override
	public String getDescricaoPlural() {
		return "Eventos";
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setValorExtenso(String valorExtenso) {
		this.valorExtenso = valorExtenso;
	}

}
