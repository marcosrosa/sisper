package br.jus.jfes.sisper.modelo;

public class RegistroBanco {
	/*
	 * a ideia era fazer uma consulta que retornasse em uma ida ao banco
	 * o codigo anterior ou posterior e tipo de remessa para exibicao da 
	 * pagina. 
	 */
	 
	private Long codigo;
	private int tipoRemessa;
	
	public RegistroBanco(Long codigo, int tipoRemessa) {
		this.codigo = codigo;
		this.tipoRemessa = tipoRemessa;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public int getTipoRemessa() {
		return tipoRemessa;
	}
	public void setTipoRemessa(int tipoRemessa) {
		this.tipoRemessa = tipoRemessa;
	}
	
	

}
