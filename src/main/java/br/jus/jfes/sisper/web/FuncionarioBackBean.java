package br.jus.jfes.sisper.web;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import org.jboss.logging.Logger;

//import br.gov.trf2.jfes.intranet.servidor.faces.ServidorBackBean;
import br.jus.jfes.sisper.modelo.Localidade;
//import br.jus.jfes.sisper.modelo.Orgao;
//import br.jus.jfes.sisper.modelo.TipoRemessa;
import br.jus.jfes.sisper.session.LocalidadeManager;



@ManagedBean(name = "funcionarioBean")
@SessionScoped
public class FuncionarioBackBean extends BaseAction {
	private static Logger logger = Logger.getLogger(FuncionarioBackBean.class);	
	
	private String usuarioLogin;
	private String login;
	private String nome;
	private String matricula;
	private String secao;
	private Localidade lotacao;
	private boolean usuarioLogado;
		
	@EJB
	private LocalidadeManager localidadeManager;
	
	public FuncionarioBackBean() {
		// manter vazio
	}
	
	@PostConstruct
	public void inicializador() {
		usuarioLogin = "nulo";
	}
	
	public String apresFuncionario() {
		//recupera dados a partir do contexto de secao do usuario
		PortletRequest pq = this.getPortletRequest();

		//  verifica se usuario Logado.
		if(pq.getRemoteUser()!=null) {
			// e o mesmo usuario ?
			if(!pq.getRemoteUser().equals(usuarioLogin)) {
				///Session session = openRHSession();
				login = pq.getRemoteUser();			
				logger.info("verificando usuario logado.:"+login);
				//ServidorBackBean funcionario = new ServidorBackBean();
				//funcionario.setConta(login);
				//funcionario.pesquisarPorConta();				
				nome = "Marcos";  //funcionario.getNome();
				logger.info("nome:" + nome);
				matricula = "10724";//funcionario.getMatricula();
				logger.info("matricula:" + matricula);
				secao = "SEDIN";//funcionario.getSecao();
				logger.info("secao:" + secao);
				// localiza na tabela de lotacao
				
				Long codLotacao = new Long(1015);//new Long(funcionario.getLotacaoCod());
				lotacao = localidadeManager.load(codLotacao);
				
			//	somente para teste
			//  if (lotacao.getCodigo().equals(1015l)) 
            //      lotacao.setCodigo(731l); 
				usuarioLogin = pq.getRemoteUser();
				usuarioLogado=true;
			}
		} else {
			usuarioLogado = false;
		}
		return null;
	}
	
	//ActionEvent actionEvent
	public String generateReport() {
		logger.info("entrou na generateReport");				
		return null;
	}


	public String getSecao() {
		return secao;
	}

	public void setSecao(String ses) {
		this.secao = ses;
	}

	public String getMatricula() {		
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isUsuarioLogado() {
		apresFuncionario();
		return usuarioLogado;
	}

	public void setUsuarioLogado(boolean usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Localidade getLotacao() {
		return lotacao;
	}

	public void setLotacao(Localidade lotacao) {
		this.lotacao = lotacao;
	}
	
}
