package br.univel.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.univel.enums.EstadoCivil;
import br.univel.interfaces.Funcoes;

public class FuncoesImplementation implements Funcoes {

	private SqlGenImplementation imp;

	public FuncoesImplementation() {
		
		try {

			SqlGenImplementation imp = new SqlGenImplementation();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void salvar(Object obj, int id) {
				
	}

	@Override
	public Object buscar(Object obj, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(Object obj, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Object obj, int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
