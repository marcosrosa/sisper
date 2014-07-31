package br.jus.jfes.sisper.web;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.jus.jfes.sisper.modelo.Malote;
import br.jus.jfes.sisper.session.MaloteManager;

//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JasperRunManager;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioServlet extends HttpServlet {
	 private static final long serialVersionUID = -63405308563274117L;

	 public void init() throws ServletException {
		      
	 }

	 protected void doGet(HttpServletRequest request,
	         HttpServletResponse response) throws ServletException, IOException {
	      doPost(request, response);
	 }

	 public void doPost(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {		 
		  String pIdGuia = "0";
		  String pLocal  = "0"; //request.getParameter("pLocal");
		  String assinatura = null;
		  String arquivamento = null;
		  String destinatario = null;
		  String imagemPath = "/home/jboss/imagens/brasao.jpg";
		  		  		  
		  ServletOutputStream servletOutputStream=null;		  
		  
		  try{
			  Map<String, Object> params = new HashMap<String,Object>();
			  
			 // params.put("pIdGuia", Integer.valueOf(pIdGuia));
			 // params.put("pImagemPath", imagemPath);
			 // params.put("pNomeLocal", nomeLocal);
			  
			 // if (null != assinatura) {
			//	  params.put("pAssinatura", assinatura);
			//	  params.put("pArquivamento", arquivamento);
			//	  params.put("pDestinatario", destinatario);
			 // }
			  
			  response.setContentType("application/pdf");
			  String host = "http://" + request.getServerName() + ":"
			  	+ request.getServerPort();
			  URL jasper = new URL(host + request.getContextPath()+ "/reports/maloteExterno.jasper");            
			  servletOutputStream = response.getOutputStream();
			  
			  /*InitialContext context = new InitialContext();
			  DataSource datasource = (DataSource) context.lookup("java:jdbc/agravoDS");
			  connection = datasource.getConnection(); */
			  
			 // JRDataSource JRdataSource = createReportDataSource();
			  
			 /* if (JRdataSource != null)			  
				  JasperRunManager.runReportToPdfStream(jasper.openStream(),
						  servletOutputStream, params, JRdataSource);
			  else */
				  System.out.println("__marcos__nao conseguiu conexao banco");
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
		  finally {
			servletOutputStream.flush();
			servletOutputStream.close();
		  }
	 }	
	 
	/* private JRDataSource createReportDataSource() {
		 JRBeanCollectionDataSource dataSource;
		 MaloteManager maloteDAO = new MaloteManager();
		 Malote maloteEnviado = maloteDAO.load(373L);
		 Collection documentos = maloteEnviado.getDocsMalote();
		 dataSource = new JRBeanCollectionDataSource(documentos);
		 return dataSource;		 
	 }*/
	 
}