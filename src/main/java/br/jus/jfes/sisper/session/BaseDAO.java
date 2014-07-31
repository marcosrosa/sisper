package br.jus.jfes.sisper.session;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

public class BaseDAO <T, ID extends Serializable> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6969207683887166980L;
	
	private static Logger logger = Logger.getLogger(BaseDAO.class);
	
	private Class<T> persistentClass;
	
	private String propriedadeId = null;
		
	@PersistenceContext(unitName="sisperPU")
	protected EntityManager em;
	
	public BaseDAO() {
		
	}
	
	protected EntityManager getEntityManager() {
		return this.em;
	}
	
	public BaseDAO(Class<T> persistenteClass) {
		this.persistentClass = persistenteClass;
		propriedadeId = propriedadeAnotadaComId();
	}
	
	public T load(ID id){
		//logger.info("lendo " + persistentClass + " com id "+ id);
		T objeto = (T)  em.find(persistentClass, id);
		return objeto;		
	}
	
	
	private String propriedadeAnotadaComId() {
		Field[] campos = persistentClass.getDeclaredFields();
		String nomePropriedade = null;
		for (Field campo : campos) {
		    campo.setAccessible(true);
		    if (campo.isAnnotationPresent(Id.class)) {
		    	nomePropriedade = campo.getName();
		        logger.info("Construtor -> Campo anotado com @Id: "+campo.getName());
		        break;
		    }                                                
		}  
		return nomePropriedade;
	}
	
	
	private Long valorId(T t) {
		Field campo = null;
		Long lcodigo = null;
		try {
			campo = t.getClass().getDeclaredField(propriedadeId);
			campo.setAccessible(true);
			lcodigo = (Long) campo.get(t);
		} catch (NoSuchFieldException nsfe) {
			logger.info("<<< compo nao existe>>>");
		} catch (IllegalAccessException iae) {
			logger.info("<<< acesso ilegal >>>");
		}
		return lcodigo;
	}
	
	public void salva(T t) {
		logger.info("Salvando " + t);
		// recuperar o campo id por reflexao para usar merge ou persist.
		
		logger.info("campo salva com @id :"+propriedadeId);
		
		Field campo = null;
		Long codigo = null;
		try {
			campo = t.getClass().getDeclaredField("id");
			campo.setAccessible(true);
			codigo = (Long) campo.get(t);
		} catch (NoSuchFieldException nsfe) {
			logger.info("<<< compo nao existe>>>");
		} catch (IllegalAccessException iae) {
			logger.info("<<< acesso ilegal >>>");
		}
		logger.info("<<< merge ou persist >>>");
		if (null != codigo) {
			logger.info("<< MERGE >>");
			em.merge(t);			
		} 
		else {
			logger.info("<< Persist >>");
			em.persist(t);			
		}
	}
	
	public void delete(ID id) {
		//logger.info("removendo " + t);
//		try {
		    T objeto = load(id);
	    	em.remove(objeto);
			//em.flush();
	/*	} catch ( cve){
			logger.info("cve no excluir");
			HibernateUtil.openSession().getTransaction().rollback();
			HibernateUtil.closeSession();			
		}
		catch (Exception e) {
			logger.info("exception no excluir");
			HibernateUtil.closeSession();
		} */
	}
		
	
	
}


