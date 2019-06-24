package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;

public class HorarioMarcado implements Serializable {

	private static final long serialVersionUID = -4378450462277642167L;

	private long codigo;
	private int codigoHorario;
	private long codigoBarbeiro;
	private long codigoCliente;
	private Date dia;
	private Date cadastro;
	private Date alterado;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public int getCodigoHorario() {
		return codigoHorario;
	}

	public void setCodigoHorario(int codigoHorario) {
		this.codigoHorario = codigoHorario;
	}

	public long getCodigoBarbeiro() {
		return codigoBarbeiro;
	}

	public void setCodigoBarbeiro(long codigoBarbeiro) {
		this.codigoBarbeiro = codigoBarbeiro;
	}

	public long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
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
