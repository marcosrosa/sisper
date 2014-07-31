package br.jus.jfes.sisper.web;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.portlet.PortletRequest;

import org.jboss.logging.Logger;

import br.jus.jfes.sisper.modelo.*;
import br.jus.jfes.sisper.session.*;


@ManagedBean(name = "maloteBean")
@SessionScoped
public class MaloteBean extends BaseAction {
	private static Logger logger = Logger.getLogger(MaloteBean.class);
	private boolean adm=false;
	private boolean checkUsuario=true;

	private Long lotacaoCod  = new Long(0);
	private Long tipoRemessa = new Long(1);
	
	private String administradores;
	
	private List<MaloteView> listaMalotesAbertos;
	private List<MaloteView> listaMaloteReceber;
	private List<MaloteVirtual> listaDocumento;

	private Documento documento;
	private Carta carta;
	private Malote malote;
	
	@EJB
	private MaloteManager maloteDAO;
	
	@EJB
	private DocumentoManager documentoDAO;
	
	@EJB
	private CartaManager cartaDAO;
	
	@EJB
	private LocalidadeManager localidadeManager;
	
//	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private String docSelecionado;
	
	private SelectItem[] tiposDocumento;
	private SelectItem[] listaDestinatario;
	private int qtdDocumentos;
	
	private Localidade setorAtual;
	
	private Localidade unidSelecionada;
	
	public Localidade getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(Localidade setorAtual) {
		this.setorAtual=setorAtual;;
	}
	
	// somente para administradores.
	public void selectSetorAtual(ValueChangeEvent vce) {
		logger.info("setor Atual modificado!!!");
		setorAtual = (Localidade) vce.getNewValue();
		this.setTipoRemessa( new Long(9) );
		//listaDestinatario = null;
		this.lotacaoCod = setorAtual.getCodigo();
	}
	
	public MaloteBean() {
		// deixar vazio
	}
	
	@PostConstruct
	public void inicializacao() {
		logger.info("pos Construct do Malote");		
		//atualizaListaMalote();
	}
			
	public List<MaloteVirtual> getListaDocumento() {
		return listaDocumento;
	}
	
	public List<MaloteView> getListaMalotesAbertos() {
		listaMalotesAbertos = maloteDAO.buscaMalotesAbertos(getLotacaoCod());
		return listaMalotesAbertos;
	}

	public List<MaloteView> getListaMaloteReceber() {
		if (isAdm()) 
			listaMaloteReceber = maloteDAO.buscaMalotesReceber(null);
		else
			listaMaloteReceber = getListaMaloteReceberUsr();
		return listaMaloteReceber;
	}

	public List<MaloteView> getListaMaloteReceberUsr() {
		listaMaloteReceber = maloteDAO.buscaMalotesReceber(localidadeUsuarioLogado());
		return listaMaloteReceber;		
	}
	
	public List<MaloteView> getListaMaloteFecharSepex() {
		listaMaloteReceber = maloteDAO.buscaMalotesFecharSepex(null);
		return listaMaloteReceber;		
	}
	
	public List<MaloteView> getListaMalotesAbertosDestinatario() {
		logger.info("<<< malotesAbertosDestinatario >>>");
		if (malote.getDestino() == null) 
			logger.info("malote.getDestino ->> nulo");
		listaMaloteReceber = maloteDAO.buscaMalotesFecharSepex(malote.getDestino());
		return listaMaloteReceber;		
	}

	
	public void setListaDocs(List<MaloteVirtual> listaDocumento) {
		this.listaDocumento = listaDocumento;
	}
	
	private void atualizaListaDoc() {
		logger.info("atualizaListadoc()");
		//listo os documentos deste malote.
		if (null != malote) {
			listaDocumento = documentoDAO.buscaDocumentos(malote);
			if (null != listaDocumento)
				logger.info("atualizaListaDoc-> "+listaDocumento.size());
		}
	}
	
		
	public SelectItem[] getTiposDocumento() {
		if (tiposDocumento == null) {
			TipoDocumentoManager tipoDocDAO = new TipoDocumentoManager();
			List<TipoDocumento> tipoDocs = tipoDocDAO.buscaTipos();
			tiposDocumento = new SelectItem[tipoDocs.size()];
			int x=0;
			for(TipoDocumento tipoDoc : tipoDocs) {
				tiposDocumento[x++] = new SelectItem(tipoDoc,tipoDoc.getDescricao());
			}
		}
		return tiposDocumento;
	}

	
	public SelectItem[] getListaDestinatario() {
		logger.info("xxx listaDestinatario->tipoRemessa: " + tipoRemessa);
		List<Localidade> destinatarios = null;
		if (null != malote) {
			if (tipoRemessa == 9 || malote.getTipoMalote() == TipoMalote.SEPEX)
				destinatarios = localidadeManager.buscaLocalidades();
			else 
				destinatarios = localidadeManager.buscaLocalidadesPorTipo(tipoRemessa);
		} else {
			destinatarios = localidadeManager.buscaLocalidades();
		}
		listaDestinatario = new SelectItem[destinatarios.size()];
		int x=0;
		for(Localidade dest : destinatarios) {
			listaDestinatario[x++] = new SelectItem(dest,dest.getDescricao());
		}
		return listaDestinatario;
	}
	
	public boolean isMaloteSepex() {		
		if (null != malote && malote.getTipoMalote().equals(TipoMalote.SEPEX)) 
			return true;
		return false;
	}
	
	public boolean isSeadm() {
		if (null != malote && malote.getTipoMalote().equals(TipoMalote.SEADM_INT)) 
			return true;
		return false;
	}
	
	public String getLabelDesc() {
		String retorno = "Remetente:";
		if (isMaloteSepex()) {
			retorno = "Destino:";
		}
		return retorno;
	}
	
	public String getLabelValue() {
		String retorno = null;
		if (isMaloteSepex()) 
			retorno = malote.getDestino().getDescricao();
		else 
			retorno = malote.getRemetente().getDescricao();
		return retorno;
	}
	
	public String getLabelTitColuna() {		
		String retorno = "Destino";
		if (isMaloteSepex()) {
			retorno = "Remetente";
		}
		return retorno;
	}
			
		
	public void novoDocumento(ActionEvent ae) {
		UIParameter val = (UIParameter) ae.getComponent().findComponent("maloteId");
		Long id = Long.valueOf(val.getValue().toString());
		if (id==null) id = new Long(-100);
		carregarMalote(id);
		preparaNovoItem();
		atualizaListaDoc(); 
	}
	
	public void conferirMalote(ActionEvent ae) {
		UIParameter val;
		//boolean origemSepex = false;
		val = (UIParameter) ae.getComponent().findComponent("maloteId");
		// pode voltar ao que era
		if (val == null) {
			//origemSepex = true;
			val = (UIParameter) ae.getComponent().findComponent("maloteSepexId");				
		}
		
		Long id = Long.valueOf(val.getValue().toString());
		if (id==null) id = new Long(-100);
		carregarMalote(id);
		atualizaListaDoc();
	}
	
	public void inserirDocsMalote(ActionEvent ae) {
		UIParameter val;
		val = (UIParameter) ae.getComponent().findComponent("maloteId");
		// pode voltar ao que era
		if (val == null) {
			//origemSepex = true;
			val = (UIParameter) ae.getComponent().findComponent("maloteSepexId");				
		}
		
		Long id = Long.valueOf(val.getValue().toString());
		if (id==null) id = new Long(-100);
		carregarMalote(id);
		atualizaListaDoc();
		preparaNovoItem();
	}
	
	public String salvar() {
		// quando esta inserindo (novo doc) eh null
		if (malote!=null && malote.getCodigo() == null)
			maloteDAO.salva(malote);

		logger.info("passou na salva documento");
		// salva o documento
		String pLoginUsuario = getFuncionarioCorrente().getLogin();
		if (tipoRemessa < 3) {
			
			// deve melhorar. 
			switch(tipoRemessa.intValue()) {
				case 1 : documento.setTipoRemessa(TipoRemessa.EXTERNO);
					    break;
				case 2 : documento.setTipoRemessa(TipoRemessa.INTERNO);
					    break;
			}
			
			// ini teste
			switch (malote.getTipoMalote()) {
				case USUARIO :
					documento.setRemetente(malote.getRemetente());
					documento.setDestinatario(unidSelecionada);
						break;
				case SEPEX :
					documento.setRemetente(unidSelecionada);
					documento.setDestinatario(malote.getDestino());
						break;
				// para seadm_int nao faz nada pois o form pede dest e remt
			}
			// fim teste
			documentoDAO.salva(documento);
			logger.info("unidade Selecionada: "+unidSelecionada.getDescricao());
			MaloteVirtual maloteVirtual = new MaloteVirtual(malote, documento, pLoginUsuario);
			
			//nao pode ser isso... 06.05.2014
			maloteDAO.salvarMaloteVirtual(maloteVirtual);
			
			malote.getDocsMalote().add(maloteVirtual);
			
		} else {
			carta.setTipoRemessa(TipoRemessa.CARTA);
			carta.setRemetente(malote.getRemetente());
			carta.setDestinatario(null);
			cartaDAO.salva(carta);
			MaloteVirtual maloteVirtual = new MaloteVirtual(malote, carta, pLoginUsuario);
			maloteDAO.salvarMaloteVirtual(maloteVirtual);
		}
		//Prepara Tela para novo documento
		preparaNovoItem();
		atualizaListaDoc(); 
		// mesagem na tela.
		FacesContext.getCurrentInstance().addMessage("messagem", new FacesMessage("Registro Salvo Com Sucesso!"));
		return "novoDocumento";
	}
	
	
	private void carregarMalote(Long codMalote) {
		if (codMalote.compareTo(0L) > 0)  {
			// carregar o malote com o item clicado - 
			logger.info("deve carregar o malote Nr.: "+codMalote);
     	    malote = maloteDAO.load(codMalote);
		} 		
	}
	
	private Localidade localidadeUsuarioLogado() {
		return localidadeManager.load(getLotacaoCod());		
	}
	
	private Malote novoMalote(boolean criadoPelaSepex) {
		Malote maloteNovo = new Malote();
		
		/* campo Destinatario registra o destino do malote 
		 * varas e setores sede -> sepex
		 * varas e setores inte -> SEADM-do interior
		 * seadm-int -> sepex;
		 * a menos que seja correio - cada interior envia seu proprio correio.
		 * obs: caso o remetente seja a sepex. Tera que informar o remetente. */
		
		maloteNovo.setDtEnvio(Calendar.getInstance().getTime());
		if (criadoPelaSepex) {			
			// nao faz nada com destino
			tipoRemessa = 0L;
			maloteNovo.setTipoMalote(TipoMalote.SEPEX);

			maloteNovo.setRemetente(localidadeManager.remetenteSepex());
		} else {
			tipoRemessa = 1L;
			maloteNovo.setRemetente(localidadeUsuarioLogado());
			if (maloteNovo.getRemetente().temCodUnidadeAdmCentral())
			   maloteNovo.setDestino(maloteNovo.getRemetente().getUnidadeAdmCentral());
			maloteNovo.setTipoMalote(TipoMalote.USUARIO);
		}
		maloteNovo.setNumMalote(maloteDAO.proximoNumeroMalote(maloteNovo.getRemetente().getCodigo()));
		
		return maloteNovo;
	}
	
	public String criaMalote() {
				
		//cria o novo malote (deveria fechar o anterior)
		this.malote = novoMalote(false);
		
		// verifica numero de malote no banco
		
		logger.info("malote num: "+ malote.getNumMalote());
		
		preparaNovoItem();
		atualizaListaDoc();
		
		return "novoDocumento";
	}
	
	public String novoMaloteSepex() {
		this.malote = novoMalote(true);
		return "novoMaloteSepex";
	}
	
	public String insereDocSepex() {

		switch(malote.getDestino().getTipoRemessa()) {
			case NI : tipoRemessa = 0L;
				break;
			case EXTERNO : tipoRemessa = 1L;
				break;
			case INTERNO : tipoRemessa = 2L;
				break;
			case CARTA : tipoRemessa = 3L;
				break;
			case VARAS_INT: case SEADM_INT: case SEPEX : tipoRemessa = 2L;
				break;
		}
		
		preparaNovoItem();
		atualizaListaDoc();
		return "insereDocSepex";
	}
	
	public String receberMalote() {
		maloteDAO.receberMalote(malote,getFuncionarioCorrente().getLogin());
		if(malote.getTipoMalote() == TipoMalote.SEPEX && 
				malote.getDtRecepcao() != null)
			return "principal";
		else 
			return "receberMalote";
	}
	
	public String cancelarReceber() {
		String navegacao = null;
		switch (malote.getTipoMalote()) {
			case USUARIO : navegacao = "receberMalote"; 
				break;
			case SEPEX :
			case SEADM_INT : 
					if (malote.isFechado())
						navegacao = "principal";
					else
						navegacao = "receberMalote";
				break;
		}
		return navegacao;
	}
	
	public String telaPrincipal() {
		atualizaListaDoc(); //??
		return "principal";
	}
	

	public String getAdministradores() {
		return administradores;
	}

	public void setAdministradores(String administradores) {
		this.administradores = administradores;
	}

	public boolean isAdm() {
		if (checkUsuario) {
			// usuario Adm ?
			logger.info("<<<grupo usuario>> "+administradores);
			FacesContext fc = FacesContext.getCurrentInstance();
			PortletRequest pr = (PortletRequest) fc.getExternalContext().getRequest();			
			//if (!pr.getUserPrincipal().toString().startsWith("jesmrfd")) 
			setAdm(pr.isUserInRole(administradores));	
			checkUsuario=false;
		}
		return adm;
	}

	public void setAdm(boolean adm) {
		logger.info("setAdmim: "+adm);
		this.adm = adm;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}
	
	
	public String getDocSelecionado() {
		return docSelecionado;
	}

	public void setDocSelecionado(String docSelecionado) {
		this.docSelecionado = docSelecionado;
	}

	public void setTiposDoc(SelectItem[] tiposDoc) {
		this.tiposDocumento = tiposDoc;
	}	
		
	public Long getLotacaoCod() {
		//so para teste
		if (null == lotacaoCod || lotacaoCod.equals(0L)) 
			lotacaoCod = new Long(204); // sepex
		
		// verifica forma correta de buscar funcionario qdo null
		if (lotacaoCod == 0) {
			FuncionarioBackBean fbb = getFuncionarioCorrente();
			if (null!=fbb &&  null != fbb.getLotacao()) {
				lotacaoCod = fbb.getLotacao().getCodigo();
			}
		}
		return lotacaoCod;
	}
	
	private FuncionarioBackBean getFuncionarioCorrente() {
		return (FuncionarioBackBean) FacesContext.getCurrentInstance()
			.getExternalContext()
			.getSessionMap()
			.get("funcionarioBean");
	}

	public void setLotacaoCod(Long lotacaoCod) {
		this.lotacaoCod = lotacaoCod;
	}

	public Malote getMalote() {
		return malote;
	}
	
	public void setMalote(Malote malote) {
		this.malote = malote;
	}	

	public Long getTipoRemessa() {
		return tipoRemessa;
	}

	public void setTipoRemessa(Long tipoRemessa) {
		this.tipoRemessa = tipoRemessa;
	}

	public int getQtdDocumentos() {		
		if (malote != null && malote.getCodigo() != null) 
			qtdDocumentos = maloteDAO.getQtdDocumentos(malote);
		return qtdDocumentos;
	}

	public void setQtdDocumentos(int qtdDocumentos) {
		this.qtdDocumentos = qtdDocumentos;
	}

	public void selectRemessa(ValueChangeEvent vce) {
		logger.info("seletor Remessa:::");
		// atualizar o valor de tipoRemessa atraves do objeto selecionado no vce
		this.setTipoRemessa( (Long) vce.getNewValue() );
		listaDestinatario = null;
		// prapara novo carta ou documento		
		preparaNovoItem();
		logger.info("tipoRemessa: "+tipoRemessa);
	}
	
	private void preparaNovoItem() {
		
		Localidade destinatarioAnt = null;
		TipoDocumento tipoDocumAnt = null;

		if (documento != null) { //&& malote.getTipoMalote().equals(TipoMalote.USUARIO)) 
			destinatarioAnt = unidSelecionada;
			tipoDocumAnt = documento.getTipoDocumento();
		} else if (carta!=null) {
			destinatarioAnt = unidSelecionada;
			tipoDocumAnt = carta.getTipoDocumento();
		}
		
		if (tipoRemessa < 3 ) { // edita Documento
			carta = null;
			documento = new Documento();
			if (destinatarioAnt!=null)
				unidSelecionada = destinatarioAnt;
			documento.setTipoDocumento(tipoDocumAnt);
			switch (tipoRemessa.intValue()) {
				case 1 : documento.setTipoRemessa(TipoRemessa.EXTERNO);
					break;
				case 2 : documento.setTipoRemessa(TipoRemessa.INTERNO);
					break;
			}
			
		} else {	// edita carta
			documento = null;
			carta = new Carta(); 
			carta.setTipoDocumento(tipoDocumAnt);
			// destinarario -> correios
			unidSelecionada = (Localidade) localidadeManager.load(10000L);
			carta.setDestinatario(unidSelecionada);
			carta.setTipoRemessa(TipoRemessa.CARTA);			
		}
		
	}
	
	public void consultarCep(ValueChangeEvent vce) {
		
		if (vce.getPhaseId().equals(PhaseId.ANY_PHASE)) {
			vce.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
			vce.queue();
		} else {
			String cepConsulta = (String) vce.getNewValue();
			logger.info("consultar cep::: "+cepConsulta);
			if (!cepConsulta.isEmpty()) {
				CepManager cepDAO = new CepManager();
				
				TabCep cep = cepDAO.load(cepConsulta);
				
				if (cep != null) {
					carta.setLogradouro(cep.getLogradouro());
					carta.setBairro(cep.getBairro());
					carta.setCidade(cep.getLocalidade());
					carta.setUf(cep.getUf());
				}
				
				carta.setNumCorreio(carta.getNumCorreio());
				
				carta.setCep(cepConsulta);
			}	
			/* para atualizar a pagina  com os dados novos.
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(
				    context, null, "novoDocumento");*/
		}
		logger.info("Fim Consulta CEP");
	}
	
	public void seletorDestino(ValueChangeEvent vce) {
		// vase eh nulo.
		logger.info("selecionando destino");
		if (vce.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
			Localidade destino = (Localidade) vce.getNewValue();
			logger.info("Destino Selecionado >>>"+destino.getDescricao());
		} else {
			vce.setPhaseId(PhaseId.INVOKE_APPLICATION);
			vce.queue();
		}
			
	    //destino = vcl.
		//malote.setDestino(destino)
		//malote.setDestino(destino)
	}

	public void navegar(ActionEvent ae) {
		// TODO navegacao nao esta funcionando;22.01.2013
		if (ae.getPhaseId().equals(PhaseId.ANY_PHASE)) {
			ae.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
			ae.queue();
		} else {			  
			
			if (tipoRemessa == 3)
				documento = (Documento) carta;
									
			if(ae.getComponent().getId().equals("btnAvancar")) {
				logger.info("avancar avancar avancar...");
				//Long proximo = documentoDAO.buscaProximo(documento.getMalote().getCodigo(), documento.getCodigo());
				Long proximo = new Long(9); // retirar
				if (proximo != null) {
					Documento odoc = documentoDAO.load(proximo);
					if (odoc.getTipoRemessa().equals(TipoRemessa.CARTA)) {
						this.carta = (Carta) cartaDAO.load(proximo);
						this.setTipoRemessa(3L);						
					} else {
						this.documento = odoc;
						this.tipoRemessa = new Long (odoc.getTipoRemessa().ordinal());
					}
				}
				else
					FacesContext.getCurrentInstance().addMessage("messagem", new FacesMessage("Último Registro!"));			
			}
			else 
			{
				logger.info("voltar voltar voltar...");
				//Long anterior = documentoDAO.buscaAnterior(documento.getMalote().getCodigo(), documento.getCodigo());
				Long anterior = new Long(9); // retirar.
				if (anterior != null) {
					Documento odoc = documentoDAO.load(anterior);
					if (odoc.getTipoRemessa().equals(TipoRemessa.CARTA)) {
						this.carta = (Carta) cartaDAO.load(anterior);
						this.setTipoRemessa(3L);
					} else {
						this.documento = odoc;
						this.setTipoRemessa(new Long(odoc.getTipoRemessa().ordinal()));
					}
				}
				else
					FacesContext.getCurrentInstance().addMessage("messagem", new FacesMessage("Primeiro Registro!"));
			}
		}
	}

	public Localidade getUnidSelecionada() {
		return unidSelecionada;
	}

	public void setUnidSelecionada(Localidade unidSelecionada) {
		this.unidSelecionada = unidSelecionada;
	}
	
	
}
