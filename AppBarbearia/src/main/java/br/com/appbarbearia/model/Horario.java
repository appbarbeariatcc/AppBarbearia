package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;

public class Horario implements Serializable {

	private static final long serialVersionUID = -941515513807208076L;

	private int codigo;
	private String descricao;
	private Date hora;
	private Date cadastro;
	private Date alterado;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
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
		return "Horario [codigo=" + codigo + ", descricao=" + descricao + ", hora=" + hora + ", cadastro=" + cadastro
				+ ", alterado=" + alterado + "]";
	}
}
