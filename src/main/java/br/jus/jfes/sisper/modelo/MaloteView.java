package br.jus.jfes.sisper.modelo;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(schema="sisper",name="vw_malote")
public class MaloteView implements Serializable {
	
	/**
	 * Foi modificado no branche dev-3 apos dev-5
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo")
	private Long codigo;

	@Column(name="data_envio")
	private Date dtEnvio;
		
	@Column(name="data_recepcao")
	private Date dtRecepcao;

	@Column(name="num_malote")
	private Integer numMalote;;
		
	@Column(name="tipo_malote")
	@Enumerated
	private TipoMalote tipoMalote;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_remetente")	
	private Localidade remetente;
			
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_destino")	
	private Localidade destino;

	@Column(name="fechado")
	private boolean fechado;
	
	@Column(name="qtd_documentos")
	private int qtdDocumentos;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDtEnvio() {
		return dtEnvio;
	}

	public void setDtEnvio(Date dtEnvio) {
		this.dtEnvio = dtEnvio;
	}

	public Date getDtRecepcao() {
		return dtRecepcao;
	}

	public void setDtRecepcao(Date dtRecepcao) {
		this.dtRecepcao = dtRecepcao;
	}

	public Integer getNumMalote() {
		return numMalote;
	}

	public void setNumMalote(Integer numMalote) {
		this.numMalote = numMalote;
	}

	public TipoMalote getTipoMalote() {
		return tipoMalote;
	}

	public void setTipoMalote(TipoMalote tipoMalote) {
		this.tipoMalote = tipoMalote;
	}

	public Localidade getRemetente() {
		return remetente;
	}

	public void setRemetente(Localidade remetente) {
		this.remetente = remetente;
	}


	public Localidade getDestino() {
		return destino;
	}

	public void setDestino(Localidade destino) {
		this.destino = destino;
	}

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}
	
	public int getQtdDocumentos() {
		return qtdDocumentos;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		MaloteView other = (MaloteView) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
