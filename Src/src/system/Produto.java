package system;

import java.sql.ResultSet;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto {
	
	public static boolean adicionaProduto(int id, String nome, float valor) throws Exception {		
		return dao.Produto.inserir(id, nome, valor);
	}
	
	public static ResultSet getProduto(int id) throws Exception {		
		return dao.Produto.get(id);
	}

	public static ObservableList<String> getProdutoNome() throws Exception {
		ResultSet result = dao.Produto.getAll();
		ObservableList<String> ol = FXCollections.observableArrayList();

		while (result.next()) {
			ol.add(result.getInt(1) + " - " + result.getString(2));
		}

		return ol;
	}
	
	public static boolean verificaExistenciaProduto(int id) throws Exception {
		return dao.Produto.verificaExistenciaProduto(id);
	}
	
	public static ObservableList<TableViewProduto> getAllProduto() throws Exception {
		ResultSet result = dao.Produto.getAll();
		ObservableList<TableViewProduto> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewProduto(result.getInt(1), result.getString(2),
					result.getFloat(3)));
		}
		
		return ol;
	}
	
	public static boolean editaProduto(int id, String nome, float valor) throws Exception {
		return dao.Produto.atualizar(id, nome, valor);
	}
	
	public static boolean delete(int id) throws Exception {
		return dao.Produto.delete(id);
	}
	
	//classe interna Modelo para a tableView produto
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
