package util;

import java.sql.Connection;

import controller.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import system.Funcionario;

/**
 * Possui as variaveis globais necessarias para o sistema.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Valores {
	private static ObservableList<String> editCheck = FXCollections.observableArrayList();
	private static Connection database;
	private static Funcionario funcionario;
	private static MenuController controller;
	
	/**
	 * Salva o funcionario logado no sistema no atual momento
	 * @param funcionario Objeto funcioanario
	 */
	public static void setFuncionario(Funcionario funcionario) {
		Valores.funcionario = funcionario;
	}
	
	/**
	 * Salva a conexão com o BD
	 * @param db Objeto do BD salvo
	 */
	public static boolean setBanco(Connection db) {
		if (db != null) {
			database = db;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Salva o Stage do menuController
	 * @param funcionario
	 */
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
