package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Crypto;
import util.Valores;

/**
 * Classe que todas as operações de CRUD referente a Funcionario.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 *
 * @version 1.0
 */
public class Funcionario {
	public static void inserir(int funcao, String nome, String login, String password, boolean garcom) throws Exception {
		Crypto cr = new Crypto();
		String sql = "INSERT INTO funcionario (login, password, nome, funcaoid, garcom)"
				+ " VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);	
		ps.setString(1, login);
		ps.setString(2, cr.encrypt(password));
		ps.setString(3, nome);
		ps.setInt(4, funcao);
		ps.setBoolean(5, garcom);
		ps.executeUpdate();
	}

	public static boolean login(String username, String password) throws Exception {
		Crypto cr = new Crypto();
		String sql = "SELECT * FROM funcionario WHERE login = ? AND password = ?"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, cr.encrypt(password));

		ResultSet result = statement.executeQuery();

		if (result.next()) {
			Valores.setFuncionario(new system.Funcionario(result.getInt("funcionarioId")));
			return true;
		}

		return false;
	}

	public static ResultSet get(int id) throws Exception {
		String sql = "SELECT * FROM funcionario WHERE funcionarioId = ?"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		statement.setInt(1, id);

		ResultSet result = statement.executeQuery();
		return result;
	}

	public static boolean update(int id, String nome, String login, String password, boolean garcom) throws Exception {
		String sql = "UPDATE funcionario SET login = ?, nome = ?, garcom = ?";
		if (!password.isEmpty())
			sql += ", password = ? WHERE funcionarioId = ?";
		else
			sql += " WHERE funcionarioId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, login);
		ps.setString(2, nome);
		ps.setBoolean(3, garcom);
		if (!password.isEmpty()) {
			Crypto cr = new Crypto();
			ps.setString(4, cr.encrypt(password));
			ps.setInt(5, id);
		} else {
			ps.setInt(4, id);
		}
			
		ps.executeUpdate();
		return true;
	}

	public static ResultSet getAll() throws Exception {
		String sql = "SELECT * FROM funcionario"; 

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);

		ResultSet result = ps.executeQuery();
		return result;
	}
	
	public ResultSet getGerente(int id) throws Exception {
		String sql = "SELECT funcionarioId FROM funcionario WHERE funcionarioId != ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet result = ps.executeQuery();
		return result;
	}
	
	public boolean delete(int id) throws Exception {
		String sql = "DELETE FROM funcionario WHERE funcionarioId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);

		ps.executeUpdate();	
		return true;
	}

	public static String getNomebyId(int id) throws Exception {
		String sql = "SELECT nome FROM funcionario WHERE funcionarioId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet result = ps.executeQuery();
		if (result.next())
			return result.getString("nome");

		return null;
	}
	
	/**
	 * Verifica a existencia de pelo menos um gerente no banco de dados
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public static boolean verificaGerente(Connection con) throws SQLException {
		String checkSql = "SELECT * FROM funcionario WHERE funcaoid = 1";
		PreparedStatement stmt = con.prepareStatement(checkSql);
		ResultSet rs =  stmt.executeQuery();

		return rs.next();
	}

	public static ResultSet getAllGarcom() throws Exception {
		String sql = "SELECT * FROM funcionario WHERE garcom = true";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);

		ResultSet result = ps.executeQuery();
		return result;
	}
}
