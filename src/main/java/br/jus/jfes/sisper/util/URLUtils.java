/*
 * Arquivo criado em Jul 12, 2004 por patrick
 */
package br.jus.jfes.sisper.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 
 *
 */
public class URLUtils {
	static SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
	//static int anoInspecao;
	
	static public Date extraiData(String str) throws Exception{
		return formatadorData.parse(str);
	}

	static public Date extraiDataHora(String str) throws Exception{
		SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return formatadorData.parse(str);
	}
	
	static public void copiarConteudoURLs(String origem, String destino) throws Exception{
		/*
		 * outra forma de leitura da origem. (+ limpo)
		URL oracle = new URL("http://www.oracle.com/");
		BufferedReader in = new BufferedReader(
		new InputStreamReader(oracle.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        	System.out.println(inputLine);
        in.close();		
		*/
		
		URL paginaURL = new URL(origem);
		URLConnection paginaCon = paginaURL.openConnection();
		InputStream paginaIS = paginaCon.getInputStream();
		
		URL url = new URL(destino);
		
		URLConnection urlcon = url.openConnection();
		OutputStream os = urlcon.getOutputStream();
		
		byte b[] = new byte[1024];
		int cont = 0;
		
		cont = paginaIS.read(b);
		while(cont!=-1){
			os.write(b,0,cont);
			cont = paginaIS.read(b);
		}
		
		os.close();
	}

	public static String preparaHtml(String s) {
		if (s==null) return "";
		String a =s.replaceAll("á","&aacute;").replaceAll("à","&agrave;").replaceAll("â","&acirc;").replaceAll("ã","&atilde;");
		String A =a.replaceAll("Á","&Aacute;").replaceAll("À","&Agrave;").replaceAll("Â","&Acirc;").replaceAll("Ã","&Atilde;");
		String e =A.replaceAll("é","&eacute;").replaceAll("è","&egrave;").replaceAll("ê","&ecirc;");
		String E =e.replaceAll("É","&Eacute;").replaceAll("È","&Egrave;").replaceAll("Ê","&Ecirc;");
		String i =E.replaceAll("í","&iacute;").replaceAll("ì","&igrave;").replaceAll("î","&icirc;");
		String I =i.replaceAll("Í","&Iacute;").replaceAll("Ì","&Igrave;").replaceAll("Î","&Icirc;");
		String o =I.replaceAll("ó","&oacute;").replaceAll("ò","&ograve;").replaceAll("ô","&ocirc;").replaceAll("õ","&otilde;");
		String O =o.replaceAll("Ó","&Oacute;").replaceAll("Ò","&Ograve;").replaceAll("Ô","&Ocirc;").replaceAll("Õ","&Otilde;");
		String u =O.replaceAll("ú","&uacute;").replaceAll("ù","&ugrave;").replaceAll("û","&ucirc;").replaceAll("ü", "&uuml");
		String U =u.replaceAll("Ú","&Uacute;").replaceAll("Ù","&Ugrave;").replaceAll("Û","&Ucirc;").replaceAll("Ü", "&Uuml");
		String c =U.replaceAll("ç","&ccedil;").replaceAll("Ç","&Ccedil;"); 
		String sobre = c.replaceAll("ª","&ordf;").replaceAll("º","&ordm;");
		//String aspas = sobre.replaceAll("Â“","\"").replaceAll("Â“","\"");
		return sobre;//aspas;
	}
	
}