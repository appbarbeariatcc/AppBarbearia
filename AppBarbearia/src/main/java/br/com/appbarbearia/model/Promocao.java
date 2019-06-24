package br.com.appbarbearia.model;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Promocao implements Serializable {

	private static final long serialVersionUID = 4201583534980049131L;

	private int codigo;
	private int codigoServico;
	private long codigoBarbearia;
	private String descricao;
	private double valorDesconto;
	private int porcentagemDesconto;
	private Calendar dataInicio;
	private Calendar dataTermino;
	private Date cadastro;
	private Date alterado;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigoServico() {
		return codigoServico;
	}

	public void setCodigoServico(int codigoServico) {
		this.codigoServico = codigoServico;
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

	public double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public int getPorcentagemDesconto() {
		return porcentagemDesconto;
	}

	public void setPorcentagemDesconto(int porcentagemDesconto) {
		this.porcentagemDesconto = porcentagemDesconto;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Calendar dataTermino) {
		this.dataTermino = dataTermino;
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
		return "Promocao [codigo=" + codigo + ", codigoServico=" + codigoServico + ", codigoBarbearia="
				+ codigoBarbearia + ", descricao=" + descricao + ", valorDesconto=" + valorDesconto
				+ ", porcentagemDesconto=" + porcentagemDesconto + ", dataInicio=" + dataInicio + ", dataTermino="
				+ dataTermino + ", cadastro=" + cadastro + ", alterado=" + alterado + "]";
	}
}
