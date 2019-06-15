package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Barbearia implements Serializable {

	private static final long serialVersionUID = -1931496073277262115L;

	private int codigo;
	private String nome;
	private String descricao;
	private Endereco endereco;
//	private Horario horario;
	private Calendar horarioAbertura;
	private Calendar horarioFechamento;
	private List<Servico> servicos;
	private List<Barbeiro> barbeiros;
//	private List<Promocao> promocoes;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

}
