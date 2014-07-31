package br.jus.jfes.sisper.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="gestao_sjes",name="tabela_cep")
public class TabCep implements Serializable {
	
	/**
	 * modificado no trunk
	 */
	private static final long serialVersionUID = -423899638667064577L;

	@Id
	@Column
	private String cep;
	
	@Column
	private String logradouro;
	
	@Column
	private String bairro;
	
	@Column
	private String localidade;
	
	@Column
	private String uf;

	public TabCep() {
		// implement
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	

}
