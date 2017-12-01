package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Valores;

public class Comanda {
	public static int novaComanda(String mesa) throws SQLException {
		int id = -1;
		String sql = "INSERT INTO comanda (mesa) VALUES (?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, mesa);
		ps.executeUpdate();
		
		ResultSet getId = ps.getGeneratedKeys();
		getId.next();
		id = getId.getInt(1);		
		
		return id;
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

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, mesa);
		ps.setInt(2, id);
		ps.executeUpdate();
	}
	
	public static void delete(int id) throws SQLException {
		String sql = "DELETE FROM comanda WHERE comandaId = ?";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
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
		String sql = "SELECT * FROM produtoComanda WHERE comandaId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		return ps.executeQuery();		
	}
}
