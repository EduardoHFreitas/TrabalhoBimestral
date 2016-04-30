package br.univel.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		String sql = getImp().getDropTable(getImp().getCon(), obj);

		divisao("APAGANDO TABELA");

		System.out.println("SQL -> " + sql);
		try (PreparedStatement ps = getImp().getCon().prepareStatement(sql)){
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("TABELA NAO ENCONTRADA OU NAO FOI POSSIVEL ESTABELECER CONEXAO COM O BANCO!");
		}

		divisao("TABELA APAGADO");
	}

	private void divisao(String funcao) {
		System.out.println("\n*======================== " + funcao + "\n");
	}

	public void CriarTabela(Object obj){
		String sql = getImp().getCreateTable(getImp().getCon(), obj);

		divisao("CRIANDO TABELA");

		System.out.println("SQL -> " + sql);

		try (PreparedStatement ps = getImp().getCon().prepareStatement(sql)){
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		divisao("TABELA CRIADA");

	}

	@Override
	public void salvar(Object obj) {

		PreparedStatement inclusao = getImp().getSqlInsert(getImp().getCon(), obj);

		divisao("INCLUINDO REGISTRO");

		Cliente cliente = (Cliente) obj;

		try {
			inclusao.setInt(1, cliente.getId());
			inclusao.setString(2, cliente.getNome());
			inclusao.setString(3, cliente.getEndereco());
			inclusao.setString(4, cliente.getTelefone());
			inclusao.setInt(5, cliente.getEstadoCivil().ordinal());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Dados inseridos: ");
		System.out.println("ID..........: " + cliente.getId());
		System.out.println("NOME........: " + cliente.getNome());
		System.out.println("ENDERECO....: " + cliente.getEndereco());
		System.out.println("TELEFONE....: " + cliente.getTelefone());
		System.out.println("ESTADOCIVIL.: " + cliente.getEstadoCivil() + " (gravado como " + cliente.getEstadoCivil().ordinal() + ")");

		try {
			inclusao.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		divisao("REGISTRO INCLUIDO");
	}

	@Override
	public Object buscar(Object obj) {
		PreparedStatement buscar = imp.getSqlSelectById(imp.getCon(), obj);

		ResultSet exibir;

		divisao("BUSCANDO REGISTRO");

		try {
			exibir = buscar.executeQuery();
			while (exibir.next()) {
				System.out.println("ID.........: " + exibir.getInt(1));
				System.out.println("NOME.......: " + exibir.getString("CLI_NOME"));
				System.out.println("ENDERECO...: " + exibir.getString("CLI_ENDERECO"));
				System.out.println("TELEFONE...: " + exibir.getString("CLI_TELEFONE"));
				System.out.println("EST.CIVIL..: " + EstadoCivil.values()[exibir.getInt("CLI_ESTADOCIVIL")]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		divisao("FIM REGISTRO");

		return null;
	}

	@Override
	public void atualizar(Object obj) {
		PreparedStatement alterar = imp.getSqlUpdateById(imp.getCon(), obj);

		Cliente cliente = (Cliente) obj;

		int exibir = 0;

		divisao("ALTERANDO REGISTRO");

		try {
			alterar.setString(1, cliente.getNome());
			alterar.setString(2, cliente.getEndereco());
			alterar.setString(3, cliente.getTelefone());
			alterar.setInt(4, cliente.getEstadoCivil().ordinal());
			alterar.setInt(5, cliente.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			exibir = alterar.executeUpdate();
			System.out.println(exibir + " Registro(s) alterados!");
			System.out.println("  Novo nome..........: " + cliente.getNome());
			System.out.println("  Novo endereço......: " + cliente.getEndereco());
			System.out.println("  Novo telefone......: " + cliente.getTelefone());
			System.out.println("  Novo Estado civil..: " + cliente.getEstadoCivil());
			System.out.println("  Alterados para o ID: " + cliente.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		divisao("FIM REGISTRO ALTERADO");
	}

	@Override
	public void excluir(Object obj) {
		PreparedStatement excluir = imp.getSqlDeleteById(imp.getCon(), obj);

		Cliente cliente = (Cliente) obj;

		int exibir = 0;

		divisao("EXCLUINDO REGISTRO");

		try {
			excluir.setInt(1, cliente.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			exibir = excluir.executeUpdate();
			System.out.println(exibir + " Registro(s) excluidos!");
			System.out.println("  Novo nome..........: " + cliente.getNome());
			System.out.println("  Novo endereço......: " + cliente.getEndereco());
			System.out.println("  Novo telefone......: " + cliente.getTelefone());
			System.out.println("  Novo Estado civil..: " + cliente.getEstadoCivil());
			System.out.println("  Excluidos para o ID: " + cliente.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		divisao("FIM REGISTRO EXCLUIDO");



	}

	@Override
	public List<Cliente> listarTodos(Object obj) {

		PreparedStatement listar = imp.getSqlDeleteById(imp.getCon(), obj);

		divisao("APAGANDO TABELA");

		System.out.println("SQL -> " + listar);
		try {
			listar.executeUpdate();
		} catch (SQLException e) {
			System.out.println("TABELA NAO ENCONTRADA OU NAO FOI POSSIVEL ESTABELECER CONEXAO COM O BANCO!");
		}

		divisao("TABELA APAGADO");

		try {
			List<Cliente> ruas = new ArrayList<Cliente>();

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// adiciona a Rua na lista
				ruas.add(populaRua(rs));
			}
			rs.close();
			stmt.close();
			System.out.println("\n");
			return ruas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

}
