package br.univel.view;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
		PreparedStatement inclusao = imp.getSqlInsert(imp.getCon(), obj);
		
		try {
			inclusao.setInt(1, id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		Class<?> cl = obj.getClass();

		Field[] vetorFields = cl.getDeclaredFields();

		for (Field field : vetorFields) {

			try {
				field.setAccessible(true);

				if (field.getName() != "")
					inclusao.setString(2, (String) field.get(obj));

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
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
