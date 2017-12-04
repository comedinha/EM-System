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
	public static boolean inserir(int id, String nome, float valor) throws Exception {
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

	public static boolean atualizar(int id, String nome, float valor) throws Exception {
		String sql = "UPDATE produto SET nome = ?, valor = ? WHERE produtoid = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, nome);
		ps.setFloat(2, valor);
		ps.setInt(3, id);
		ps.executeUpdate();			
		return true;
	}

	public static ResultSet getAll() throws Exception {
		String sql = "SELECT * FROM produto WHERE status = 1"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		return result;
	}
	
	public static ResultSet get(int id) throws Exception {
		String sql = "SELECT * FROM produto WHERE produtoid = ?"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		return result;
	}
	
	public static boolean verificaExistenciaProduto(int id) throws Exception {
		String sql = "SELECT produtoId FROM produto WHERE produtoId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeQuery().next();
	}

	public static boolean delete(int id) throws Exception {
		String sql = "UPDATE produto SET status = 0 WHERE produtoid = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();			
		return true;
	}

	public static ResultSet getPrecoProduto(Timestamp data) throws Exception {
		String sql = "SELECT * FROM produto p LEFT JOIN produtoAlterado pa ON p.produtoId = pa.produtoId WHERE data <= ? ORDER BY (pa.data)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setTimestamp(1, data);

		ResultSet result = ps.executeQuery();
		return result;
	}
}
