package br.com.appbarbearia.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Barbeiro implements Serializable {

	private static final long serialVersionUID = 2982174066743555439L;

	private long codigo;
	private long codigoBarbearia;
	private int codigoCidade;
	private Cidade cidade;
	private String nome;
	private String rg;
	private String cpf;
	private Integer telefone;
	private int celular;
//	URL
	private String foto;
	private Date dataNascimento;
	private Date cadastro;
	private Date alterado;
	private List<HorarioMarcado> horariosMarcados = new LinkedList<>();

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getTelefone() {
		return telefone;
	}

	public void setTelefone(Integer telefone) {
		this.telefone = telefone;
	}

	public int getCelular() {
		return celular;
	}

	public void setCelular(int celular) {
		this.celular = celular;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public List<HorarioMarcado> getHorariosMarcados() {
		return horariosMarcados;
	}

	public void setHorariosMarcados(List<HorarioMarcado> horariosMarcados) {
		this.horariosMarcados = horariosMarcados;
	}

	@Override
	public String toString() {
		return "Barbeiro [codigo=" + codigo + ", codigoBarbearia=" + codigoBarbearia + ", codigoCidade=" + codigoCidade
				+ ", cidade=" + cidade + ", nome=" + nome + ", rg=" + rg + ", cpf=" + cpf + ", foto=" + foto
				+ ", dataNascimento=" + dataNascimento + ", cadastro=" + cadastro + ", alterado=" + alterado
				+ ", horariosMarcados=" + horariosMarcados + "]";
	}
}
