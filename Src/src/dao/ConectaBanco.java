package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Valores;

public class ConectaBanco {

	public static void conectaBanco(String type, String addr, String port, String usr, String pass) throws SQLException {
		String url = "jdbc:" +  type + "://"+ addr +":"+ port +"/EMSystem";
		Valores.setBanco(DriverManager.getConnection(url, usr, pass));
		//if (!nullCheck())
		//	return;
	}

	public static void desconectaBanco() throws SQLException {
		Valores.getConnection().close();
	}

	public static boolean nullCheck() throws SQLException {
		String checkSql = "SELECT * From funcionario";
		PreparedStatement stmt = Valores.getConnection().prepareStatement(checkSql);
		ResultSet rs =  stmt.executeQuery();

		boolean empty = true;
		while (rs.next()) {
			empty = false;
		}
	    return empty;
	}
}