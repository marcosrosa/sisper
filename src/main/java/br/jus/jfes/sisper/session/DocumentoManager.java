package br.jus.jfes.sisper.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.jus.jfes.sisper.modelo.Documento;
import br.jus.jfes.sisper.modelo.Malote;
import br.jus.jfes.sisper.modelo.MaloteVirtual;


@Stateless
@LocalBean
public class DocumentoManager extends BaseDAO<Documento, Long> {
	private static Logger logger = Logger.getLogger(DocumentoManager.class);
	
	private String expressaoHql="";
	private String expressaoOrderBy=" order by d.codigo";
	
	public String getExpressaoHql() {
		return expressaoHql;
	}

	public void setExpressaoHql(String expressaoHql) {
		this.expressaoHql = expressaoHql;
	}

	public DocumentoManager(Class<Documento> persistenteClass) {
		super(persistenteClass);
	}
	
	public DocumentoManager() {
		super(Documento.class);
	}
		
	public List<MaloteVirtual> buscaDocumentos(Malote maloteid){
		logger.info("<<<buscar os documentos>>> "+maloteid.getCodigo());
		List<MaloteVirtual> listaRetorno = null;
		if (maloteid != null ) {
			// retorna documentos do malote
			listaRetorno = new ArrayList( maloteid.getDocsMalote() );
			
			int qtd = maloteid.getDocsMalote().size();
			logger.info("buscaDocumetnos->qtd doc malote>>>"+qtd);
		}
		return  (List<MaloteVirtual>) listaRetorno;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Malote> buscaDocsPeriodo(Long lotacaoCod, Date dtIni, Date dtFim){
		Query q = em.createQuery("from "
				+Malote.class.getName()+" as g"
				+" where g.lotacao.codigo = :lotac " 
				+" and g.dataRemessa between :dataI and :dataF "				
				+" order by g.dataRemessa");
		q.setParameter("lotac", lotacaoCod);		
		q.setParameter("dataI", dtIni);
		q.setParameter("dataF", dtFim);
		
		List<Malote> listaRetorno = q.getResultList(); 
		
		return  listaRetorno;
	}

	//teste para ficar igual ao delphi (voltar)
	public Long buscaAnterior(Long malote, Long codDocumento) {
		if (malote == null) malote = -1L;
		if (codDocumento == null || codDocumento < 1) codDocumento = 99999999999L;
		Query q = em.createQuery("Select max(d.codigo) from Documento d  where d.malote.codigo = :numMalote and d.codigo < :numDocumento");
		
		q.setParameter("numMalote", malote);
		q.setParameter("numDocumento", codDocumento);
		
		Long anterior = (Long) q.getSingleResult();
		
		return anterior;
	}
	
	//teste para ficar igual ao delphi (avancar)
	public Long buscaProximo(Long malote, Long codDocumento) {
		if (malote == null) malote = -1L;
		if (codDocumento == null) codDocumento = 0L;
		Query q = em.createQuery("Select min(d.codigo) from Documento d  where d.malote.codigo = :numMalote and d.codigo > :numDocumento");
		
		q.setParameter("numMalote", malote);
		q.setParameter("numDocumento", codDocumento);
		
		Long result = (Long) q.getSingleResult();
		
		return result;
	}
	

}
