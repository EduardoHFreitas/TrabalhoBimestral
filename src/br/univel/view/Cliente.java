package br.univel.view;

import br.univel.anotations.Tabela;
import br.univel.anotations.Coluna;
import br.univel.enums.EstadoCivil;

@Tabela("CLIENTES")
public class Cliente {

	@Coluna(pk=true, nome="CLI_ID")
	private int id;

	@Coluna(nome="CLI_NOME", tamanho=60)
	private String nome;

	@Coluna(nome="CLI_ENDERECO", tamanho=100)
	private String endereco;

	@Coluna(nome="CLI_TELEFONE", tamanho=15)
	private String telefone;

	@Coluna(nome="CLI_ESTADO-CIVIL")
	private EstadoCivil estadoCivil; // Gravar o ordinal

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public Cliente() { // Construtor sem parametros
		this(0, null, null, null, null);
	}

	public Cliente(int id, String nome, String endereco, String telefone, EstadoCivil estadoCivil) { // Construtor com parametros
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.estadoCivil = estadoCivil;
	}
}
