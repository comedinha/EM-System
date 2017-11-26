package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Valores;

public class Produto {
	public void inserir (int id, String nome, float valor, int qtde) throws Exception {
		String sql = "INSERT INTO produto VALUES (?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, nome);
		ps.setFloat(3, valor);
		ps.setInt(4, qtde);
		ps.executeUpdate();
	}	


	public void atualizar() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet getAll() throws SQLException {
		String sql = "SELECT * FROM Produto"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		return result;
	}
}
