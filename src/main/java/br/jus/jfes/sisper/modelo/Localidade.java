package br.jus.jfes.sisper.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table(schema="gestao_sjes",name="localidade")
public class Localidade implements Serializable {
	
	@Id
	@Column(name="codigo")
	private Long codigo;
	
	@Column(name="descricao")
	private String descricao;

	@Column(name="tipo_remessa")
	private TipoRemessa tipoRemessa;
	
	@Column(name="cod_orgao")
	@Enumerated(EnumType.ORDINAL)
	private Orgao orgao;
	
	@Column(name="grupo_cod", insertable=false,updatable=false)
	private Long agrupado;

	@Column(name="unidade_adm", insertable=false,updatable=false)
	private Long codUnidadeAdmCentral;
	
	@Column
	private boolean ativo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="unidade_adm")
	private Localidade unidadeAdmCentral;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="grupo_cod")
	private Localidade maloteAgrupado;

	public Localidade() {
		
	}
	
	public Localidade(String oLocal) {
		this.codigo = new Long(7777);
		this.descricao = oLocal;
	}
	
	public Localidade(Long value) {
		this.codigo = value;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoRemessa getTipoRemessa() {
		return tipoRemessa;
	}

	public void setTipoRemessa(TipoRemessa tipoRemessa) {
		this.tipoRemessa = tipoRemessa;
	}

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Localidade getUnidadeAdmCentral() {
		return unidadeAdmCentral;
	}

	public Localidade getMaloteAgrupado() {
		return maloteAgrupado;
	}

	public void setMaloteAgrupado(Localidade maloteAgrupado) {
		this.maloteAgrupado = maloteAgrupado;
	}
	
	public boolean isAgrupado() {
		if (this.agrupado!=null && this.agrupado>0) 
			return true;
		return false;
	}

	public boolean temCodUnidadeAdmCentral() {
		if (this.codUnidadeAdmCentral !=null && this.codUnidadeAdmCentral>0) 
			return true;
		return false;
	}

	public void setUnidadeAdmCentral(Localidade unidadeAdm) {
		this.unidadeAdmCentral = unidadeAdm;
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
		if (!(obj instanceof Localidade))
			return false;
		final Localidade other = (Localidade) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
