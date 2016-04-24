package br.univel.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.abstratc.SqlGen;

public class SqlGenImplementation extends SqlGen {

	private Connection con;

	public SqlGenImplementation() throws SQLException{
		StartConnection();

		CloseConnection();
	}

	private void CloseConnection() throws SQLException {
		con.close();
    }


	private void StartConnection() throws SQLException {
		System.out.println("é aqui mesmo");
		String url = "jdbc:h2:D:/Eduardo/Desktop/DBA";
		String user = "admin";
		String pass = "admin";

		con = DriverManager.getConnection(url, user, pass);
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

	public static void main (String[] args){
		try {
			new SqlGenImplementation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
