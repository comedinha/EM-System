package system;

import java.sql.ResultSet;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Possui toda parte lógica referente a Produto
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Produto {
	/**
	 * Insere um novo produto
	 * @param id ID do produto
	 * @param nome Nome do produto
	 * @param valor Valor do produto
	 * @return Retorna se ocorreu tudo bem
	 * @throws Exception
	 */
	public static boolean adicionaProduto(int id, String nome, float valor) throws Exception {		
		return dao.Produto.insereProduto(id, nome, valor);
	}
	
	/**
	 * Pesquisa por um determinada produto apartir de seu ID
	 * @param id ID do produto
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static ResultSet getProduto(int id) throws Exception {		
		return dao.Produto.getProduto(id);
	}
	
	/**
	 * Pega todos os produtos cadastrados no sistema, e que estão disponíveis 
	 * @return Retorna o resultado da pesquisa
	 * @throws Exception
	 */
	public static ObservableList<String> getProdutoNome() throws Exception {
		ResultSet result = dao.Produto.getAllProduto();
		ObservableList<String> ol = FXCollections.observableArrayList();

		while (result.next()) {
			ol.add(result.getInt(1) + " - " + result.getString(2));
		}

		return ol;
	}
	
	/**
	 * Verefica se um produto existe no sistema
	 * @param id ID do produto
	 * @return Se existir, retorna True, caso contrario, False
	 * @throws Exception
	 */
	public static ResultSet verificaExistenciaProduto(int id) throws Exception {
		return dao.Produto.verificaExistenciaProduto(id);
	}
	
	/**
	 * Pega todos os produtos cadastrados no sistema, e que estão disponíveis 
	 * @return Retorna o resultado da pesquisa
	 * @throws Exception
	 */
	public static ObservableList<TableViewProduto> getAllProduto() throws Exception {
		ResultSet result = dao.Produto.getAllProduto();
		ObservableList<TableViewProduto> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewProduto(result.getInt(1), result.getString(2),
					result.getFloat(3)));
		}		
		return ol;
	}
	
	/**
	 * Atualiza as informações do produto
	 * @param id ID do produto
	 * @param nome Nome do Produto
	 * @param valor Valor do produto
	 * @return Retorna se ocorreu tudo bem
	 * @throws Exception
	 */
	public static boolean editaProduto(int id, String nome, float valor) throws Exception {
		return dao.Produto.updateProduto(id, nome, valor);
	}
	
	/**
	 * Remove um produto do sistema
	 * @param id ID do produto
	 * @return
	 * @throws Exception
	 */
	public static boolean delete(int id) throws Exception {
		return dao.Produto.deleteProduto(id);
	}

	/**
	 * Reativa um produto no sistema
	 * @param id ID do produto
	 * @param nome nome do produto
	 * @param valor valor do produto
	 * @return
	 * @throws Exception
	 */
	public static void reativaProduto(int id, String nome, float valor) throws Exception {
		dao.Produto.reativaProduto(id, nome, valor);
	}

	/**
	 * Classe interna, que é utilizada como referencia para a TableView que lista os produtos 
	 * cadastrados no sistema
	 * @author Bruno Carvalho, Luiz Eduardo, Mateus Machado
	 * @version 1.0
	 */
    public static class TableViewProduto {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;
    	private final SimpleFloatProperty valor;    	

    	public TableViewProduto(int id, String nome, float valor) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    		this.valor = new SimpleFloatProperty(valor);
    	}

    	public int getId() {
    		return id.get();
    	}
    	public String getNome() {
    		return nome.get();
    	}
    	public float getValor() {
    		return valor.get();
    	}   		
    }
}
