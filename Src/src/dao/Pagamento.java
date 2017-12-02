package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import util.Valores;

public class Pagamento {
	public static boolean inserir(float valor, int comanda, Date dataComanda) throws Exception {
		String sql = "INSERT INTO pagamento (valor, funcionarioId, comandaId, dataComanda) VALUES (?, ?, ?, ?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
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
