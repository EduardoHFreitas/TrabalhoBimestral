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

		getCreateTable(con, oi);
		String strCreateTable = getCreateTable(con, oi);
		System.out.println(strCreateTable);

		CloseConnection();
	}

	private void CloseConnection() throws SQLException {
		con.close();
	}

	private void StartConnection() throws SQLException {

		String url = "jdbc:h2:D:/Eduardo/Desktop/DBA/banco";
		String user = "admin";
		String pass = "admin";

		con = DriverManager.getConnection(url, user, pass);

	}

	@Override
	protected String getCreateTable(Connection con, Object obj) {

		Class<?> cl = obj.getClass();

		try {

			StringBuilder sb = new StringBuilder(); // Construtor de String
			// append adciona os caracteres

			// Declaração da tabela.
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("CREATE TABLE ").append(nomeTabela).append(" (");

			Field[] atributos = cl.getDeclaredFields();

			// Declaração das colunas
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

			// Declaração das chaves primárias
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

			// Declaração da tabela.
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("DROP TABLE ").append(nomeTabela).append(");");

			return sb.toString(); // Cria a String

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
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

	public static void main(String[] args) {
		try {
			new SqlGenImplementation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
