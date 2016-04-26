package br.univel.view;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.univel.enums.EstadoCivil;
import br.univel.interfaces.Funcoes;

public class FuncoesImplementation implements Funcoes {

	private SqlGenImplementation imp;

	private SqlGenImplementation getImp() {
		if (imp == null) {
			try {
				setImp(new SqlGenImplementation());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return imp;
	}

	private void setImp(SqlGenImplementation imp) {
		this.imp = imp;
	}

	public void ApagarTabela(Object obj){
		getImp().getDropTable(getImp().getCon(), obj);
	}

	public void CriarTabela(Object obj){
		String sql = getImp().getCreateTable(getImp().getCon(), obj);

		System.out.println("" + sql);
		
		System.out.println();
		
		try (PreparedStatement ps = getImp().getCon().prepareStatement(sql)){
			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void salvar(Object obj, int id) {
	
		PreparedStatement inclusao = getImp().getSqlInsert(getImp().getCon(), obj);
		
		try {
			inclusao.setInt(1, id);
			inclusao.setString(2, "");
			inclusao.setString(3, "");
			inclusao.setString(4, "");
			inclusao.setInt(5, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			inclusao.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object buscar(Object obj, int id) {
		PreparedStatement buscar = imp.getSqlSelectById(imp.getCon(), obj, id);

		try {
			buscar.executeQuery();
		} catch (SQLException e) {
			System.out.println("2");
			e.printStackTrace();
		}

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
