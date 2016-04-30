package br.univel.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.enums.EstadoCivil;

public class Principal extends FuncoesImplementation {

	public Principal() {

		Cliente c1 = new Cliente(1, "Eduardo", "R:Flamboyant", "1111-1111", EstadoCivil.SOLTEIRO);

		ApagarTabela(c1);

		CriarTabela(c1);

		salvar(c1);

		Cliente c2 = new Cliente(2, "Joao", "R:Parana", "2222-2222", EstadoCivil.SEPARADO);

		salvar(c2);

		Cliente c3 = new Cliente(3, "Jose", "R:Cascavel", "4444-3333", EstadoCivil.SEPARADO);

		salvar(c3);

		listarTodos();

		buscar(c1);

		c3.setNome("NOME 3");
		c3.setEndereco("ENDERECO 3");
		c3.setTelefone("TELEFONE 3");
		c3.setEstadoCivil(EstadoCivil.VIUVO);

		atualizar(c3);

		excluir(c2);

		listarTodos();
	}

	public static void main(String[] args) {
		new Principal();
	}
}
