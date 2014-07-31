package br.jus.jfes.sisper.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(schema="sisper",name="tipo_documento")
public class TipoDocumento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 756849988559175523L;

	@Id
	@SequenceGenerator(name="tipodoc_gen", sequenceName="tipodoc_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="tipodoc_gen")
	@Column(name="codigo")
	private Long codigo;	
	
	@Column
	private String descricao;
	
	@Column
	private boolean ativo;
	
	public TipoDocumento() {
		
	}

	public TipoDocumento(Long value) {
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TipoDocumento))
			return false;
		final TipoDocumento other = (TipoDocumento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public String toString() {
		return codigo.toString();
	}
	
}
