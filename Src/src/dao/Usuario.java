package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Crypto;
import util.Valores;

public class Usuario implements IConnector {
	public static void inserir(int funcao, String nome, String username, String password) throws Exception {
		Crypto cr = new Crypto();
		String sql = "INSERT INTO funcionario(username, password, nome, funcaoid)"
				+ " VALUES (?, ?, ?, ?)";

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

		if (result.next()) {
			Valores.setUsuario(new sistema.Usuario(result.getString("nome"), result.getInt("funcionarioId"), result.getInt("funcaoid")));
			return true;
		}

		return false;
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
	public ResultSet getAll() throws SQLException {
		String sql = "SELECT * FROM funcionario"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		return result;
	}
}
