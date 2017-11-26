package util;

import java.sql.Connection;

import system.Funcionario;

public class Valores {

	private static Connection database;
	private static Funcionario funcionario;

	public static void setFuncionario(Funcionario funcionario) {
		Valores.funcionario = funcionario;
	}

	public static boolean setBanco(Connection db) {
		if (db != null) {
			database = db;
			return true;
		} else {
			return false;
		}
	}

	public static Funcionario getUsuario() {
		return funcionario;
	}

	public static Connection getConnection() {
		return database;
	}
}
