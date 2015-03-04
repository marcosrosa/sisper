package br.jus.jfes.sisper.web;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.portlet.PortletRequest;

import org.jboss.logging.Logger;

import br.gov.trf2.jfes.rh.api.FuncionarioVO;
import br.gov.trf2.jfes.rh.api.session.IFuncionarioDAO;
import br.jus.jfes.sisper.modelo.Localidade;
import br.jus.jfes.sisper.session.LocalidadeManager;



@ManagedBean(name = "funcionarioBean")
@SessionScoped
public class FuncionarioBackBean extends BaseAction {
	private static Logger logger = Logger.getLogger(FuncionarioBackBean.class);	
	private static final String emptyStr = "";
	
//	private String login;
	private Localidade lotacao;
	private boolean usuarioLogado;
	
	// loockup em outro app no mesmo servidor.
	@EJB(lookup="ejb:rh-modelo-ear/rh-modelo-ejb/FuncionarioDAO!br.gov.trf2.jfes.rh.api.session.IFuncionarioDAO")
	private IFuncionarioDAO funcionarioManager;
	
	private FuncionarioVO funcionario;
		
	@EJB
	private LocalidadeManager localidadeManager;
	
	public FuncionarioBackBean() {
		// manter vazio
	}
	
	@PostConstruct
	public void inicializador() {
		usuarioLogado = false;
		consultaFuncionario();
	}
	
	private void consultaFuncionario() {
		//recupera dados a partir do contexto de secao do usuario
		PortletRequest pr = this.getPortletRequest();

		//  verifica se usuario Logado.
		if(pr.getRemoteUser()!=null && pr.getRemoteUser()!=emptyStr) {
			if(!usuarioLogado) {
				///Session session = openRHSession();
							
				logger.info("verificando usuario logado.:"+pr.getRemoteUser());
				funcionario = funcionarioManager.getFuncVOPorLogin(pr.getRemoteUser());
				//nome = "Marcos";  //funcionario.getNome();
				logger.info("nome:" + funcionario.getNome());
				
				logger.info("matricula:" + funcionario.getMatricula());
				logger.info("secao:" + funcionario.getSiglaLotacao());
				
				//Long codLotacao = new Long(funcionario.get);
				//lotacao = localidadeManager.load(codLotacao);
				
				usuarioLogado=true;
			}
		} else {
			usuarioLogado = false;
		}
	}
	
	//ActionEvent actionEvent
	public String generateReport() {
		logger.info("entrou na generateReport");				
		return null;
	}


	public String getSecao() {
		if (funcionario!=null)
			//return funcionario.getLotacaoAtual().getDescricao();
			return funcionario.getSiglaLotacao();
		else 
			return emptyStr;
	}

	public String getMatricula() {		
		if (funcionario!=null)
			return emptyStr + funcionario.getMatricula();
		else 
			return emptyStr;
	}

	public String getNome() {
		if (funcionario!=null)
			return funcionario.getNome();
		else 
			return emptyStr;
	}

	public boolean isUsuarioLogado() {
		consultaFuncionario();
		return usuarioLogado;
	}

	public String getLogin() {
		if (funcionario!=null)
			return funcionario.getLoginIntranet();
		else 
			return emptyStr;
	}

	public Localidade getLotacao() {
		return lotacao;
	}

	public void setLotacao(Localidade lotacao) {
		this.lotacao = lotacao;
	}
	
}
