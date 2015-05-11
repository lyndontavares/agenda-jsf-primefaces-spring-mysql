package com.technos.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="tab_contato")
public class Contato implements Serializable,BasicEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(length=100)
	private String nome;
	
	@Column(length=100)
	private String email;
	
	@Column(length=100)
	private String endereco;
	
	@Column(length=100)
	private String telefone;
	
	@Column(length=1000)
	private String info;
	
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Transient
	private String dataCadastroFormatada;
	
	@Column(columnDefinition="int(2) default 0")
	private int situacao;

	@Basic
	private int sexo;

	/*settins e gettings*/

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getDataCadastroFormatada() {
		if (getDataCadastro()==null){
			return "";
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(getDataCadastro());
		}
	}

	public void setDataCadastroFormatada(String dataCadastroFormatada) {
		this.dataCadastroFormatada = dataCadastroFormatada;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
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
		Contato other = (Contato) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String getDescricaoSingular() {
		return "Contato";
	}

	@Override
	public String getDescricaoPlural() {
		return "Contatos";
	}

	@Override
	public String toString() {
		return "Contato [id=" + id + ", nome=" + nome + ", email=" + email
				+ ", endereco=" + endereco + ", telefone=" + telefone
				+ ", info=" + info + ", dataCadastro=" + dataCadastro
				+ ", dataCadastroFormatada=" + dataCadastroFormatada
				+ ", situacao=" + situacao + "]";
	}
	
	
}
