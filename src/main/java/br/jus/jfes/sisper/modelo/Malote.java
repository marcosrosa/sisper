package br.jus.jfes.sisper.modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(schema="sisper",name="malote")
public class Malote implements Serializable{
	
	@Id
	@SequenceGenerator(name="malote_gen", sequenceName="malote_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="malote_gen")
	@Column(name="codigo")
	private Long codigo;

	@Column(name="data_envio")
	private Date dtEnvio;
	
	@ManyToOne
	@JoinColumn(name="cod_destino")	
	private Localidade destino;
	
	@Column(name="num_malote")
	private Integer numMalote;;
	
	@Column(name="data_recepcao")
	private Date dtRecepcao;
	
	@Column(name="tipo_malote")
	@Enumerated
	private TipoMalote tipoMalote;

	@ManyToOne
	@JoinColumn(name="cod_remetente")	
	private Localidade remetente;
		
	@Column(name="login_recepcao")
	private String loginRecepcao;
	
	@Column(name="fechado")
	private boolean fechado;
	
	
	@OneToMany(mappedBy="malote")
    @OrderBy("dtMovimento")
    private Collection<MaloteVirtual> docsMalote = new ArrayList<MaloteVirtual>();

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


	public Localidade getDestino() {
		return destino;
	}

	public void setDestino(Localidade destino) {
		this.destino = destino;
	}

	public Integer getNumMalote() {
		return numMalote;
	}

	public void setNumMalote(Integer numMalote) {
		this.numMalote = numMalote;
	}

	public Date getDtRecepcao() {
		return dtRecepcao;
	}

	public void setDtRecepcao(Date dtRecepcao) {
		this.dtRecepcao = dtRecepcao;
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

	public String getLoginRecepcao() {
		return loginRecepcao;
	}

	public void setLoginRecepcao(String loginRecepcao) {
		this.loginRecepcao = loginRecepcao;
	}

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}

	public Collection<MaloteVirtual> getDocsMalote() {
		return docsMalote;
	}

	public void setDocsMalote(Collection<MaloteVirtual> docsMalote) {
		this.docsMalote = docsMalote;
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
		Malote other = (Malote) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
