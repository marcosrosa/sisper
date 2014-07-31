package br.jus.jfes.sisper.session;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.jus.jfes.sisper.modelo.Localidade;

@Stateless
@LocalBean
public class LocalidadeManager extends BaseDAO<Localidade, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final StringBuilder sbRemetenteDocSql = new StringBuilder("select u.descricao ")	   
	   .append(" from malote_documento t, malote m, local u ")
	   .append(" where m.codigo = t.cod_malote and m.cod_remetente = u.codigo ")
	   .append(" and m.tipo_malote = 0 ")
	   .append(" and t.cod_documento = :codDocumento");
	
	private String expressaoHql="";
	private String expressaoOrderBy=" order by l.tipoRemessa, l.descricao";
		
	public String getExpressaoHql() {
		return expressaoHql;
	}
	
	public void setExpressaoHql(String expressaoHql) {
		this.expressaoHql = expressaoHql;
	}

	public LocalidadeManager(Class<Localidade> persistenteClass) {
		super(persistenteClass);
	}
	
	public LocalidadeManager(){
		super(Localidade.class);
	}
		
	@SuppressWarnings("unchecked")
	public List<Localidade> buscaLocalidades() {
		// o que 0,3,6
		Query q = em.createQuery("from "
				+Localidade.class.getName()+" as l" 
				+" where l.ativo=true and l.tipoRemessa not in (0,3,6) "
				+expressaoOrderBy);
		return q.getResultList();
	}		

	@SuppressWarnings("unchecked")
	public List<Localidade> buscaLocalidadesPorTipo(Long remessa){
		StringBuilder sbQuery = new StringBuilder(" from ");
		sbQuery.append(Localidade.class.getName());
		sbQuery.append(" as l ");
		if (remessa < 2) {
            sbQuery.append(" where l.tipoRemessa in (1,4,6)"); 
		} else {
            sbQuery.append(" where l.tipoRemessa ="); 
            sbQuery.append(remessa);			
		}
		sbQuery.append(" and l.ativo = true ");
		sbQuery.append(expressaoOrderBy);
		Query q = em.createQuery(sbQuery.toString());
		return q.getResultList();
	}		
	
	public Localidade remetenteSepex() {
		Query q = em.createQuery("from "
				+Localidade.class.getName()+" as l "
				+" where l.tipoRemessa = 6");
		return (Localidade) q.getSingleResult();	
	}
	
	/*public String getNomeRemetenteDoc(Long rCodigo) {
		Query q = getSession().createSQLQuery(sbRemetenteDocSql.toString());
		q.setLong("codDocumento", rCodigo);
		String retorno = null;;
		try {
			retorno = (String) q.uniqueResult();
			
		} catch (HibernateException hibex) {
			System.out.println("<<metetodo NomeRemetenteDoc em LocalidadeDao Gerou Exception>>");
			System.out.println(hibex.getMessage());
		}
		return retorno;
	}*/
	
	
}
