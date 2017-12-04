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
import util.MeioPagamentoEnum;

/**
 * Possui toda parte lógica referente a Comanda
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 * @version 1.0
 */
public class Pagamento {
	/**
	 * Adiciona pagamento a uma comanda
	 * @param id ID da comanda
	 * @param time Data do pagamento
	 * @param valor Valor do pagamento
	 * @param funcionarioId ID do funcionario
	 * @param formaPagamento Forma do pagamento
	 * @param desconto Valor do desconto
	 * @throws Exception
	 */
	public static void pagamentoComanda(int id, Timestamp time, Float valor, int funcionarioId, int formaPagamento, boolean desconto) throws Exception {
		dao.Pagamento.pagamentoComanda(id, time, valor, funcionarioId, formaPagamento, desconto);
	}
	
	/**
	 * Adiciona um pagamento a um produto
	 * @param id ID do produto
	 * @param comandaId ID da comanda
	 * @param comandaTime Data da comanda
	 * @param valor Valor a ser pago
	 * @param formaPagamento Forma de pagamento
	 * @param funcionarioId ID do funcionario
	 * @throws Exception
	 */
	public static void pagamentoProduto(int id, int comandaId, Timestamp comandaTime, Float valor, int formaPagamento, int funcionarioId) throws Exception {		
		dao.Pagamento.pagamentoProduto(id, comandaId, comandaTime, valor, funcionarioId, formaPagamento);
	}
	
	/**
	 * Remove um pagamento do BD
	 * @param id ID do Pagamento
	 * @return Retorna se ocorreu tudo bem
	 * @throws Exception
	 */
	public static boolean removePagamento(int id) throws Exception {
		return dao.Pagamento.removePagamento(id);
	}
	
	/**
	 * Calcula o valor total do pagamento
	 * @param id ID do pagamento
	 * @param time Data da comanda
	 * @param desconto Valor do desconto
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static float getAllValor(int id, Timestamp time, boolean desconto) throws Exception {
		ResultSet result = dao.Pagamento.getAllPagamentoDesconto(id, time, desconto);
		float valor = 0;
		while (result.next())
			valor += result.getFloat("valor");

		return valor;
	}
	
	/**
	 * Pesquisa todos os pagamentos do sistema
	 * @param id ID do pagamento
	 * @param time Data da comanda
	 * @param desconto Valor do desconto
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static ObservableList<TableViewPagamento> getAllPagamento(int id, Timestamp data) throws Exception {
		ResultSet result = dao.Pagamento.getAllPagamento(id, data);
		ObservableList<TableViewPagamento> ol = FXCollections.observableArrayList();

		while(result.next()) {
			ol.add(new TableViewPagamento(result.getInt(1), result.getString(6), result.getInt(4), result.getInt(5), result.getTimestamp(3), result.getFloat(2)));
		}

		return ol;
	}
	
	/**
	 * Classe interna, que é utilizada como referencia para a TableView que lista os pagamentos 
	 * efetuados.
	 * @author Bruno Carvalho, Luiz Eduardo, Mateus Machado
	 * @version 1.0
	 */
	public static class TableViewPagamento {
    	private final SimpleIntegerProperty id;
    	private final SimpleStringProperty descricao;
    	private final SimpleStringProperty funcionario;
    	private final SimpleStringProperty pagamento;
    	private final SimpleStringProperty data;
    	private final SimpleFloatProperty valor;

    	public TableViewPagamento(int id, String descricao, int funcionario, int pagamento, Timestamp data, float valor) {
    		this.id = new SimpleIntegerProperty(id);
    		this.descricao = new SimpleStringProperty(descricao);
    		this.funcionario = new SimpleStringProperty(Funcionario.getNomebyId(funcionario));
    		this.pagamento = new SimpleStringProperty(MeioPagamentoEnum.get(pagamento).toString());
    		DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			this.data = new SimpleStringProperty(f.format(data));
    		this.valor = new SimpleFloatProperty(valor);
    	}

    	public int getId() {
    		return id.get();
    	}

    	public String getDescricao() {
    		return descricao.get();
    	}

    	public String getFuncionario() {
    		return funcionario.get();
    	}

    	public String getPagamento() {
    		return pagamento.get();
    	}

    	public String getData() {
    		return data.get();
    	}

    	public float getValor() {
    		return valor.get();
    	}
    }
}
