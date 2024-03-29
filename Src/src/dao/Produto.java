package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import util.Valores;

/**
 * Classe que todas as operações de CRUD referente a Produto
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 *
 * @version 1.0
 */
public class Produto {
	/**
	 * Insere um novo produto
	 * @param id ID do produto
	 * @param nome Nome do produto
	 * @param valor Valor do produto
	 * @return Retorna se ocorreu tudo bem
	 * @throws Exception
	 */
	public static boolean insereProduto(int id, String nome, float valor) throws Exception {
		String sql = "INSERT INTO produto (nome, valor, produtoid) VALUES (?, ?, ?)";
		if (id == 0)
			sql = "INSERT INTO produto (nome, valor) VALUES (?, ?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, nome);
		ps.setFloat(2, valor);

		if (id != 0)
			ps.setInt(3, id);

		ps.executeUpdate();
		return true;
	}

	/**
	 * Atualiza as informações do produto
	 * @param id ID do produto
	 * @param nome Nome do Produto
	 * @param valor Valor do produto
	 * @return Retorna se ocorreu tudo bem
	 * @throws Exception
	 */
	public static boolean updateProduto(int id, String nome, float valor) throws Exception {
		String sql = "UPDATE produto SET nome = ?, valor = ? WHERE produtoid = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, nome);
		ps.setFloat(2, valor);
		ps.setInt(3, id);
		ps.executeUpdate();
		return true;
	}

	public static ResultSet getAllProduto() throws Exception {
		String sql = "SELECT * FROM produto WHERE status = 1";

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		return result;
	}

	/**
	 * Pesquisa por um determinada produto apartir de seu ID
	 * @param id ID do produto
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static ResultSet getProduto(int id) throws Exception {
		String sql = "SELECT * FROM produto WHERE produtoid = ?";

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		return result;
	}

	/**
	 * Verefica se um produto existe no sistema
	 * @param id ID do produto
	 * @return Se existir, retorna True, caso contrario, False
	 * @throws Exception
	 */
	public static ResultSet verificaExistenciaProduto(int id) throws Exception {
		String sql = "SELECT status FROM produto WHERE produtoId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeQuery();
	}

	/**
	 * Reativa um produto no sistema
	 * @param id ID do produto
	 * @param nome Nome do produto
	 * @param valor Valor do produto 
	 * @return Se existir, retorna True, caso contrario, False
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void reativaProduto(int id, String nome, float valor) throws Exception {
		String sql = "UPDATE produto SET nome = ?, valor = ?, status = ? WHERE produtoid = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, nome);
		ps.setFloat(2, valor);
		ps.setInt(3, 1);
		ps.setInt(4, id);
		ps.executeUpdate();
	}

	/**
	 * Remove um produto do sistema
	 * @param ID do produto
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteProduto(int id) throws Exception {
		String sql = "UPDATE produto SET status = 0 WHERE produtoid = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		return true;
	}

	/**
	 * Retorna o produto alterado pesquisado
	 * @param date Data do produto
	 * @throws Exception
	 */
	public static ResultSet getProdutoAlterado(Timestamp date) throws Exception {
		String sql = "SELECT pa.nome, pa.valor FROM produtoAlterado pa WHERE pa.data = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setTimestamp(1, date);

		ResultSet result = ps.executeQuery();
		return result;
	}
}
