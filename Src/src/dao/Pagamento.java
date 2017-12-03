package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import util.Valores;

public class Pagamento {
	public static int pagamentoComanda(int id, int funcionarioId) throws Exception {
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
	
	public static boolean pagamentoProduto(float valor, int comanda, Date dataComanda) throws Exception {
		String sql = "INSERT INTO pagamento (valor, funcionarioId, comandaId, dataComanda) VALUES (?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, Valores.getUsuario().getId());
		ps.setInt(3, comanda);
		ps.setDate(4, dataComanda);

		ps.executeUpdate();
		return true;
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
