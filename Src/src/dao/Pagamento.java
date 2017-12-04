package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import util.Valores;

public class Pagamento {
	public static void pagamentoComanda(int id, Timestamp time, Float valor, int funcionarioId, boolean desconto) throws Exception {
		String sql = "INSERT INTO pagamento (comandaId, dataComanda, valor, funcionarioId) VALUES (?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.setTimestamp(2, time);
		ps.setFloat(3, valor);
		ps.setInt(4, funcionarioId);
		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		if (!getId.next())
			throw new Exception("Algum erro ocorreu ao adicionar o pagamento.");

		int pagamentoId = getId.getInt("pagamentoId");

		sql = "INSERT INTO pagamentoComanda (pagamentoId, desconto) VALUES (?, ?)";

		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, pagamentoId);
		ps.setBoolean(2, desconto);
		ps.executeUpdate();
	}
	
	public static void pagamentoProduto(int id, int comandaId, Timestamp comandaTime, Float valor, int funcionarioId) throws Exception {
		String sql = "INSERT INTO pagamento (comandaId, dataComanda, valor, funcionarioId) VALUES (?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, comandaId);
		ps.setTimestamp(2, comandaTime);
		ps.setFloat(3, valor);
		ps.setInt(4, funcionarioId);
		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		if (!getId.next())
			throw new Exception("Algum erro ocorreu ao adicionar o pagamento.");

		int pagamentoId = getId.getInt("pagamentoId");

		sql = "INSERT INTO pagamentoProduto (pagamentoId, produtoId) VALUES (?, ?)";

		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, pagamentoId);
		ps.setInt(2, id);
		ps.executeUpdate();
	}

	public static ResultSet getAll(int id, Timestamp time, boolean desconto) throws Exception {
		String sql = "SELECT p.pagamentoId, p.valor, p.data, CASE WHEN pc.desconto IS NULL THEN 'Pagamento' ELSE 'Desconto' END AS tipo FROM pagamento p JOIN pagamentoComanda pc ON p.pagamentoId = pc.pagamentoId WHERE p.comandaId = ? AND p.dataComanda = ?"
				+ " UNION SELECT p.pagamentoId, p.valor, p.data, pd.nome AS tipo FROM pagamento p JOIN pagamentoProduto pp ON p.pagamentoId = pp.pagamentoId JOIN produto pd ON pp.produtoId = pd.produtoId WHERE p.comandaId = ? AND p.dataComanda = ?";
		if (desconto)
			sql = "SELECT p.pagamentoId, p.valor, p.data, pc.desconto FROM pagamento p JOIN pagamentoComanda pc ON p.pagamentoId = pc.pagamentoId WHERE p.comandaId = ? AND p.dataComanda = ?";

		PreparedStatement p = Valores.getConnection().prepareStatement(sql);
		p.setInt(1, id);
		p.setTimestamp(2, time);

		if (!desconto) {
			p.setInt(3, id);
			p.setTimestamp(4, time);
		}

		ResultSet result = p.executeQuery();
		return result;
	}
}
