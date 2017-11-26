package sistema;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import viewTableModel.TableViewProduto;

public class Produto {
	
	public void adicionaProduto(int id, String nome, float valor, int qtde) {
		dao.Produto cadastro = new dao.Produto();
		
		cadastro.inserir(id, nome, valor, qtde);
	}

	public void getProduto() {
		
	}
	
	public ObservableList<TableViewProduto> getAllProduto() throws SQLException {
		dao.Produto produtoBD = new dao.Produto();
		ResultSet result = produtoBD.getAll();
		ObservableList<TableViewProduto> ol = ObservableList<TableViewProduto>();
		int i = 0;
		
		while(result.next()) {			
			ol.set(i, new TableViewProduto(result.getInt(1), result.getString(2),
					result.getFloat(3),	result.getInt(4)));
			i++;
		}
		
		return ol;
	}

	public void editaProduto() {

	}

}
