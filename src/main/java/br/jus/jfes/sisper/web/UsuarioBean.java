package br.jus.jfes.sisper.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//import javax.portlet.PortletPreferences;
//import javax.portlet.PortletRequest;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
	
	//administradores fica no preferences
	public static final String PREF_GRUPO = "nomeGrupoAdministradores";
	public static final String GRUPO_PADRAO = "AdministradoresInspecao";
	
	String nomeGrupoAdministrador;
		
	public UsuarioBean(){
		/*FacesContext ctx = FacesContext.getCurrentInstance();
		PortletRequest req = (PortletRequest) ctx.getExternalContext().getRequest();
		PortletPreferences prefs = req.getPreferences();
		if(prefs != null){
			nomeGrupoAdministrador = prefs.getValue(PREF_GRUPO, GRUPO_PADRAO);
		}*/ 
	}
	
	public boolean isAdministrador(){
		//FacesContext ctx = FacesContext.getCurrentInstance();
		//PortletRequest req = (PortletRequest) ctx.getExternalContext().getRequest();
		//return  req.isUserInRole(nomeGrupoAdministrador);  
		return true;
	}
	
}
