package util;

import java.sql.Connection;
import sistema.Usuario;

public class Valores {

	private static Connection database;
	private static Usuario usuario;

	public static void setUsuario(Usuario usr) {
		usuario = usr;
	}

	public static void setBanco(Connection db) {
		database = db;
	}

	public static Usuario getUsuario() {
		return usuario;
	}

	public static Connection getConnection() {
		return database;
	}
}
