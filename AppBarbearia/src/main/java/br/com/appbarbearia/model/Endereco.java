package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;

public class Endereco implements Serializable {

	private static final long serialVersionUID = 3705708728373168798L;

	private int codigo;
	private int codigoCidade;
	private Cidade cidade;
	private String endereco;
	private int numero;
	private String cep;
	private Date cadastro;
	private Date alterado;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoCidade() {
		return codigoCidade;
	}

	public void setCodigoCidade(int codigoCidade) {
		this.codigoCidade = codigoCidade;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}

	public Date getAlterado() {
		return alterado;
	}

	public void setAlterado(Date alterado) {
		this.alterado = alterado;
	}

	@Override
	public String toString() {
		return "Endereco [codigo=" + codigo + ", codigoCidade=" + codigoCidade + ", cidade=" + cidade + ", endereço="
				+ endereco + ", numero=" + numero + ", cep=" + cep + ", cadastro=" + cadastro + ", alterado=" + alterado
				+ "]";
	}
}
