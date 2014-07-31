package br.jus.jfes.sisper.session;

import org.jboss.logging.Logger;

import br.jus.jfes.sisper.modelo.TabCep;

public class CepManager extends BaseDAO<TabCep, String> {
	private static Logger logger = Logger.getLogger(CepManager.class);
	private String expressaoHql="";
//	private String expressaoOrderBy=" order by c.identDestinatario";
	
	public String getExpressaoHql() {
		return expressaoHql;
	}
	public void setExpressaoHql(String expressaoHql) {
		this.expressaoHql = expressaoHql;
	}

	public CepManager(Class<TabCep> persistenteClass) {
		super(persistenteClass);
	}
	
	public CepManager(){
		super(TabCep.class);
	}
		
	/*
	private void deleteDocumento(String cepId) {
		logger.info("deleteDocumento...");
		TabCep cep = (TabCep) getSession().load(TabCep.class, cepId);
		if (cep != null) {
			logger.info("cep != null");
			getSession().delete(cep);
			getSession().flush();
			closeSession();
		}	
	}*/	

}
