package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;

public class Endereco implements Serializable {

	private static final long serialVersionUID = 3705708728373168798L;

	private int codigo;
	private int cidadeCodigo;
	private Cidade cidade;
	private String endereço;
	private int numero;
	private String cep;
	private Date criadoEm;
	private Date alteradoEm;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCidadeCodigo() {
		return cidadeCodigo;
	}

	public void setCidadeCodigo(int cidadeCodigo) {
		this.cidadeCodigo = cidadeCodigo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getEndereço() {
		return endereço;
	}

	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Date getAlteradoEm() {
		return alteradoEm;
	}

	public void setAlteradoEm(Date alteradoEm) {
		this.alteradoEm = alteradoEm;
	}

	@Override
	public String toString() {
		return "Endereco [codigo=" + codigo + ", cidade=" + cidade + ", endereço=" + endereço + ", numero=" + numero
				+ ", cep=" + cep + ", criadoEm=" + criadoEm + ", alteradoEm=" + alteradoEm + "]";
	}
}
