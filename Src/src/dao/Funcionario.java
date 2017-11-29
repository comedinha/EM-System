package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Crypto;
import util.Valores;

public class Funcionario {
	public static void inserir(int funcao, String nome, String login, String password) throws Exception {
		Crypto cr = new Crypto();
		String sql = "INSERT INTO funcionario (username, password, nome, funcaoid)"
				+ " VALUES (?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);	
		ps.setString(1, login);
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
			Valores.setFuncionario(new system.Funcionario(result.getString("nome"), result.getInt("funcionarioId"), result.getInt("funcaoid")));
			return true;
		}

		return false;
	}

	public boolean update(int id, String nome, String login, String password) throws Exception {
		//ELE PARA ELE MESMO DÁ ERRO.
		String sql = "UPDATE funcionario SET username = ?, nome = ?, password = ?"
				+ " WHERE funcionarioId = ?";
		if (password.isEmpty())
			sql = "UPDATE funcionario SET username = ?, nome = ?"
					+ " WHERE funcionarioId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, login);
		if (!password.isEmpty()) {
			Crypto cr = new Crypto();
			password = cr.encrypt(password);
		
			ps.setString(2, password);
			ps.setString(3, nome);
			ps.setInt(4, id);
		} else {
			ps.setString(2, nome);
			ps.setInt(3, id);
		}
			
		ps.executeUpdate();
		return true;
	}

	public ResultSet getAll() throws SQLException {
		String sql = "SELECT * FROM funcionario"; 

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		return result;
	}
	
	public ResultSet getGerente(int id) throws SQLException {
		String sql = "SELECT funcionarioId FROM funcionario WHERE funcionarioId != ?";
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet result = ps.executeQuery();

		return result;
	}
	
	public boolean delete(int id) throws SQLException {
		String sql = "DELETE FROM funcionario WHERE funcionarioId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();	
		return true;
	}
}
