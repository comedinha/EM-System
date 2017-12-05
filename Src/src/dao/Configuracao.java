package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.Valores;

public class Configuracao {
	public static void adicionaConfiguracaoFuncionario(int id, String key, String value) throws Exception {
		String sql = "INSERT INTO configuracaoFuncionario (configKey, funcionarioId, value) VALUES (?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);
		ps.setInt(2, id);
		ps.setString(3, value);
		ps.executeUpdate();
	}

	public static void adicionaConfiguracaoGlobal(String key, String value) throws Exception {
		String sql = "INSERT INTO configuracao (configKey, value) VALUES (?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);
		ps.setString(2, value);
		ps.executeUpdate();
	}

	public static void updateConfiguracaoFuncionario(int id, String key, String value) throws Exception {		
		String sql = "UPDATE configuracaoFuncionario SET value = ? WHERE funcionarioId = ? AND configKey = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, value);
		ps.setInt(2, id);
		ps.setString(3, key);
		ps.executeUpdate();
	}

	public static void updateConfiguracaoGlobal(String key, String value) throws Exception {		
		String sql = "UPDATE configuracao SET value = ? WHERE configKey = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, value);
		ps.setString(2, key);
		ps.executeUpdate();
	}

	public static ResultSet getConfigFuncionario(int id, String key) throws Exception {
		String sql = "SELECT * FROM configuracaoFuncionario WHERE configKey = ? AND funcionarioId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);
		ps.setInt(2, id);

		return ps.executeQuery();
	}

	public static ResultSet getConfigGlobal(String key) throws Exception {
		String sql = "SELECT * FROM configuracao WHERE configKey = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);

		return ps.executeQuery();
	}
}
