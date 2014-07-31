package br.jus.jfes.sisper.web;

import java.util.ArrayList;
import java.util.Collection;

import br.jus.jfes.sisper.modelo.Documento;
import br.jus.jfes.sisper.modelo.Localidade;
import br.jus.jfes.sisper.modelo.Malote;
import br.jus.jfes.sisper.modelo.MaloteVirtual;
import br.jus.jfes.sisper.modelo.TipoDocumento;

public class CriaDataReport {
	
	public static Collection createBeanCollection() {
		 Collection lista = new ArrayList();
		 Documento documento = new Documento();
		 documento.setCodigo(888L);
		 documento.setAosCuidados("Marcos Rosa");
		 documento.setNumDoc("rm999");
		 documento.setTipoDocumento(new TipoDocumento());
		 Localidade dest = new Localidade();
		 dest.setDescricao("so prara testre");
		 documento.setDestinatario(dest);
		 Localidade remet =  new Localidade();
		 remet.setDescricao("o remetente");
		 documento.setRemetente(remet);

		 Documento documento2 = new Documento();
		 documento2.setCodigo(777L);
		 documento2.setAosCuidados("Marcos Rosa");
		 documento2.setNumDoc("rm888");
		 documento2.setTipoDocumento(new TipoDocumento());
		 Localidade dest2 = new Localidade();
		 dest2.setDescricao("ourto");
		 documento2.setDestinatario(dest2);
		 Localidade remet2 =  new Localidade();
		 remet2.setDescricao("outro remetente");
		 documento2.setRemetente(remet2);
		 MaloteVirtual mv1 = new MaloteVirtual(new Malote(), documento, "marcos");
		 MaloteVirtual mv2 = new MaloteVirtual(new Malote(), documento2, "jose");
		 lista.add(mv1);
		 lista.add(mv2);
		 Collection documentos = lista;
		 return documentos;		 		
	}

}
