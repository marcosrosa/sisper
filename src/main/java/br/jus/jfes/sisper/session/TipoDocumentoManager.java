package br.jus.jfes.sisper.session;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.Query;

import br.jus.jfes.sisper.modelo.TipoDocumento;

@Stateless
@LocalBean
public class TipoDocumentoManager extends BaseDAO<TipoDocumento, Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String expressaoHql="";
	private String expressaoOrderBy=" order by t.descricao";
	
	public String getExpressaoHql() {
		return expressaoHql;
	}
	public void setExpressaoHql(String expressaoHql) {
		this.expressaoHql = expressaoHql;
	}

	public TipoDocumentoManager(Class<TipoDocumento> persistenteClass) {
		super(persistenteClass);
	}
	
	public TipoDocumentoManager(){
		super(TipoDocumento.class);
	}
	
	public List<TipoDocumento> buscaTipos(){
		Query q = em.createQuery("from "
				+TipoDocumento.class.getName()+" as tpd"
				+expressaoOrderBy);
		List<TipoDocumento> ltd = q.getResultList();
		return ltd;
	}

	public List<TipoDocumento> buscaTiposRemessa(Long rem){
		Query q = em.createQuery("from "
				+TipoDocumento.class.getName()+" as t " 
				+expressaoOrderBy);
		return q.getResultList();
	}
	

}
