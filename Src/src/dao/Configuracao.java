package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.Valores;

/**
 * Classe referente configuração do sistema.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class Configuracao {
	/**
	 * Realiza a adição da configuração de funcionário no banco de dados.
	 * @param id id do funcionário
	 * @param key chave da configuração
	 * @param value valor da configuração
	 * @throws Exception
	 */
	public static void adicionaConfiguracaoFuncionario(int id, String key, String value) throws Exception {
		String sql = "INSERT INTO configuracaoFuncionario (configKey, funcionarioId, value) VALUES (?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);
		ps.setInt(2, id);
		ps.setString(3, value);
		ps.executeUpdate();
	}

	/**
	 * Realiza a adição da configuração global no banco de dados.
	 * @param key chave da configuração
	 * @param value valor da configuração
	 * @throws Exception
	 */
	public static void adicionaConfiguracaoGlobal(String key, String value) throws Exception {
		String sql = "INSERT INTO configuracao (configKey, value) VALUES (?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);
		ps.setString(2, value);
		ps.executeUpdate();
	}

	/**
	 * Realiza a atualização da configuração de funcionário no banco de dados.
	 * @param id id do funcionário
	 * @param key chave da configuração
	 * @param value valor da configuração
	 * @throws Exception
	 */
	public static void updateConfiguracaoFuncionario(int id, String key, String value) throws Exception {		
		String sql = "UPDATE configuracaoFuncionario SET value = ? WHERE funcionarioId = ? AND configKey = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, value);
		ps.setInt(2, id);
		ps.setString(3, key);
		ps.executeUpdate();
	}

	/**
	 * Realiza a atualização da configuração global no banco de dados.
	 * @param key chave da configuração
	 * @param value valor da configuração
	 * @throws Exception
	 */
	public static void updateConfiguracaoGlobal(String key, String value) throws Exception {		
		String sql = "UPDATE configuracao SET value = ? WHERE configKey = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, value);
		ps.setString(2, key);
		ps.executeUpdate();
	}

	/**
	 * Realiza a busca da configuração de funcionário no banco de dados.
	 * @param id id do funcionário
	 * @param key chave da configuração
	 * @throws Exception
	 */
	public static ResultSet getConfigFuncionario(int id, String key) throws Exception {
		String sql = "SELECT * FROM configuracaoFuncionario WHERE configKey = ? AND funcionarioId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);
		ps.setInt(2, id);

		return ps.executeQuery();
	}

	/**
	 * Realiza a busca da configuração global no banco de dados.
	 * @param key chave da configuração
	 * @throws Exception
	 */
	public static ResultSet getConfigGlobal(String key) throws Exception {
		String sql = "SELECT * FROM configuracao WHERE configKey = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, key);

		return ps.executeQuery();
	}
}
