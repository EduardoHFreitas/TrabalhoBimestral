package br.univel.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.abstratc.SqlGen;

public class Principal extends SqlGen {

	private Connection con;

	public Principal() throws SQLException{
		StartConnection();

	}

	private void StartConnection() throws SQLException {
		String url = "jdbc:h2:C:/Users/ehdfreitas/Desktop/DBA/banco.mv";
		String user = "admin";
		String pass = "admin";

		con = DriverManager.getConnection(url, user, pass);
	}

	public static void main (String[] args){
		try {
			new Principal();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getCreateTable(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Connection con, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
