package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import util.Valores;

public class Comanda {
	public static ResultSet novaComanda(int id, int funcionarioId) throws Exception {
		String sql = "INSERT INTO comanda (funcionarioId, comandaId) VALUES (?, ?)";
		if (id == 0) {
			sql = "INSERT INTO comanda (funcionarioId) VALUES (?)";
		}

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, funcionarioId);

		if (id != 0)
			ps.setInt(2, id);

		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		getId.next();
		return getId;
	}
	
	public static boolean existeNaComanda(int idProduto, int idComanda) throws Exception {
		String sql = "SELECT * FROM produtoComanda WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		return ps.executeQuery().next();
	}
	
	public static void updateQtde(int idProduto, int idComanda, int qtde) throws Exception {		
		String sql = "UPDATE produtoComanda SET quantidade = ? WHERE produtoId = ? AND comandaId = ?";
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, qtde);
		ps.setInt(2, idProduto);
		ps.setInt(3, idComanda);
		ps.executeUpdate();
	}
	
	public static ResultSet getQtdeProdutoComanda(int idProduto, int idComanda) throws Exception {
		String sql = "SELECT quantidade FROM produtoComanda WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		return ps.executeQuery();
	}
	
	public static void removeProdutoComanda(int idProduto, int idComanda) throws Exception {
		String sql = "DELETE FROM produtoComanda WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		ps.executeUpdate();		
	}
	
	public static void addProduto(int comandaId, Timestamp data, int produtoId, int qtde) throws Exception {
		String sql = "INSERT INTO produtoComanda (comandaId, dataComanda, produtoId, quantidade) VALUES (?, ?, ?, ?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, comandaId);
		ps.setTimestamp(2, data);
		ps.setInt(3, produtoId);
		ps.setInt(4, qtde);
		ps.executeUpdate();
	}

	public static ResultSet getComanda(int id, Timestamp data) throws Exception {
		String sql = "SELECT * FROM comanda WHERE comandaId = ? AND data = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.setTimestamp(2, data);
		return ps.executeQuery();
	}

	public static ResultSet getAllComandas() throws Exception {
		String sql = "SELECT * FROM comanda";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		
		return ps.executeQuery();
	}
	
	public static ResultSet getAllProduto(int id, Timestamp data) throws Exception {		
		String sql = "SELECT pc.produtoId, p.nome, pc.quantidade, p.valor, pg.valor FROM produtoComanda pc LEFT JOIN pagamentoProduto pp ON pp.produtoId = pc.produtoId JOIN pagamento pg ON pg.pagamentoId = pp.pagamentoId JOIN produto p ON p.produtoId = pc.produtoId WHERE pc.comandaId = ? AND pc.dataComanda = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.setTimestamp(2, data);
		return ps.executeQuery();		
	}

	public static boolean update(int id, String mesa, int funcionario, boolean pago) throws Exception {
		String sql = "UPDATE comanda SET mesa = ?, funcionarioId = ?, pago = ? WHERE comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, mesa);
		ps.setInt(2, funcionario);
		ps.setBoolean(3, pago);
		ps.setInt(4, id);
		ps.executeUpdate();
		return true;
	}

	public static Timestamp getDataProduto(int id) throws SQLException {
		String sql = "SELECT data FROM produtoAlterado WHERE id = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet result = ps.executeQuery();
		result.next();
		return result.getTimestamp(1);
	}

	public static float getPrecoComanda(int comandaId, Timestamp date) throws Exception {
		String sql = "SELECT cp.data, cp.quantidade, p.valor, pa.data, pa.valor FROM comanda c JOIN produtoComanda cp ON c.comandaId = cp.comandaId JOIN produto p ON p.produtoId = cp.produtoId LEFT JOIN produtoAlterado pa ON p.produtoId = pa.produtoId WHERE c.data = ? AND c.comandaId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setTimestamp(1, date);
		ps.setInt(2, comandaId);

		float valor = 0;
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			valor += result.getFloat(3) * result.getInt(2);
		}

		return valor;
	}
}


