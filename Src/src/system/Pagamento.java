package system;

import java.sql.Date;
import java.sql.ResultSet;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pagamento {
	
	public static boolean adicionaProduto(float valor, int comanda, Date dataComanda) throws Exception {		
		return dao.Pagamento.inserir(valor, comanda, dataComanda);
	}
	
	public static ResultSet getProduto(int id) throws Exception {		
		return dao.Pagamento.get(id);
	}
	
	public static ObservableList<TableViewPagamento> getAllPagamento() throws Exception {
		ResultSet result = dao.Pagamento.getAll();
		ObservableList<TableViewPagamento> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewPagamento(result.getInt(1), result.getFloat(2),
					result.getDate(3), result.getInt(4), result.getInt(5), result.getDate(6)));
		}
		
		return ol;
	}
	
	//classe interna Modelo para a tableView produto
    public static class TableViewPagamento {
    	private final SimpleIntegerProperty id;
    	private final SimpleFloatProperty valor;
    	private final SimpleStringProperty data;
    	private final SimpleStringProperty funcionario;
    	private final SimpleIntegerProperty comanda;
    	private final SimpleStringProperty comandaData;

    	public TableViewPagamento(int id, float valor, Date data, int funcionario, int comanda, Date comandaData) {
    		this.id = new SimpleIntegerProperty(id);
    		this.valor = new SimpleFloatProperty(valor);
    		this.data = new SimpleStringProperty(data.toString());
    		this.funcionario = new SimpleStringProperty(Funcionario.getNomebyId(funcionario));
    		this.comanda = new SimpleIntegerProperty(comanda);
    		this.comandaData = new SimpleStringProperty(comandaData.toString());
    	}

    	public int getId() {
    		return id.get();
    	}

    	public float getValor() {
    		return valor.get();
    	}

    	public String getData() {
    		return data.get();
    	}

    	public String getFuncionario() {
    		return funcionario.get();
    	}

    	public int getComanda() {
    		return comanda.get();
    	}

    	public String getComandaData() {
    		return comandaData.get();
    	}
    }

}
