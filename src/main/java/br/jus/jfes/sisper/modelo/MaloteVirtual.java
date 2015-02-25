package br.jus.jfes.sisper.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema="sisper",name="malote_documento")
public class MaloteVirtual implements Serializable {
	
	/**
	 *  tabela muitos p muitos com malotes enviados e recebidos
	 *  
	 */
	private static final long serialVersionUID = -511795180344961340L;
	
	@Id
	@SequenceGenerator(name="m_virtual_gen", sequenceName="malote_doc_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="m_virtual_gen")
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name="cod_malote")
	private Malote malote;
	
	@ManyToOne 
	@JoinColumn(name="cod_documento")
	private Documento documento;
	
/*	@ManyToOne
	@JoinColumn(name="unidade_adm")
	private Localidade UnidadeAdm; */
	
	@Column(name="data_movimento")
	private Date dtMovimento;
	
	@Column(name="login_usuario")
	private String loginUsuario;

	public MaloteVirtual() {
		
	}

	public MaloteVirtual(Malote pMalote, Documento pDocumento, String pLogin) {
		this.malote = pMalote;
		this.documento = pDocumento;
		this.loginUsuario = pLogin;
		this.dtMovimento = Calendar.getInstance().getTime();
	}
	
	public Malote getMalote() {
		return malote;
	}

	public void setMalote(Malote malote) {
		this.malote = malote;
	}
	
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

/*	public Localidade getUnidadeAdm() {
		return UnidadeAdm;
	}

	public void setUnidadeAdm(Localidade unidadeAdm) {
		UnidadeAdm = unidadeAdm;
	}*/

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDtMovimento() {
		return dtMovimento;
	}

	public void setDtMovimento(Date dtMovimento) {
		this.dtMovimento = dtMovimento;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}
	
}
