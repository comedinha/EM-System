package system;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Comanda {
	public static ResultSet criaComanda(int id, int funcionarioId) throws Exception {
		return dao.Comanda.novaComanda(id, funcionarioId);
	}

	public static ObservableList<TableViewComandaLista> getAllComanda() throws Exception {
		ResultSet result = dao.Comanda.getAllComandas();
		ObservableList<TableViewComandaLista> ol = FXCollections.observableArrayList();
		
		while (result.next()) {			
			ol.add(new TableViewComandaLista(result.getInt(1), result.getTimestamp(2),
					result.getString(3), result.getFloat(4)));
		}
		
		return ol;
	}

	public static float getValorPagoComanda(int id) throws Exception {
		ResultSet result = dao.Comanda.getValorPagoComanda(id);
		result.next();
		return result.getFloat(1);
	}

	public static int finalizaComanda() {
		return 0;
	}

	public static void addProduto(int comandaId, int produtoId, int qtde) throws Exception {
		dao.Comanda.addProduto(comandaId, produtoId, qtde);
	}

	public static ObservableList<TableViewComandaProduto> getAllProduto(int id) throws Exception {		
		ResultSet result = dao.Comanda.getAllProduto(id);
		ObservableList<TableViewComandaProduto> ol = FXCollections.observableArrayList();
		Timestamp dataComanda = dao.Comanda.getDataComanda(id);
		
		while (result.next()) {
			if(dataComanda.after(dao.Comanda.getDataProduto(result.getInt(1)))) {
				ol.add(new TableViewComandaProduto(result.getInt(1), result.getString(2), 
						result.getInt(3), result.getFloat(4), result.getFloat(5)));
			} else {
				ol.add(new TableViewComandaProduto(result.getInt(1), result.getString(2), 
						result.getInt(3), dao.Comanda.getPrecoVelho(result.getInt(1)), 
						result.getFloat(5)));
			}
		}
		return ol;
	}

	public static boolean existeNaComanda(int idProduto, int idComanda) throws Exception {
		return dao.Comanda.existeNaComanda(idProduto, idComanda);
	}

	public static void updateQtde(int idProduto, int idComanda, int qtde) throws Exception {
		dao.Comanda.updateQtde(idProduto, idComanda, qtde);
	}

	public static void removeProdutoComanda(int idProduto, int idComanda) throws Exception {
		dao.Comanda.removeProdutoComanda(idProduto, idComanda);
	}

	public static int getQtdePrdoutoComanda(int idProduto, int idComanda) throws Exception {
		ResultSet result = dao.Comanda.getQtdeProdutoComanda(idProduto, idComanda);
		result.next();
		return result.getInt(1);
	}

	public static boolean updateComanda(int id, String mesa, int funcionario, boolean pago) throws Exception {
		return dao.Comanda.update(id, mesa, funcionario, pago);
	}

	public static ResultSet getComanda(int id, Timestamp data) throws Exception {
		return dao.Comanda.getComanda(id, data);
	}

	//Classe Interna modelo Comanda-Prdoutos
	public static class TableViewComandaProduto {
    	private final SimpleIntegerProperty id;
    	private final SimpleIntegerProperty qtde;
    	private final SimpleStringProperty nome;
    	private final SimpleFloatProperty valorIndividual;
    	private final SimpleFloatProperty valorTotal;
    	private final SimpleFloatProperty valorPago;
		
    	public TableViewComandaProduto(int id, String nome, int qtde, float valorIndividual,
    			float valorPago) {
			this.id = new SimpleIntegerProperty(id);
			this.qtde = new SimpleIntegerProperty(qtde);
			this.nome = new SimpleStringProperty(nome);
			this.valorIndividual = new SimpleFloatProperty(valorIndividual);
			this.valorTotal = new SimpleFloatProperty(getQtde() * getValorIndividual());
			this.valorPago = new SimpleFloatProperty(valorPago);
		}

		public int getId() {
			return id.get();
		}

		public int getQtde() {
			return qtde.get();
		}

		public String getNome() {
			return nome.get();
		}

		public float getValorIndividual() {
			return valorIndividual.get();
		}

		public float getValorTotal() {
			return valorTotal.get();
		}

		public float getValorPago() {
			return valorPago.get();
		}
    }
	
	//Classe Interna Modelo Lista de Comanda
	public static class TableViewComandaLista {
		private final SimpleIntegerProperty id;
		private final SimpleStringProperty mesa;
		private final SimpleStringProperty data;
		private final SimpleFloatProperty valor;
		private final SimpleStringProperty timestamp;

		public TableViewComandaLista(int id, Timestamp data, String mesa, float valor) {
			this.id = new SimpleIntegerProperty(id);
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.data = new SimpleStringProperty(f.format(data));
			this.mesa = new SimpleStringProperty(mesa);
			this.valor = new SimpleFloatProperty(valor);
			this.timestamp = new SimpleStringProperty(data.toString());
		}
		
		public int getId() {
			return id.get();
		}

		public String getMesa() {
			return mesa.get();
		}

		public String getData() {
			return data.get();
		}

		public float getValor() {
			return valor.get();
		}

		public Timestamp getTimeStamp() {
			return Timestamp.valueOf(timestamp.get());
		}
	}
}