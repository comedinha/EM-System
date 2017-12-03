package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import util.Valores;

public class Pagamento {
	public static void pagamentoComanda(int id, Timestamp time, Float valor, int funcionarioId, boolean desconto) throws Exception {
		String sql = "INSERT INTO pagamento (valor, funcionarioId) VALUES (?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setFloat(1, valor);
		ps.setInt(2, funcionarioId);
		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		if (!getId.next())
			throw new Exception("Algum erro ocorreu ao adicionar o pagamento.");

		int pagamentoId = getId.getInt("pagamentoId");

		sql = "INSERT INTO pagamentoComanda (pagamentoId, comandaId, dataComanda, desconto) VALUES (?, ?, ?, ?)";

		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, pagamentoId);
		ps.setInt(2, id);
		ps.setTimestamp(3, time);
		ps.setBoolean(4, desconto);
		ps.executeUpdate();
	}
	
	public static void pagamentoProduto(int id, int quantidade, int comandaId, Timestamp comandaTime, Float valor, int funcionarioId) throws Exception {
		String sql = "INSERT INTO pagamento (valor, funcionarioId) VALUES (?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setFloat(1, valor);
		ps.setInt(2, funcionarioId);
		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		if (!getId.next())
			throw new Exception("Algum erro ocorreu ao adicionar o pagamento.");

		int pagamentoId = getId.getInt("pagamentoId");

		sql = "INSERT INTO pagamentoProduto (pagamentoId, comandaId, dataComanda, produtoId, quantidade) VALUES (?, ?, ?, ?, ?)";

		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, pagamentoId);
		ps.setInt(2, comandaId);
		ps.setTimestamp(3, comandaTime);
		ps.setInt(4, id);
		ps.setInt(5, quantidade);
		ps.executeUpdate();
	}

	public static ResultSet getAll() throws Exception {
		String sql = "SELECT * FROM pagamento"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		return result;
	}
	
	public static ResultSet get(int id) throws Exception {
		String sql = "SELECT * FROM pagamento WHERE pagamentoId = ?"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		return result;
	}
}
