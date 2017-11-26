package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.corba.se.impl.ior.GenericTaggedComponent;

import util.Valores;

public class Produto {
	public void inserir (int id, String nome, float valor, int qtde) {
		PreparedStatement ps;
		Connection conn = Valores.getConnection();
		String sql = "INSERT INTO produto VALUES (?, ?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, nome);
			ps.setFloat(3, valor);
			ps.setInt(4, qtde);
			ps.executeUpdate();
			
		} catch(SQLException erro) {
			System.out.println("Problemas na conexao com o banco de dados"	+ erro.toString());
		}
	}	


	@Override
	public void atualizar() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public ResultSet getAll() throws SQLException {
		String sql = "SELECT * FROM Produto"; 
		PreparedStatement statement;
		ResultSet result;
		
		statement = Valores.getConnection().prepareStatement(sql);
		
		result = statement.executeQuery();
		
		return result;
	}
}
