package br.jus.jfes.sisper.util;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.jus.jfes.sisper.modelo.Localidade;
import br.jus.jfes.sisper.session.LocalidadeManager;


@ManagedBean(name="conversorLocalidade")
@RequestScoped
public class LocalidadeConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 348722074961258235L;
	
	@EJB
	private LocalidadeManager localidadeManager;

	public LocalidadeConverter() {
		// TODO Auto-generated constructor stub
	}

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		Long cod = new Long(arg2);
		Localidade local = localidadeManager.load(cod);
		if (local!=null)
			System.out.println("Carreguei Localidade:"+local.getDescricao());
		return local;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		Localidade local = (Localidade) arg2;
		if (local == null) 
			local = new Localidade(-1L);
		return local.getCodigo().toString();
	}

}
