package system;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Stages;

public class Funcionario {

	private static String nome;
	private static int funcionarioId;
	private static int funcao;
	
	public Funcionario(String nome, int id, int funcao) {
		Funcionario.nome = nome;
		Funcionario.funcionarioId = id;
		Funcionario.funcao = funcao;
	}

	public String getNome() {
		return nome;
	}

	public int getId() {
		return funcionarioId;
	}

	public int getFuncao() {
		return funcao;
	}

	public static void criaUsuario(int func, String nome, String usr, String pass) throws Exception {
		dao.Funcionario.inserir(func, nome, usr, pass);
	}

	public static boolean login(String usr, String pass) throws Exception {
		return dao.Funcionario.login(usr, pass);
	}

	public static ObservableList<TableViewFuncionario> getAllFuncionario() throws Exception {
		ResultSet result = dao.Funcionario.getAll();
		ObservableList<TableViewFuncionario> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(new TableViewFuncionario(result.getInt("funcionarioId"), result.getString("nome"), result.getString("username"), result.getInt("funcaoId")));
		}
	
		return ol;
	}
	
	public static boolean editaFuncionario(int id, String nome, String login, String password) throws Exception {
		return dao.Funcionario.update(id, nome, login, password);
	}
	
	public static boolean removeFuncionario(int id, String cargo) throws Exception {
		dao.Funcionario funcionario = new dao.Funcionario();
		
		if (!cargo.equals("Gerente")) {
			return funcionario.delete(id);
		} else {
			ResultSet result = funcionario.getGerente(id);
			if (result.next()) {				
				return funcionario.delete(id);
			} else {
				throw new Exception("Você não pode remover todos os gerentes!");
			}
		}
	}

	public static String getNomebyId(int id) {
		String nome = "";
		try {
			nome = dao.Funcionario.getNomebyId(id);
			if (nome.isEmpty())
				throw new Exception("Funcionario não encontrado!");
		} catch (Exception e) {
			Stages.novoAlerta(e.getMessage(), "", true);
		}
		return nome;
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
    		this.cargo = new SimpleStringProperty(FuncionarioEnum.get(cargo).toString());
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

	public enum FuncionarioEnum {
		Gerente(1), Usuário(2);

		private static final Map<Integer, FuncionarioEnum> lookup = new HashMap<Integer, FuncionarioEnum>();
	    static {
	        for (FuncionarioEnum d : FuncionarioEnum.values()) {
	            lookup.put(d.getValor(), d);
	        }
	    }

		private final int value;
		FuncionarioEnum(int value) {
			this.value = value;
		}

		public int getValor() {
			return value;
		}

		public static FuncionarioEnum get(int id) {
	        return lookup.get(id);
	    }

		@Override
		public String toString() {
			switch (value) {
				case 1 :
					return "Gerente";
				case 2 :
					return "Usuário";
				default :
					return "Error";
			}
		}
	}
}
