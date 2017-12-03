package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import util.Valores;

public class Comanda {
	public static int novaComanda(int id, int funcionarioId) throws Exception {
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
		return getId.getInt(1);
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
	
	public static void addProduto(int comandaId, int produtoId, int qtde) throws Exception {
		String sql = "INSERT INTO produtoComanda (comandaId, produtoId, quantidade) VALUES (?, ?, ?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, comandaId);
		ps.setInt(2, produtoId);
		ps.setInt(3, qtde);
		ps.executeUpdate();
	}
	
	public static void setValorPagoComanda(int idComanda, float valor) throws Exception {
		String sql = "UPDATE comanda SET valorPago = ? WHERE comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setFloat(1, valor + system.Comanda.getValorPagoComanda(idComanda));
		ps.setInt(2, idComanda);
		ps.executeUpdate();
	}
	
	public static void setValorPagoProduto(int idProduto, int idComanda, float valor) throws Exception {
		String sql = "UPDATE produtoComanda SET valorPago = ? WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, idProduto);
		ps.setInt(3, idComanda);
		ps.executeUpdate();
	}
	
	public static ResultSet getValorPagoComanda(int id) throws Exception {
		String sql = "SELECT valorPago FROM comanda WHERE comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeQuery();
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
	
	public static ResultSet getAllProduto(int id) throws Exception {		
		String sql = "SELECT produtoComanda.produtoId, produto.nome, produtoComanda.quantidade, "
				+ "produto.valor, produtoComanda.valorPago FROM produtoComanda "
				+ "JOIN produto ON produtoComanda.produtoId = produto.produtoId "
				+ "WHERE produtoComanda.comandaId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
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
}
