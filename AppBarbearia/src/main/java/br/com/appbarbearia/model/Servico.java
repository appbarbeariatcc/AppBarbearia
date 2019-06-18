package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;

public class Servico implements Serializable {

	private static final long serialVersionUID = -3694300017473668994L;

	private long codigo;
	private long codigoBarbearia;
	private String descricao;
	private double preco;
	private Date cadastro;
	private Date alterado;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public long getCodigoBarbearia() {
		return codigoBarbearia;
	}

	public void setCodigoBarbearia(long codigoBarbearia) {
		this.codigoBarbearia = codigoBarbearia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
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

}
