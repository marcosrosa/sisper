package br.jus.jfes.sisper.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.portlet.PortletRequest;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean extends BaseAction {
	
	public UsuarioBean() {
		//so pra atender api		
	}
	
	//administradores fica no preferences
	// nao tem um grupo de administradores do sisper somente de usuarios.
	public static final String PREF_GRUPO = "GrupoSisper";
	public static final String GRUPO_PADRAO = "usuarios_sisper";
	
	String nomeGrupoAdministrador;
	
	@PostConstruct
	public void inicializar() {
		//busca diretamente do baseAction.
		nomeGrupoAdministrador = getPortletPreference(PREF_GRUPO, GRUPO_PADRAO);
	}
		
	public boolean isAdministrador(){
		PortletRequest req = getPortletRequest();
		return  req.isUserInRole(nomeGrupoAdministrador);
	}
	
}
