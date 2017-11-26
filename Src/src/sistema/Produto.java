package sistema;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.MenuController.TableViewProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Produto {
	
	public void adicionaProduto(int id, String nome, float valor, int qtde) throws Exception {
		dao.Produto cadastro = new dao.Produto();
		
		cadastro.inserir(id, nome, valor, qtde);
	}

	public void getProduto() {
		
	}
	
	public ObservableList<TableViewProduto> getAllProduto() throws SQLException {
		dao.Produto produtoBD = new dao.Produto();
		ResultSet result = produtoBD.getAll();
		ObservableList<TableViewProduto> ol = FXCollections.observableArrayList();
		
		while(result.next()) {			
			ol.add(new TableViewProduto(result.getInt(1), result.getString(2),
					result.getFloat(3),	result.getInt(4)));
		}
	
		return ol;
	}
	
	public void editaProduto() {

	}

}
