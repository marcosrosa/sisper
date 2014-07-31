package br.jus.jfes.sisper.web;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This is the action from which all other actions derive from.  It contains
 * useful information regarding the session and username attatched to the
 * session.
 *
 */
public abstract class BaseAction {

	public FacesContext getFacesContext()
	{
		return FacesContext.getCurrentInstance();
	}
	
	public HttpSession getSession()
	{
		return (HttpSession)getFacesContext().getExternalContext().getSession(false);
	}
	
	public ServletRequest getRequest()
	{
		return (ServletRequest)getFacesContext().getExternalContext().getRequest();
	}
	
	public PortletRequest getPortletRequest()
	{
		return (PortletRequest)getFacesContext().getExternalContext().getRequest();
	}

	public String getCurrentUserName()
	{
		return (String)getSession().getAttribute("userName");
	}
	
	public void setCurrentUserName(String userName)
	{
		getSession().setAttribute("userName", userName);
	}
	
	public Long getLongAttribute(ActionEvent ae, String name)  {
		Long ident = (Long) ae.getComponent().getAttributes().get(name);
		return ident;
	}
	
	public String getPortletPreference(String nome, String valorPadrao) {
		return getPortletRequest().getPreferences().getValue(nome, valorPadrao);		
	}
}
