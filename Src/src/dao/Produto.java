package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Valores;

public class Produto {
	public void inserir (int id, String nome, float valor) throws Exception {
		String sql = "INSERT INTO produto (nome, valor, produtoid) VALUES (?, ?, ?)";
		if (id == 0)
			sql = "INSERT INTO produto (nome, valor) VALUES (?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, nome);
		ps.setFloat(2, valor);

		if (id != 0)
			ps.setInt(3, id);

		ps.executeUpdate();
	}	


	public void atualizar() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public ResultSet getAll() throws SQLException {
		String sql = "SELECT * FROM produto"; 

		PreparedStatement statement = Valores.getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		return result;
	}
}
