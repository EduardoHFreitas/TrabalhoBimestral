package br.univel.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.univel.enums.EstadoCivil;
import br.univel.interfaces.Funcoes;

public class FuncoesImplementation implements Funcoes {

	public FuncoesImplementation() {
		
		Cliente oi = new Cliente(1, "Eduardo", "treta", "treta", EstadoCivil.CASADO);
		
		try {
			SqlGenImplementation teste = new SqlGenImplementation();
			
			System.out.println(teste.getCreateTable(teste.getCon(), oi));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void salvar(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object buscar(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		new FuncoesImplementation();
	}
}
