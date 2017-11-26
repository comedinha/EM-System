package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Crypto;
import util.Valores;

public class ConectaBanco {
	public static void conectaBanco(String type, String addr, String port, String usr, String pass, Boolean checkFunc) throws Exception {
		String url = "jdbc:" +  type + "://"+ addr +":"+ port +"/EMSystem";
		Crypto cr = new Crypto();
		Connection con = DriverManager.getConnection(url, usr, cr.decrypt(pass));
		if (checkFunc && !verificaGerente(con))
			throw new Exception("Nenhum funcionário existente.");
		Valores.setBanco(con);
	}

	public static void desconectaBanco() throws SQLException {
		Valores.getConnection().close();
	}

	public static boolean verificaGerente(Connection con) throws SQLException {
		String checkSql = "SELECT * FROM funcionario WHERE funcaoid = 1";
		PreparedStatement stmt = con.prepareStatement(checkSql);
		ResultSet rs =  stmt.executeQuery();

		boolean full = false;
		while (rs.next()) {
			full = true;
		}
	    return full;
	}
}