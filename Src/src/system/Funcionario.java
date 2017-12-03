package system;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Stages;

public class Funcionario {
	private int id;
	
	public Funcionario(int id) {
		this.id = id;
	}

	public String getNome() throws Exception {
		ResultSet result = dao.Funcionario.get(id);
		if (result.next())
			return result.getString("nome");

		throw new Exception("Funcionário não encontrado");
	}

	public int getId() throws Exception {
		ResultSet result = dao.Funcionario.get(id);
		if (result.next())
			return result.getInt("funcionarioId");

		throw new Exception("Funcionário não encontrado");
	}

	public int getFuncao() throws Exception {
		ResultSet result = dao.Funcionario.get(id);
		if (result.next())
			return result.getInt("funcaoId");

		throw new Exception("Funcionário não encontrado");
	}

	public boolean getGarcom() throws Exception {
		ResultSet result = dao.Funcionario.get(id);
		if (result.next())
			return result.getBoolean("garcom");

		throw new Exception("Funcionário não encontrado");
	}

	public static boolean getGarcomById(int id) throws Exception {
		ResultSet result = dao.Funcionario.get(id);
		if (result.next())
			return result.getBoolean("garcom");

		throw new Exception("Funcionário não encontrado");
	}

	public static void criaUsuario(int func, String nome, String usr, String pass, boolean garcom) throws Exception {
		dao.Funcionario.inserir(func, nome, usr, pass, garcom);
	}

	public static boolean login(String usr, String pass) throws Exception {
		return dao.Funcionario.login(usr, pass);
	}

	public static ObservableList<TableViewFuncionario> getAllFuncionario() throws Exception {
		ResultSet result = dao.Funcionario.getAll();
		ObservableList<TableViewFuncionario> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(new TableViewFuncionario(result.getInt("funcionarioId"), result.getString("nome"), result.getString("username"), result.getInt("funcaoId"), result.getBoolean("garcom")));
		}
	
		return ol;
	}
	
	public static boolean editaFuncionario(int id, String nome, String login, String password, boolean garcom) throws Exception {
		return dao.Funcionario.update(id, nome, login, password, garcom);
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

	public static ObservableList<String> getFuncionariosNome() throws Exception {
		ResultSet result = dao.Funcionario.getAllGarcom();
		ObservableList<String> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(result.getInt("funcionarioId") + " - " + result.getString("nome"));
		}
	
		return ol;
	}

	public static class TableViewFuncionario {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;
    	private final SimpleStringProperty login;
    	private final SimpleStringProperty cargo;
    	private final SimpleBooleanProperty garcom;

    	public TableViewFuncionario(int id, String nome, String loginname, int cargo, boolean garcom) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    		this.login = new SimpleStringProperty(loginname);
    		this.cargo = new SimpleStringProperty(FuncionarioCargoEnum.get(cargo).toString());
    		this.garcom = new SimpleBooleanProperty(garcom);
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

    	public boolean getGarcom() {
    		return garcom.get();
    	}
    }

	public enum FuncionarioCargoEnum {
		Gerente(1), Usuário(2);

		private static final Map<Integer, FuncionarioCargoEnum> lookup = new HashMap<Integer, FuncionarioCargoEnum>();
	    static {
	        for (FuncionarioCargoEnum d : FuncionarioCargoEnum.values()) {
	            lookup.put(d.getValor(), d);
	        }
	    }

		private final int value;
		FuncionarioCargoEnum(int value) {
			this.value = value;
		}

		public int getValor() {
			return value;
		}

		public static FuncionarioCargoEnum get(int id) {
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
