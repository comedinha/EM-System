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
			ol.add(new TableViewFuncionario(result.getInt("funcionarioId"), result.getString("nome")));
		}
	
		return ol;
	}

	public static class TableViewFuncionario {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;

    	public TableViewFuncionario(int id, String nome) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    	}

    	public int getId() {
    		return id.get();
    	}

    	public String getNome() {
    		return nome.get();
    	}		
    }
}
