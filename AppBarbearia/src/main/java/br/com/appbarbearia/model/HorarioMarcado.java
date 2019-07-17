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

	public HorarioMarcado(){

	}

	private HorarioMarcado(HorarioMarcadoBuilder builder) {
		this.codigo = builder.codigo;
		this.codigoHorario = builder.codigoHorario;
		this.codigoBarbeiro = builder.codigoBarbeiro;
		this.codigoCliente = builder.codigoCliente;
		this.dia = builder.dia;
		this.cadastro = builder.cadastro;
		this.alterado = builder.alterado;
	}

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

	public static HorarioMarcadoBuilder builder() {
		return new HorarioMarcadoBuilder();
	}

	public static final class HorarioMarcadoBuilder {
		private long codigo;
		private int codigoHorario;
		private long codigoBarbeiro;
		private long codigoCliente;
		private Date dia;
		private Date cadastro;
		private Date alterado;

		private HorarioMarcadoBuilder() {
		}

		public HorarioMarcadoBuilder withCodigo(long codigo) {
			this.codigo = codigo;
			return this;
		}

		public HorarioMarcadoBuilder withCodigoHorario(int codigoHorario) {
			this.codigoHorario = codigoHorario;
			return this;
		}

		public HorarioMarcadoBuilder withCodigoBarbeiro(long codigoBarbeiro) {
			this.codigoBarbeiro = codigoBarbeiro;
			return this;
		}

		public HorarioMarcadoBuilder withCodigoCliente(long codigoCliente) {
			this.codigoCliente = codigoCliente;
			return this;
		}

		public HorarioMarcadoBuilder withDia(Date dia) {
			this.dia = dia;
			return this;
		}

		public HorarioMarcadoBuilder withCadastro(Date cadastro) {
			this.cadastro = cadastro;
			return this;
		}

		public HorarioMarcadoBuilder withAlterado(Date alterado) {
			this.alterado = alterado;
			return this;
		}

		public HorarioMarcado build() {
			return new HorarioMarcado(this);
		}
	}
}