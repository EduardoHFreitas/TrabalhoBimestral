package br.univel.view;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.abstratc.SqlGen;
import br.univel.anotations.Coluna;
import br.univel.anotations.Tabela;
import br.univel.enums.EstadoCivil;

public class SqlGenImplementation extends SqlGen {

	private Connection con;

	public SqlGenImplementation() throws SQLException {
		Cliente oi = new Cliente(1, "Eduardo", "treta", "treta", EstadoCivil.CASADO);

		StartConnection();

		try (PreparedStatement ps = con.prepareStatement(getCreateTable(con, oi))){}

		getDropTable(con, oi);

		PreparedStatement teste = getSqlInsert(con, oi);
		teste.setInt(1, 1);
		teste.setString(2, oi.getNome());
		teste.setString(3, oi.getEndereco());
		teste.setString(4, oi.getTelefone());
		teste.setInt(5, oi.getEstadoCivil().ordinal());

		int res = teste.executeUpdate();

		System.out.println(res);

		CloseConnection();
	}

	private void StartConnection() throws SQLException {

		String url = "jdbc:h2:D:/Eduardo/Desktop/DBA/banco";
		String user = "admin";
		String pass = "admin";

		con = DriverManager.getConnection(url, user, pass);

	}

	private void CloseConnection() throws SQLException {
		con.close();
	}

	@Override
	protected String getCreateTable(Connection con, Object obj) {

		Class<?> cl = obj.getClass();

		try {

			StringBuilder sb = new StringBuilder(); // Construtor de String
			// append adciona os caracteres

			// Declara��o da tabela.
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("CREATE TABLE ").append(nomeTabela).append(" (");

			Field[] atributos = cl.getDeclaredFields();

			// Declara��o das colunas
			for (int i = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				String nomeColuna;
				String tipoColuna;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = anotacaoColuna.nome();
					}

				} else {
					nomeColuna = field.getName().toUpperCase();
				}

				Class<?> tipoParametro = field.getType();

				if (tipoParametro.equals(String.class)) {
					tipoColuna = "VARCHAR(";

					if (field.isAnnotationPresent(Coluna.class)) {
						Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

						if (anotacaoColuna.tamanho() == 0) {
							tipoColuna += "100)";
						} else {
							tipoColuna += anotacaoColuna.tamanho() + ")";
						}
					}
				} else if (tipoParametro.equals(int.class)) {
					tipoColuna = "INT";
				} else if (tipoParametro.equals(EstadoCivil.class)) {
					tipoColuna = "INT(1)";
				} else {
					tipoColuna = "DESCONHECIDO";
				}

				if (i > 0) {
					sb.append(",");
				}

				sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
			}

			// Declara��o das chaves prim�rias
			sb.append(",\n\tPRIMARY KEY( ");

			for (int i = 0, achou = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				if (field.isAnnotationPresent(Coluna.class)) {

					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.pk()) {

						if (achou > 0) {
							sb.append(", ");
						}

						if (anotacaoColuna.nome().isEmpty()) {
							sb.append(field.getName().toUpperCase());
						} else {
							sb.append(anotacaoColuna.nome());
						}

						achou++;
					}

				}
			}

			sb.append(" )\n);");

			return sb.toString(); // Cria a String

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected String getDropTable(Connection con, Object obj) {
		Class<?> cl = obj.getClass();

		try {

			StringBuilder sb = new StringBuilder(); // Construtor de String
			// append adciona os caracteres

			// Declara��o da tabela.
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("DROP TABLE ").append(nomeTabela).append(";");

			return sb.toString(); // Cria a String

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {
		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();

		// Declara��o da tabela.
		String nomeTabela;

		if (cl.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();
		}

		sb.append("INSERT INTO ").append(nomeTabela).append(" (");

		Field[] atributos = cl.getDeclaredFields();

		// Nome dos campos/atributos
		for (int i = 0; i < atributos.length; i++) {

			Field field = atributos[i];

			String nomeColuna;

			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

				if (anotacaoColuna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = anotacaoColuna.nome();
				}

			} else {
				nomeColuna = field.getName().toUpperCase();
			}

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nomeColuna);
		}

		sb.append(") VALUES (");

		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append('?');
		}
		sb.append(");");

		String strSql = sb.toString();

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(strSql);
/*			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];

				// importante n�o esquecer
				field.setAccessible(true);
				if (field.getType().equals(int.class)) {
					ps.setInt(i + 1, field.getInt(obj));
				} else if (field.getType().equals(String.class)) {
					ps.setString(i + 1, String.valueOf(field.get(obj)));
				} else if (field.getType().equals(EstadoCivil.class)) {
					ps.setString(i + 1, String.valueOf(field.get(obj)));
				} else {
					throw new RuntimeException("Tipo n�o suportado, falta implementar.");

				}
			} */
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} /*catch (IllegalAccessException e) {
			e.printStackTrace();
		} */

		return ps;
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

	public static void main(String[] args) {
		try {
			new SqlGenImplementation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
