package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.Valores;

public class Usuario implements IConnector {
	public static void inserir(int funcao, String nome, String username, String password) {
		PreparedStatement ps;
		Connection conn = Valores.getConnection();
		String sql = "INSERT INTO funcionario VALUES (DEFAULT, ?, ?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setInt(4, funcao);
			ps.executeUpdate();
			
		} catch(SQLException erro) {
			System.out.println("Problemas na conexao com o banco de dados"	+ erro.toString());
		}
	}

	@Override
	public void atualizar() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void get(int i) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAll() throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
