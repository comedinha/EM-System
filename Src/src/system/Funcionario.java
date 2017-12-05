package system;

import java.sql.ResultSet;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.FuncionarioEnum;
import util.Stages;

/**
 * Possui toda parte lógica referente a Comanda
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Funcionario {
	private int id;
	
	public Funcionario(int id) {
		this.id = id;
	}
	
	public String getNome() throws Exception {
		ResultSet result = dao.Funcionario.getFuncionario(id);
		if (result.next())
			return result.getString("nome");

		throw new Exception("Funcionário não encontrado");
	}

	public int getId() throws Exception {
		ResultSet result = dao.Funcionario.getFuncionario(id);
		if (result.next())
			return result.getInt("funcionarioId");

		throw new Exception("Funcionário não encontrado");
	}

	public int getFuncao() throws Exception {
		ResultSet result = dao.Funcionario.getFuncionario(id);
		if (result.next())
			return result.getInt("funcaoId");

		throw new Exception("Funcionário não encontrado");
	}

	public boolean getGarcom() throws Exception {
		ResultSet result = dao.Funcionario.getFuncionario(id);
		if (result.next())
			return result.getBoolean("garcom");

		throw new Exception("Funcionário não encontrado");
	}

	public static boolean getGarcomById(int id) throws Exception {
		ResultSet result = dao.Funcionario.getFuncionario(id);
		if (result.next())
			return result.getBoolean("garcom");

		throw new Exception("Funcionário não encontrado");
	}
	
	/**
	 * Adiciona um novo funcionario ao sistema
	 * @param func Função do funcionario
	 * @param nome Nome do funcionario
	 * @param usr Nome de usuario do funcionario
	 * @param pass Senha
	 * @param garcom Se esse funcionario vai ser garçom ou não
	 * @throws Exception
	 */
	public static void criaUsuario(int func, String nome, String usr, String pass, boolean garcom) throws Exception {
		dao.Funcionario.insereFuncionario(func, nome, usr, pass, garcom);
	}
	
	/**
	 * Verifica se o nome de usuario existe no sistema, e se a senha está correta
	 * @param usr Nome usuario
	 * @param passSenha
	 * @return Retorna True caso esteja tudo certo, e False caso contrario
	 * @throws Exception
	 */
	public static boolean login(String usr, String pass) throws Exception {
		return dao.Funcionario.login(usr, pass);
	}
	
	/**
	 * Pesquisa todos os funcionarios dos sistema
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static ObservableList<TableViewFuncionario> getAllFuncionario() throws Exception {
		ResultSet result = dao.Funcionario.getAllFuncionarios();
		ObservableList<TableViewFuncionario> ol = FXCollections.observableArrayList();
		
		while(result.next()) {
			ol.add(new TableViewFuncionario(result.getInt("funcionarioId"), result.getString("nome"), result.getString("login"), result.getInt("funcaoId"), result.getBoolean("garcom")));
		}
	
		return ol;
	}
	
	/**
	 * Faz atualizações das informações do funcionario
	 * @param id ID do funcionario
	 * @param nome Nome do funcionario
	 * @param login Nome de usuario
	 * @param password Senha
	 * @param garcom Se é garçom
	 * @return Retorna se ocorreu o update corretamente
	 * @throws Exception
	 */
	public static boolean editaFuncionario(int id, String nome, String login, String password, boolean garcom) throws Exception {
		return dao.Funcionario.updateFuncionario(id, nome, login, password, garcom);
	}
	
	/**
	 * Remove um funcionario. Caso seja um gerente tentando remover ele mesmo, ele verifica se há
	 * outro gerente cadastrado no sistema
	 * @param id ID do funcioanrio
	 * @param cargo Cargo do funcionario
	 * @return True ou false caso de certo ou errado
	 * @throws Exception
	 */
	public static boolean removeFuncionario(int id, String cargo) throws Exception {
		dao.Funcionario funcionario = new dao.Funcionario();
		
		if (!cargo.equals("Gerente")) {
			return funcionario.deleteFuncionario(id);
		} else {
			ResultSet result = funcionario.getGerente(id);
			if (result.next()) {				
				return funcionario.deleteFuncionario(id);
			} else {
				throw new Exception("Você não pode remover todos os gerentes!");
			}
		}
	}
	
	/**
	 * Pesquisa o nome de um funcionario apartir de seu ID
	 * @param id ID do funcionario
	 * @return Retorna o nome do funcionario
	 * @throws Exception
	 */
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
	
	/**
	 * Pesquisa todos os garçons cadastrados no sistema
	 * @return
	 * @throws Exception
	 */
	public static ObservableList<String> getFuncionariosNome() throws Exception {
		ResultSet result = dao.Funcionario.getAllGarcom();
		ObservableList<String> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(result.getInt("funcionarioId") + " - " + result.getString("nome"));
		}
	
		return ol;
	}
	
	/**
	 * Classe interna, que é utilizada como referencia para a TableView que lista todos os 
	 * funcionarios.
	 * @author Bruno Carvalho, Luiz Eduardo, Mateus Machado
	 * @version 1.0
	 */
	public static class TableViewFuncionario {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;
    	private final SimpleStringProperty login;
    	private final SimpleStringProperty cargo;
    	private final SimpleStringProperty garcom;
    	private final SimpleBooleanProperty garcomBool;

    	public TableViewFuncionario(int id, String nome, String loginname, int cargo, boolean garcom) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    		this.login = new SimpleStringProperty(loginname);
    		this.cargo = new SimpleStringProperty(FuncionarioEnum.get(cargo).toString());
    		this.garcom = new SimpleStringProperty(garcom ? "Sim" : "Não");
    		this.garcomBool = new SimpleBooleanProperty(garcom);
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

    	public String getGarcomTexto() {
    		return garcom.get();
    	}

    	public boolean getGarcom() {
    		return garcomBool.get();
    	}
    }
}
