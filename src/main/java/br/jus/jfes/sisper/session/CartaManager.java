package br.jus.jfes.sisper.session;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.jus.jfes.sisper.modelo.Carta;
import br.jus.jfes.sisper.modelo.Documento;
//import br.jus.jfes.sisper.util.HibernateUtil;

@Stateless
@LocalBean
public class CartaManager extends BaseDAO<Carta, Long> {
	
	private static Logger logger = Logger.getLogger(CartaManager.class);	
	
	private String expressaoHql="";
//	private String expressaoOrderBy=" order by c.identDestinatario"; novo
	
	public String getExpressaoHql() {
		return expressaoHql;
	}
	public void setExpressaoHql(String expressaoHql) {
		this.expressaoHql = expressaoHql;
	}

	public CartaManager(Class<Carta> persistenteClass) {
		super(persistenteClass);
	}
	
	public CartaManager(){
		super(Carta.class);
	}
		
	public void salvaC(Carta carta){
	/*	try {
			this.salva(carta);
		}
		catch (ConstraintViolationException cve) { 
			// numero correio ja cadastrado.
			Long id = carta.getCodigo();
			getSession().close();		
			logger.info("NRCorreio ja cadastrado No Sistema ");
			logger.info("CartaC deletando o documento id : "+id);
			deleteDocumento(id);
		} */
		this.salva(carta);		
	}
	
	private void deleteDocumento(Long cartaId) {
		logger.info("deleteDocumento...");		
		this.delete(cartaId);
		
	}
	
}
