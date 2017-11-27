package system;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Funcionario {

	private static String nome;
	private static int funcionarioId;
	private static int funcao;

	public String getNome() {
		return nome;
	}

	public int getId() {
		return funcionarioId;
	}

	public int getFuncao() {
		return funcao;
	}

	public Funcionario(String nome, int id, int funcao) {
		Funcionario.nome = nome;
		Funcionario.funcionarioId = id;
		Funcionario.funcao = funcao;
	}

	public void criaUsuario(int func, String nome, String usr, String pass) throws Exception {
		dao.Funcionario.inserir(1, nome, usr, pass);
	}

	public static boolean login(String usr, String pass) throws Exception {
		return dao.Funcionario.login(usr, pass);
	}

	public static ObservableList<TableViewFuncionario> getAllFuncionario() throws SQLException {
		ResultSet result = new dao.Funcionario().getAll();
		ObservableList<TableViewFuncionario> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(new TableViewFuncionario(result.getInt("funcionarioId"), result.getString("nome"), result.getString("username"), result.getInt("funcaoId")));
		}
	
		return ol;
	}

	public static class TableViewFuncionario {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;
    	private final SimpleStringProperty login;
    	private final SimpleStringProperty cargo;

    	public TableViewFuncionario(int id, String nome, String loginname, int cargo) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    		this.login = new SimpleStringProperty(loginname);
    		if (cargo == 1) {
    			this.cargo = new SimpleStringProperty("Gerente");
    		} else {
    			this.cargo = new SimpleStringProperty("Funcionário");
    		}
    	}

    	public int getId() {
    		return id.get();
    	}

    	public String getNome() {
    		return nome.get();
    	}

    	public String getLogin() {
    		return login.get();
    	}

    	public String getCargo() {
    		return cargo.get();
    	}
    }
}
