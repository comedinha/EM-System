package sistema;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto {
	
	public void adicionaProduto(int id, String nome, float valor, int qtde) {
		dao.Produto cadastro = new dao.Produto();
		
		cadastro.inserir(id, nome, valor, qtde);
	}

	public void getProduto() {
		
	}
	
	public ObservableList<TableViewProduto> getAllProduto() throws SQLException {
		ResultSet result = new dao.Produto().getAll();
		ObservableList<TableViewProduto> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(new TableViewProduto(result.getInt(1), result.getString(2),
					result.getFloat(3),	result.getInt(4)));
		}
	
		return ol;
	}
	
	public void editaProduto() {

	}
	
	//classe interna Modelo para a tableView produto
    public static class TableViewProduto {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty nome;
    	private final SimpleFloatProperty valor;
    	private final SimpleIntegerProperty quantidade;
    	

    	public TableViewProduto(int id, String nome, float valor, int quantidade) {
    		this.id = new SimpleIntegerProperty(id);
    		this.nome = new SimpleStringProperty(nome);
    		this.valor = new SimpleFloatProperty(valor);
    		this.quantidade = new SimpleIntegerProperty(quantidade);
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
    	public int getQuantidade() {
    		return quantidade.get();
    	}    		
    }

}
