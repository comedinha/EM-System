package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import util.Valores;

public class ConectaBanco {

	public static void conectaBanco() throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/EMSystem";
		String usuario = "postgres";
		String senha = "comedinhabruno1";
		Valores.setBanco(DriverManager.getConnection(url, usuario, senha));
	}

	public static void desconectaBanco() throws SQLException {
		Valores.getConnection().close();
	}
}