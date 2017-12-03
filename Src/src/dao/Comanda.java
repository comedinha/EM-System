package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Valores;

public class Comanda {
	public static int novaComanda() throws SQLException {
		int id;
		String sql = "INSERT INTO comanda VALUES (DEFAULT)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.executeUpdate();
		ResultSet getId = ps.getGeneratedKeys();
		getId.next();
		id = getId.getInt(1);
		return id;
	}
	
	public static void novaComandaId(int id) throws SQLException {
		String sql = "INSERT INTO comanda (comandaId) VALUES (?)";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
	}
	
	public static boolean existeNaComanda(int idProduto, int idComanda) throws SQLException {
		String sql = "SELECT * FROM produtoComanda WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		return ps.executeQuery().next();
	}
	
	public static void updateQtde(int idProduto, int idComanda, int qtde) throws SQLException {		
		String sql = "UPDATE produtoComanda SET quantidade = ? WHERE produtoId = ? AND comandaId = ?";
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, qtde);
		ps.setInt(2, idProduto);
		ps.setInt(3, idComanda);
		ps.executeUpdate();
	}
	
	public static ResultSet getQtdeProdutoComanda(int idProduto, int idComanda) throws SQLException {
		String sql = "SELECT quantidade FROM produtoComanda WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		return ps.executeQuery();
	}
	
	public static void removeProdutoComanda(int idProduto, int idComanda) throws SQLException {
		String sql = "DELETE FROM produtoComanda WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		ps.executeUpdate();		
	}
	
	public static void addProduto(int comandaId, int produtoId, int qtde) throws SQLException {
		String sql = "INSERT INTO produtoComanda (comandaId, produtoId, quantidade) VALUES (?, ?, ?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, comandaId);
		ps.setInt(2, produtoId);
		ps.setInt(3, qtde);
		ps.executeUpdate();
	}
	
	public static void atualizarNomeMesa(String mesa, int id) throws SQLException {
		String sql = "UPDATE comanda SET mesa = ? WHERE comandaId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, mesa);
		ps.setInt(2, id);
		ps.executeUpdate();
	}
	
	public static void setValorPagoComanda(int idComanda, float valor) throws SQLException {
		String sql = "UPDATE comanda SET valorPago = ? WHERE comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setFloat(1, valor + system.Comanda.getValorPagoComanda(idComanda));
		ps.setInt(2, idComanda);
		ps.executeUpdate();
	}
	
	public static void setValorPagoProduto(int idProduto, int idComanda, float valor) throws SQLException {
		String sql = "UPDATE produtoComanda SET valorPago = ? WHERE produtoId = ? AND comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, idProduto);
		ps.setInt(3, idComanda);
		ps.executeUpdate();
	}
	
	public static ResultSet getValorPagoComanda(int id) throws SQLException {
		String sql = "SELECT valorPago FROM comanda WHERE comandaId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeQuery();
	}

	public static void get(int i) throws SQLException {
		// TODO Auto-generated method stub
	}

	public static ResultSet getAllComandas() throws SQLException {
		String sql = "SELECT * FROM comanda";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		
		return ps.executeQuery();
	}
	
	public static ResultSet getAllProduto(int id) throws SQLException {		
		String sql = "SELECT produtoComanda.produtoId, produto.nome, produtoComanda.quantidade, "
				+ "produto.valor, produtoComanda.valorPago FROM produtoComanda "
				+ "JOIN produto ON produtoComanda.produtoId = produto.produtoId "
				+ "WHERE produtoComanda.comandaId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeQuery();		
	}
}
