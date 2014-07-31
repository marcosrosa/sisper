package br.jus.jfes.sisper.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(schema="sisper",name="documento")
@Inheritance(strategy=InheritanceType.JOINED)
public class Documento implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4512145941168050197L;

	@Id
	@SequenceGenerator(name="documento_gen", sequenceName="documento_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="documento_gen")
    @Column(name="codigo")
	private Long codigo;
    
    @Column(name="num_documento")
	private String numDoc;
    	                        
    @Column(name="cuidados")
    private String aosCuidados;
        
    @ManyToOne()
    @JoinColumn(name="cod_remetente")
    private Localidade remetente;
    
    @ManyToOne()
    @JoinColumn(name="cod_destinatario")
    private Localidade destinatario;
    
    //nomeInformado: pode ser remetente(nao cadastrado) ou destinatario.
    @Column(name="desc_externo")
    private String descExterno;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name="tipo_remessa")
    private TipoRemessa tipoRemessa;
        
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_tipo_doc")
	private TipoDocumento tipoDocumento;
			            	
	public Documento() {

	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public String getAosCuidados() {
		return aosCuidados;
	}

	public void setAosCuidados(String aosCuidados) {
		this.aosCuidados = aosCuidados;
	}

	public String getDescExterno() {
		return descExterno;
	}

	public void setDescExterno(String descExterno) {
		this.descExterno = descExterno;
	}

	public TipoRemessa getTipoRemessa() {
		return tipoRemessa;
	}

	public void setTipoRemessa(TipoRemessa tipoRemessa) {
		this.tipoRemessa = tipoRemessa;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	//vai sair daqui.
	/*public String getNomeRemetente() {
		String nomeRemetente = null;
		if (tipoRemessa == TipoRemessa.SEPEX && null != descDestinatario && !descDestinatario.isEmpty() ) {
			nomeRemetente = descDestinatario;
		}
		else {
			LocalidadeDAO2 remetenteDAO = new LocalidadeDAO2();
			nomeRemetente = remetenteDAO.getNomeRemetenteDoc(this.getCodigo());
		}
		return nomeRemetente;
	}*/

	public Localidade getRemetente() {
		return remetente;
	}

	public void setRemetente(Localidade remetente) {
		this.remetente = remetente;
	}

	public Localidade getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Localidade destinatario) {
		this.destinatario = destinatario;
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
		Documento other = (Documento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	} 
	
	
}

