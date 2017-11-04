package bancodedados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import valores.Valores;

public class ConectaBanco {

	public void conectaBanco() {
		String url = "jdbc:postgresql://localhost:5432/EMSystem";
		String usuario = "postgres";
		String senha = "comedinhabruno1";
		try {
			Valores.setBanco(DriverManager.getConnection(url, usuario, senha));
		} catch (SQLException e) {
			System.out.println("Problemas na conexao com a fonte de dados"	+ e.toString());
		}
	}

	public void desconectaBanco() {
		try {
			Valores.getConnection().close();
		} catch (SQLException e) {
			System.out.println("Problemas na conexao com a fonte de dados"	+ e.toString());
		}
	}

}