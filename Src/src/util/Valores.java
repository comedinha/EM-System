package util;

import java.sql.Connection;

import controller.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.Funcionario;

public class Valores {
	private static ObservableList<String> editCheck = FXCollections.observableArrayList();
	private static Connection database;
	private static Funcionario funcionario;
	private static MenuController controller;

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

	public static void setController(MenuController controller) {
		Valores.controller = controller;
	}

	public static Funcionario getUsuario() {
		return funcionario;
	}

	public static Connection getConnection() {
		return database;
	}

	public static MenuController getController() {
		return controller;
	}

	public static ObservableList<String> editCheck() {
		return editCheck;
	}
}
