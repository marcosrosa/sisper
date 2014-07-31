package br.jus.jfes.sisper.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="sisper",name="correio")
public class Carta extends Documento {
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = -876202165345933782L;

	@Column(name="num_correio")
	private String numCorreio;
		
	@Column
	private String cep;
	
	@Column
	private Integer peso;
	
	@Column
	private String numero;

	@Column
	private String logradouro;

	@Column
	private String bairro;

	@Column
	private String cidade;

	@Column
	private String uf;
	
	@Column(name="mp")
	private boolean maoPropria;
	
	@Column(name="ar")
	private boolean avisoReceb;
	
	public Carta() {
		// nao faz nada
	}

	public Carta(String logradouro, String bairro, String localidade, String uf) {
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = localidade;
		this.uf = uf;
	}

	
	public String getNumCorreio() {
		return numCorreio;
	}

	public void setNumCorreio(String numCorreio) {
		this.numCorreio = numCorreio;
	}


	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public boolean isMaoPropria() {
		return maoPropria;
	}

	public void setMaoPropria(boolean maoPropria) {
		this.maoPropria = maoPropria;
	}

	public boolean isAvisoReceb() {
		return avisoReceb;
	}

	public void setAvisoReceb(boolean avisoReceb) {
		this.avisoReceb = avisoReceb;
	}
	

}
