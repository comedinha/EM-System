package dao;

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

	public static ResultSet getAll(int id, Timestamp time, boolean desconto) throws Exception {
		String sql = "SELECT p.pagamentoId, p.valor, p.data FROM pagamento p JOIN pagamentoComanda pc ON p.pagamentoId = pc.pagamentoId WHERE pc.comandaId = ? AND pc.dataComanda = ? AND pc.desconto = ?";
		if (!desconto)
			sql += " UNION SELECT p.pagamentoId, p.valor, p.data FROM pagamento p JOIN pagamentoProduto pp ON p.pagamentoId = pp.pagamentoId WHERE pp.comandaId = ? AND pp.dataComanda = ?";

		PreparedStatement p = Valores.getConnection().prepareStatement(sql);
		p.setInt(1, id);
		p.setTimestamp(2, time);
		p.setBoolean(3, desconto);

		if (!desconto) {
			p.setInt(4, id);
			p.setTimestamp(5, time);
		}

		ResultSet result = p.executeQuery();
		return result;
	}
}
