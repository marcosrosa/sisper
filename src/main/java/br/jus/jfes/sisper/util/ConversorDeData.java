package br.jus.jfes.sisper.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConversorDeData implements Converter{

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date data;
		try {
			data = df.parse(arg2);
		} catch (ParseException e) {
			System.out.println("ConversorDeData:Erro na conversão - Inicio");
			e.printStackTrace();
			System.out.println("ConversorDeData:Erro na conversão - Fim");
			data = null;
		}
		return (Object) data;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if (!(arg2==null)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date data = (Date) arg2;		
			return sdf.format(data);
		}
		return null;
	}

	
}
