package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Barbearia implements Serializable {

	private static final long serialVersionUID = -1931496073277262115L;

	private long codigo;
	private String nome;
	private String descricao;
	private int codigoEndereco;
	private Endereco endereco;
	private Calendar horarioAbertura;
	private Calendar horarioFechamento;
	private Date cadastro;
	private Date alterado;
	private List<Servico> servicos;
	private List<Barbeiro> barbeiros;
	private List<Promocao> promocoes;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getCodigoEndereco() {
		return codigoEndereco;
	}

	public void setCodigoEndereco(int codigoEndereco) {
		this.codigoEndereco = codigoEndereco;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Calendar getHorarioAbertura() {
		return horarioAbertura;
	}

	public void setHorarioAbertura(Calendar horarioAbertura) {
		this.horarioAbertura = horarioAbertura;
	}

	public Calendar getHorarioFechamento() {
		return horarioFechamento;
	}

	public void setHorarioFechamento(Calendar horarioFechamento) {
		this.horarioFechamento = horarioFechamento;
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

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Barbeiro> getBarbeiros() {
		return barbeiros;
	}

	public void setBarbeiros(List<Barbeiro> barbeiros) {
		this.barbeiros = barbeiros;
	}

	public List<Promocao> getPromocoes() {
		return promocoes;
	}

	public void setPromocoes(List<Promocao> promocoes) {
		this.promocoes = promocoes;
	}

	@Override
	public String toString() {
		return "Barbearia [codigo=" + codigo + ", nome=" + nome + ", descricao=" + descricao + ", codigoEndereco="
				+ codigoEndereco + ", endereco=" + endereco + ", horarioAbertura=" + horarioAbertura
				+ ", horarioFechamento=" + horarioFechamento + ", servicos=" + servicos + ", barbeiros=" + barbeiros
				+ ", promocoes=" + promocoes + "]";
	}
}
