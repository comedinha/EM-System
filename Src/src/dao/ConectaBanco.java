package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import util.Valores;

public class ConectaBanco {

	public static void conectaBanco(String type, String addr, String port, String usr, String pass) throws SQLException {
		String url = "jdbc:" +  type + "://"+ addr +":"+ port +"/EMSystem";
		Valores.setBanco(DriverManager.getConnection(url, usr, pass));
	}

	public static void desconectaBanco() throws SQLException {
		Valores.getConnection().close();
	}
}