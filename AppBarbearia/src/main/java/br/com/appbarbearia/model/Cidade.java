package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;

public class Cidade implements Serializable {

	private static final long serialVersionUID = -7662743840785590775L;

	private int codigo;
	private String nome;
	private Estados estado;
	private Date cadastro;
	private Date alterado;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
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
		return "Cidade [codigo=" + codigo + ", nome=" + nome + ", estado=" + estado + ", cadastro=" + cadastro
				+ ", alterado=" + alterado + "]";
	}
}
