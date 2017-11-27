package system;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto {
	
	public static boolean adicionaProduto(int id, String nome, float valor) throws Exception {
		dao.Produto produto = new dao.Produto();
		
		return produto.inserir(id, nome, valor);
	}

	public void getProduto() {
		
	}
	
	public static ObservableList<TableViewProduto> getAllProduto() throws SQLException {
		ResultSet result = new dao.Produto().getAll();
		ObservableList<TableViewProduto> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(new TableViewProduto(result.getInt(1), result.getString(2),
					result.getFloat(3)));
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
