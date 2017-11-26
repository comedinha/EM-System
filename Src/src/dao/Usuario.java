package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Crypto;
import util.Valores;

public class Usuario implements IConnector {
	public static void inserir(int funcao, String nome, String username, String password) throws Exception {
		Crypto cr = new Crypto();
		String sql = "INSERT INTO funcionario VALUES (DEFAULT, ?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, cr.encrypt(password));
		ps.setString(3, nome);
		ps.setInt(4, funcao);
		ps.executeUpdate();
	}

	public static boolean login(String username, String password) throws Exception {
		Crypto cr = new Crypto();
		String sql = "SELECT * FROM funcionario WHERE username = ? AND password = ?"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, cr.encrypt(password));

		ResultSet result = statement.executeQuery();
		return result != null;
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
