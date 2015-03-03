package br.jus.jfes.sisper.util;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.jus.jfes.sisper.modelo.TipoDocumento;
import br.jus.jfes.sisper.session.TipoDocumentoManager;

@ManagedBean
@RequestScoped
public class TipoDocConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private TipoDocumentoManager tipoDocManager;

	
	public TipoDocConverter() {
		// TODO Auto-generated constructor stub
	}

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		Long cod = new Long(arg2);
		TipoDocumento tipodoc = tipoDocManager.load(cod);
		if (tipodoc!=null)
			System.out.println("Carreguei tipoDocumento:"+tipodoc.getDescricao());
		return tipodoc;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		TipoDocumento tipodoc = null;
		if (arg2 != null)
			tipodoc = (TipoDocumento) arg2;
		else 
			tipodoc = new TipoDocumento(-1L);
		
		return tipodoc.getCodigo().toString();
	}

}
