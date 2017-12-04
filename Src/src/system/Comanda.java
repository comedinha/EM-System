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

/**
 * Possui toda parte lógica referente a Comanda
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Comanda {
	/**
	 * Cria uma nova comanda
	 * @param id ID da nova comanda
	 * @param funcionarioId ID do funcionario que está criando a comanda
	 * @return Retorna uma consulta com o ID da comanda
	 * @throws Exception
	 */
	public static ResultSet criaComanda(int id, int funcionarioId) throws Exception {
		return dao.Comanda.novaComanda(id, funcionarioId);
	}
	
	/**
	 * Retorna uma lista com as comandas que já foram pagas em determinado período de tempo.
	 * @param dataDe Data inicial da pesquisa
	 * @param dataAte Data final da pesquisa
	 * @return Retorna uma lista com as comandas finalizadas
	 * @throws Exception
	 */
	public static ObservableList<TableViewComandaPaga> getAllComandaPaga(Date dataDe, Date dataAte) throws Exception {
		ResultSet result = dao.Comanda.getAllComandasPagas(dataDe, dataAte);
		ObservableList<TableViewComandaPaga> ol = FXCollections.observableArrayList();

		while (result.next()) {
			ol.add(new TableViewComandaPaga(result.getInt(1), result.getTimestamp(2),
					result.getTimestamp(4), result.getInt(3), dao.Comanda.getPrecoComanda(result.getInt(1), result.getTimestamp(2))));
		}

		return ol;
	}
	
	/**
	 * Cria uma lista com todas as comandas no DB
	 * @return Lista com as comandas
	 * @throws Exception
	 */
	public static ObservableList<TableViewComandaLista> getAllComanda() throws Exception {
		ResultSet result = dao.Comanda.getAllComandas();
		ObservableList<TableViewComandaLista> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewComandaLista(result.getInt("comandaId"), result.getTimestamp("data"),
					result.getString("mesa"), result.getInt("funcionarioId"), dao.Comanda.getPrecoComanda(result.getInt("comandaId"), result.getTimestamp("data"))));
		}		
		return ol;
	}
	
	/**
	 * Finaliza a comanda
	 * @return 
	 */
	public static int finalizaComanda() {
		return 0;
	}
	
	/**Adiciona produto em uma comanda
	 * @param comandaId ID da comanda
	 * @param data Data da criação da comanda
	 * @param produtoId ID do Produto
	 * @param qtde Quantidade do produto que vai ser adicionado
	 * @throws Exception
	 */
	public static void addProduto(int comandaId, Timestamp data, int produtoId, int qtde) throws Exception {
		dao.Comanda.addProduto(comandaId, data, produtoId, qtde);
	}
	
	/**
	 * Cria uma lista de todos os produtos de uma determinada comanda
	 * @param id ID da comanda
	 * @param dataComanda Data da criação da comanda 
	 * @return Retorna a lista com os produtos da comanda
	 * @throws Exception
	 */
	public static ObservableList<TableViewComandaProduto> getAllProduto(int id, Timestamp dataComanda) throws Exception {		
		ResultSet result = dao.Comanda.getAllProduto(id, dataComanda);
		ObservableList<TableViewComandaProduto> ol = FXCollections.observableArrayList();
		
		while (result.next()) {
			ol.add(new TableViewComandaProduto(result.getInt(1), result.getString(2), 
					result.getInt(3), result.getFloat(4), result.getFloat(5)));
		}
		return ol;
	}
	
	/**
	 * Verifica a existencia de um produto na comanda.
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @return	Retorna True caso exista, ou False caso não exista
	 * @throws Exception
	 */
	public static boolean existeNaComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		return dao.Comanda.existeNaComanda(idComanda, data, idProduto);
	}
	
	/**
	 * Atualiza a quantidade de um produto na comanda.
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @param qtde Nova quantidade de produtos
	 * @throws Exception
	 */
	public static void updateQtde(int idComanda, Timestamp data, int idProduto, int qtde) throws Exception {
		dao.Comanda.updateQtde(idComanda, data, idProduto, qtde);
	}
	
	/**
	 * Remove um produto da comanda
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @throws Exception
	 */
	public static void removeProdutoComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		dao.Comanda.removeProdutoComanda(idComanda, data, idProduto);
	}
	
	/**
	 * Pega a quantidade de um determinado produto existente em uma comanda 
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @return Retorna a quantidade de um produto
	 * @throws Exception
	 */
	public static int getQtdePrdoutoComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		ResultSet result = dao.Comanda.getQtdeProdutoComanda(idComanda, data, idProduto);
		result.next();
		return result.getInt(1);
	}
	
	/**
	 * Atualiza alguns campos da comanda.
	 * @param id ID da comanda
	 * @param data Data da criação da comanda
	 * @param mesa Nome da mesa
	 * @param funcionario ID do funcionario
	 * @param pago Valor pago
	 * @return Retorna true caso de certo o update, e flase caso contrario 
	 * @throws Exception
	 */
	public static boolean updateComanda(int id, Timestamp data, String mesa, int funcionario, boolean pago) throws Exception {
		return dao.Comanda.updateComanda(id, data, mesa, funcionario, pago);
	}
	
	/**
	 * Pega uma determinada comanda do BD e retorna para o sistema
	 * @param id ID da comanda
	 * @param data Data da criação da comanda
	 * @return Retorna a comanda desejada
	 * @throws Exception
	 */
	public static ResultSet getComanda(int id, Timestamp data) throws Exception {
		return dao.Comanda.getComanda(id, data);
	}

	/**
	 * Classe interna, que é utilizada como referencia para a TableView que lista os produtos de
	 * uma comanda.
	 * @author Bruno Carvalho, Luiz Eduardo, Mateus Machado
	 * @version 1.0
	 */
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
	
	/**
	 * Classe interna, que é utilizada como referencia para a TableView que lista todas as 
	 * comandas, ainda não pagas.
	 * @author Bruno Carvalho, Luiz Eduardo, Mateus Machado
	 * @version 1.0
	 */
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
	
	/**
	 * Classe interna, que é utilizada como referencia para a TableView que lista todas as 
	 * comandas já pagas.
	 * @author Bruno Carvalho, Luiz Eduardo, Mateus Machado
	 * @version 1.0
	 */
	public static class TableViewComandaPaga {
		private final SimpleIntegerProperty id;
		private final SimpleStringProperty data;
		private final SimpleStringProperty funcionario;
		private final SimpleStringProperty permanencia;
		private final SimpleFloatProperty valor;
		private final SimpleStringProperty timestamp;

		public TableViewComandaPaga(int id, Timestamp data, Timestamp permanencia, int funcionario, float valor) {
			this.id = new SimpleIntegerProperty(id);
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.data = new SimpleStringProperty(f.format(data));
			f = new SimpleDateFormat("mm:ss");
			this.permanencia = new SimpleStringProperty(f.format(permanencia.getTime() - data.getTime()));
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

		public String getPermanencia() {
			return permanencia.get();
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