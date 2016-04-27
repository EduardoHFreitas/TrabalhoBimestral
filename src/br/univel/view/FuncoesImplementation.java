package br.univel.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		
		divisao("TABELA APAGANDO");
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
	public void salvar(Object obj, int id) {
	
		PreparedStatement inclusao = getImp().getSqlInsert(getImp().getCon(), obj);
		
		divisao("INCLUINDO REGISTRO");
		
		Cliente cliente = (Cliente) obj;
		
		try {
			inclusao.setInt(1, id);
			inclusao.setString(2, cliente.getNome());
			inclusao.setString(3, cliente.getEndereco());
			inclusao.setString(4, cliente.getTelefone());
			inclusao.setInt(5, cliente.getEstadoCivil().ordinal());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("SQL -> " + inclusao);
		
		try {
			inclusao.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		divisao("REGISTRO INCLUIDO");
	}

	@Override
	public Object buscar(Object obj, int id) {
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
				System.out.println("EST.CIVIL..: " + exibir.getString("CLI_ESTADOCIVIL")); 
				 

				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		divisao("FIM REGISTRO");
		
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
