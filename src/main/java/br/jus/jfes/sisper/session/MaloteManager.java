package br.jus.jfes.sisper.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import javax.persistence.Query;

import br.jus.jfes.sisper.modelo.Documento;
import br.jus.jfes.sisper.modelo.Localidade;
import br.jus.jfes.sisper.modelo.Malote;
import br.jus.jfes.sisper.modelo.MaloteView;
import br.jus.jfes.sisper.modelo.MaloteVirtual;
import br.jus.jfes.sisper.modelo.TipoDocumento;
import br.jus.jfes.sisper.modelo.TipoMalote;
import br.jus.jfes.sisper.modelo.TipoRemessa;

@Stateless
@LocalBean
public class MaloteManager extends BaseDAO<Malote, Long> {
	private static Logger logger = Logger.getLogger(MaloteManager.class);
	
	private String expressaoHql="";
	private String expressaoOrderBy=" order by g.dataRemessa";
	
	private final StringBuilder sbBuscaMaloteRecSepex = new StringBuilder(" from ")
		.append(MaloteView.class.getName())
		.append(" as m ")
		.append(" join fetch m.destino r ")
		.append(" join fetch m.remetente x ")
		.append("  where m.dtRecepcao is null " )
		.append("    and m.tipoMalote = 0 ")
		.append(" order by m.dtEnvio");	

	private final StringBuilder sbBuscaMaloteRecUsr = new StringBuilder("from ")
		.append(MaloteView.class.getName())
		.append(" as m ")
		.append(" join fetch m.destino r ")
		.append(" where m.dtRecepcao is null ")
		.append(" and m.tipoMalote > 0 and fechado = true")
		.append("  and m.destino = :pDestino ")
		.append(" order by m.dtEnvio");
	

	
	public MaloteManager(Class<Malote> persistenteClass) {
		super(persistenteClass);
	}
	
	public MaloteManager(){
		super(Malote.class);
	}

	@PreDestroy
	private void finalizacao() throws Exception {
		logger.info(MaloteManager.class.getName()+"<< finalizando EJB>>");
	}
	
	@PostConstruct
	private void inicializacao() throws Exception {
		logger.info(MaloteManager.class.getName()+"<< pos construct chamado >>");
	}
	
	public String getExpressaoHql() {
		return expressaoHql;
	}
	public void setExpressaoHql(String expressaoHql) {
		this.expressaoHql = expressaoHql;
	}
		
	public Malote buscaMaloteHoje() {
		Calendar dataInicial = dataHoje();
		
		Query q = em.createQuery("from Malote m where m.dtEnvio > :pDataHoje");
		q.setParameter("pDataHoje", dataInicial.getTime());
		Malote maloteAtual = null;
		try {
		  	maloteAtual = (Malote) q.getSingleResult();
		  	//maloteAtual.getDocumentos().size();
		}
		catch (Exception e) {
			logger.info("nao conseguiu localizar malote HOJE");
			e.printStackTrace();
		}
		return maloteAtual;
	}
	
	private Malote buscaMaloteDestino(Localidade destinoMalote) {
		// filtrar por data do dia tambem.
		logger.info("buscar Malote Destino:"+destinoMalote.getDescricao());
						 
		Localidade destinoFinal = destinoMalote;
		TipoMalote tipoMalote = TipoMalote.SEPEX;
		
		if (destinoMalote.getTipoRemessa().equals(TipoRemessa.VARAS_INT) &&
				destinoMalote.temCodUnidadeAdmCentral()) {
			logger.info("<<< malote seadm interior >>");
			destinoFinal = destinoMalote.getUnidadeAdmCentral();
			tipoMalote = TipoMalote.SEADM_INT;
		} else {
			if (destinoMalote.getTipoRemessa().equals(TipoRemessa.INTERNO) && destinoMalote.isAgrupado()) {
				logger.info("<<< malote agrupado >>");
				destinoFinal = destinoMalote.getMaloteAgrupado();
			} else {		
				logger.info("<<< malote interno nao agrupado>>>");
			}
		}


		StringBuilder sb = new StringBuilder("from Malote m ");
		sb.append("where m.fechado = false and ");
		sb.append("m.tipoMalote = :pTipoMalote ");
		sb.append("and m.destino = :pDestino");
		Query q = em.createQuery(sb.toString());
		q.setParameter("pTipoMalote", tipoMalote);
		q.setParameter("pDestino", destinoFinal);
		
		Malote maloteEnviar = (Malote) q.getSingleResult();
		
		if (null == maloteEnviar) {
			logger.info("<<<bmd - malote novo>>");
			LocalidadeManager locDAO = new LocalidadeManager();
			maloteEnviar = new Malote();
			maloteEnviar.setDestino(destinoFinal);
			// rem : sepex ou sistema
			maloteEnviar.setRemetente(locDAO.remetenteSepex());
			maloteEnviar.setTipoMalote(tipoMalote);
			maloteEnviar.setDtEnvio(Calendar.getInstance().getTime());
			salva(maloteEnviar);
			//closeSession();
		}
		return maloteEnviar;
	}
	
	public int getQtdDocumentos(Malote dMalote) {
		Query q = em.createQuery("select count(*) " +
				"from Documento where malote = :pMalote");
		q.setParameter("pMalote", dMalote);
		Long quantidade = (Long) q.getSingleResult();
		return quantidade.intValue();
	}
	
	public List<MaloteView> buscaMalotesAbertos(Long lotacaoCod) {
		if (null == lotacaoCod) lotacaoCod = 0L;
		logger.info("buscaMalotesAbertos -> lotacaoCod -> "+lotacaoCod);
		Query q = em.createQuery("from "
				+MaloteView.class.getName()+" as m "
				+" where m.remetente.codigo = :lotac " 
				+" and m.dtRecepcao is null " 
				+" and m.tipoMalote = :tmalote" 
				+" order by m.dtEnvio");
		q.setParameter("lotac", lotacaoCod);
		q.setParameter("tmalote", TipoMalote.USUARIO); 
		List<MaloteView> listaRetorno = q.getResultList(); 
		return  listaRetorno;
	}


	public List<MaloteView> buscaMalotesFecharSepex(Localidade oDestino) {
		// TODO Auto-generated method stub
		logger.info("buscaMaloteFecharSepex: conferencia pelo destinatario.");
		Query q;
		if (null != oDestino) {
			// malotes conferidos pelo proprio setor-destinatario.
			q = em.createQuery("from "
					+MaloteView.class.getName()+" as m "
					+" join fetch m.destino r " +
					"  where m.dtRecepcao is null " +
					"  and m.tipoMalote > 0  " +
					"  and m.fechado = false  " +
					"  and m.destino = :pDestino" +
					" order by m.dtEnvio, m.destino.descricao");
					q.setParameter("pDestino", oDestino);
		} else {
			// malotes conferidos pela sepex
			q = em.createQuery("from " +
			    MaloteView.class.getName()+" as m " + 
				" join fetch m.destino r " + 
				"  where m.dtRecepcao is null " +
				"  and m.tipoMalote > 0 " +
				"  and m.fechado = false " +
				" order by m.dtEnvio, m.destino.descricao");			
		}
		return (List<MaloteView>) q.getResultList();
	}

	public List<MaloteView> buscaMalotesReceber(Localidade destino) {
		/* pode ter um parametro aqui para receber malotes 
		'enviado p sepex -> tipo 0 ou 'da sepex p dest final -> tipo=1' */
		Query q;
		if (destino != null) {
			logger.info("conferencia pelo destinatario.");
			if (destino.isAgrupado()) 
				destino = destino.getMaloteAgrupado();

			q = em.createQuery(sbBuscaMaloteRecUsr.toString());
			q.setParameter("pDestino", destino);			
		} else {
			logger.info("conferencia pela sepex");
			q = em.createQuery(sbBuscaMaloteRecSepex.toString());
		}
		List<MaloteView> listaRetorno = q.getResultList(); 
		return  listaRetorno;		
	}
	
	
	private Calendar dataHoje() {
		Calendar dataHoje = Calendar.getInstance();
		dataHoje.set(Calendar.HOUR_OF_DAY, 0);
		dataHoje.set(Calendar.MINUTE,0);
		dataHoje.set(Calendar.SECOND,0);
		dataHoje.set(Calendar.MILLISECOND, 0);
		return dataHoje;
	}
	
	public Integer proximoNumeroMalote(Long localidade) {
		logger.info("<<<proximo num malote>>>");
		StringBuilder sbQuery = new StringBuilder("select max(numMalote) from Malote" );
		sbQuery.append(" where dtEnvio > :pDataHoje ");
		sbQuery.append(" and cod_remetente = :pLocalidade");
		Query q = em.createQuery(sbQuery.toString());
		q.setParameter("pDataHoje", dataHoje().getTime());
		q.setParameter("pLocalidade", localidade);
		logger.info("localidade:"+localidade);
		Integer result = 0;
		try {
			result = (Integer) q.getSingleResult();
			++result;
		}
		catch (Exception ex) {
			result = new Integer(1);
		}
		return result;
	}
	
	public void receberMalote(Malote maloteRec, String loginRec) {
		int afetados = 0;
		if (maloteRec.getTipoMalote().equals(TipoMalote.USUARIO)) {	
			logger.info("<<< Receber/destrinchar malote destinado a Sepex >>> "+maloteRec.getCodigo());

			// busca documentos do malote.				
			List<MaloteVirtual> listaDoc = new ArrayList<MaloteVirtual>(maloteRec.getDocsMalote());
			logger.info("tam lista docs: "+listaDoc.size());
			
			for (MaloteVirtual maloteVirtual : listaDoc) {
				logger.info("<<< doc lista num >>"+maloteVirtual.getCodigo());
				// malotes de distribuicao para docs Externo e Interno
				if(maloteVirtual.getDocumento().getTipoRemessa()!=TipoRemessa.CARTA) {
					// pode gerar um novo malote ou recuperar um aberto e nao fechado para o local
					Malote maloteDestino = buscaMaloteDestino(maloteVirtual.getDocumento().getDestinatario());
					
					MaloteVirtual oVirtual = new MaloteVirtual(maloteDestino, maloteVirtual.getDocumento(), loginRec);
	    			em.persist(oVirtual);
	    			//docDAO.salva(oVirtual.getDocumento());
				}
			}
			StringBuilder sbQuery = new StringBuilder("update Malote m set " );
			sbQuery.append(" m.dtRecepcao = :dataRec, ");
			sbQuery.append(" m.fechado = true, ");
			sbQuery.append(" m.loginRecepcao = :loginRec ");
			sbQuery.append(" where m.codigo = :codigo");
			Query q = em.createQuery(sbQuery.toString());
			q.setParameter("codigo", maloteRec.getCodigo());
			q.setParameter("dataRec", Calendar.getInstance().getTime());
			q.setParameter("loginRec", loginRec);
			afetados = q.executeUpdate();

			logger.info("fim receber malote!");
		} 
		else {
			logger.info("<<< malote Conferencia pelo usuario nao destrincha>>> "+maloteRec.getCodigo());
			StringBuilder sbQuery = new StringBuilder("update Malote m set " );
			Query q;
			if(maloteRec.isFechado()) {
				// quem esta recebendo eh o setor de destino
				sbQuery.append(" m.dtRecepcao = :dataRec, ");
				sbQuery.append(" m.loginRecepcao = :loginRec ");
				sbQuery.append(" where m.codigo = :codigo");
				q = em.createQuery(sbQuery.toString());
				q.setParameter("dataRec", Calendar.getInstance().getTime());
				q.setParameter("loginRec", loginRec);
				q.setParameter("codigo", maloteRec.getCodigo());
			} else {
				// a sepex esta fechando o malote.
				sbQuery.append(" m.fechado = true ");
				sbQuery.append(" where m.codigo = :codigo");
				q = em.createQuery(sbQuery.toString());
				q.setParameter("codigo", maloteRec.getCodigo());
			}
			afetados = q.executeUpdate();			
		}
		
		logger.info("Qtd itens afetados: "+afetados);
	}
	
	public void salvarMaloteVirtual(MaloteVirtual mv) {
		// rever ... 06.05.2014 - 
		em.persist(mv);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Malote> buscaGuias(){
		Query q = em.createQuery("from "
				+Malote.class.getName()+" as g"
				+expressaoOrderBy);
		return q.getResultList();
	}


	
}
