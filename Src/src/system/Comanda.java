package system;

import java.sql.Date;
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

	public static ObservableList<TableViewComandaLista> getAllComandaPaga(Date dataDe, Date dataAte) throws Exception {
		ResultSet result = dao.Comanda.getAllComandasPagas(dataDe, dataAte);
		ObservableList<TableViewComandaLista> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewComandaLista(result.getInt("comandaId"), result.getTimestamp("data"),
					result.getString("mesa"), result.getInt("funcionarioId"), dao.Comanda.getPrecoComanda(result.getInt("comandaId"), result.getTimestamp("data"))));
		}
		
		return ol;
	}

	public static ObservableList<TableViewComandaLista> getAllComanda() throws Exception {
		ResultSet result = dao.Comanda.getAllComandas();
		ObservableList<TableViewComandaLista> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewComandaLista(result.getInt("comandaId"), result.getTimestamp("data"),
					result.getString("mesa"), result.getInt("funcionarioId"), dao.Comanda.getPrecoComanda(result.getInt("comandaId"), result.getTimestamp("data"))));
		}
		
		return ol;
	}

	public static int finalizaComanda() {
		return 0;
	}

	public static void addProduto(int comandaId, Timestamp data, int produtoId, int qtde) throws Exception {
		dao.Comanda.addProduto(comandaId, data, produtoId, qtde);
	}

	public static ObservableList<TableViewComandaProduto> getAllProduto(int id, Timestamp dataComanda) throws Exception {		
		ResultSet result = dao.Comanda.getAllProduto(id, dataComanda);
		ObservableList<TableViewComandaProduto> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewComandaProduto(result.getInt(1), result.getString(2), 
					result.getInt(3), result.getFloat(4), result.getFloat(5)));
		}
		return ol;
	}

	public static boolean existeNaComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		return dao.Comanda.existeNaComanda(idComanda, data, idProduto);
	}

	public static void updateQtde(int idComanda, Timestamp data, int idProduto, int qtde) throws Exception {
		dao.Comanda.updateQtde(idComanda, data, idProduto, qtde);
	}

	public static void removeProdutoComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		dao.Comanda.removeProdutoComanda(idComanda, data, idProduto);
	}

	public static int getQtdePrdoutoComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		ResultSet result = dao.Comanda.getQtdeProdutoComanda(idComanda, data, idProduto);
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
		private final SimpleStringProperty data;
		private final SimpleStringProperty mesa;
		private final SimpleStringProperty funcionario;
		private final SimpleFloatProperty valor;
		private final SimpleStringProperty timestamp;

		public TableViewComandaLista(int id, Timestamp data, String mesa, int funcionario, float valor) {
			this.id = new SimpleIntegerProperty(id);
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.data = new SimpleStringProperty(f.format(data));
			this.mesa = new SimpleStringProperty(mesa);
			this.funcionario = new SimpleStringProperty(Funcionario.getNomebyId(funcionario));
			this.valor = new SimpleFloatProperty(valor);
			this.timestamp = new SimpleStringProperty(data.toString());
		}
		
		public int getId() {
			return id.get();
		}

		public String getData() {
			return data.get();
		}

		public String getMesa() {
			return mesa.get();
		}

		public String getFuncionario() {
			return funcionario.get();
		}

		public float getValor() {
			return valor.get();
		}

		public Timestamp getTimeStamp() {
			return Timestamp.valueOf(timestamp.get());
		}
	}
}