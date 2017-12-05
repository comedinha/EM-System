package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import util.Valores;

/**
 * Classe que todas as operações de CRUD referente a Pagamento.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 *
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
		String sql = "INSERT INTO pagamento (comandaId, comandaData, valor, funcionarioId, formaPagamento) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.setTimestamp(2, time);
		ps.setFloat(3, valor);
		ps.setInt(4, funcionarioId);
		ps.setInt(5, formaPagamento);
		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		if (!getId.next())
			throw new Exception("Algum erro ocorreu ao adicionar o pagamento.");

		int pagamentoId = getId.getInt("pagamentoId");

		sql = "INSERT INTO pagamentoComanda (pagamentoId, desconto) VALUES (?, ?)";

		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, pagamentoId);
		ps.setBoolean(2, desconto);
		ps.executeUpdate();
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
		String sql = "INSERT INTO pagamento (comandaId, comandaData, valor, funcionarioId, formaPagamento) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, comandaId);
		ps.setTimestamp(2, comandaTime);
		ps.setFloat(3, valor);
		ps.setInt(4, funcionarioId);
		ps.setInt(5, formaPagamento);
		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		if (!getId.next())
			throw new Exception("Algum erro ocorreu ao adicionar o pagamento.");

		int pagamentoId = getId.getInt("pagamentoId");

		sql = "INSERT INTO pagamentoProduto (pagamentoId, produtoId) VALUES (?, ?)";

		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, pagamentoId);
		ps.setInt(2, id);
		ps.executeUpdate();
	}
	
	/**
	 * Remove um pagamento do BD
	 * @param id ID do Pagamento
	 * @return Retorna se ocorreu tudo bem
	 * @throws Exception
	 */
	public static boolean removePagamento(int id) throws Exception {
		String sql = "DELETE FROM pagamento WHERE pagamentoId = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);

		ps.executeUpdate();	
		return true;
	}
	
	/**
	 * Pesquisa todos os pagamentos do sistema separando os descontos de produtos
	 * @param id ID do pagamento
	 * @param time Data da comanda
	 * @param desconto Valor do desconto
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static ResultSet getAllPagamentoDesconto(int id, Timestamp time, boolean desconto) throws Exception {
		String sql = "SELECT p.pagamentoId, p.valor, p.data, p.funcionarioId, p.formaPagamento, CASE WHEN pc.desconto THEN 'Desconto' ELSE 'Pagamento' END AS tipo FROM pagamento p JOIN pagamentoComanda pc ON p.pagamentoId = pc.pagamentoId WHERE p.comandaId = ? AND p.comandaData = ? AND pc.desconto = ?"
				+ " UNION SELECT p.pagamentoId, p.valor, p.data, p.funcionarioId, p.formaPagamento, pd.nome AS tipo FROM pagamento p JOIN pagamentoProduto pp ON p.pagamentoId = pp.pagamentoId JOIN produto pd ON pp.produtoId = pd.produtoId WHERE p.comandaId = ? AND p.comandaData = ?";
		if (desconto)
			sql = "SELECT p.pagamentoId, p.valor, p.data, p.funcionarioId, p.formaPagamento, pc.desconto FROM pagamento p JOIN pagamentoComanda pc ON p.pagamentoId = pc.pagamentoId WHERE p.comandaId = ? AND p.comandaData = ? AND pc.desconto = ?";

		PreparedStatement p = Valores.getConnection().prepareStatement(sql);
		p.setInt(1, id);
		p.setTimestamp(2, time);
		p.setBoolean(3, desconto);

		if (!desconto) {
			p.setInt(4, id);
			p.setTimestamp(5, time);
		}

		ResultSet result = p.executeQuery();
		return result;
	}

	/**
	 * Pesquisa todos os pagamentos do sistema
	 * @param id ID do pagamento
	 * @param time Data da comanda
	 * @return Retorna a pesquisa
	 * @throws Exception
	 */
	public static ResultSet getAllPagamento(int id, Timestamp time) throws Exception {
		String sql = "SELECT p.pagamentoId, p.valor, p.data, p.funcionarioId, p.formaPagamento, CASE WHEN pc.desconto THEN 'Desconto' ELSE 'Pagamento' END AS tipo FROM pagamento p JOIN pagamentoComanda pc ON p.pagamentoId = pc.pagamentoId WHERE p.comandaId = ? AND p.comandaData = ?"
				+ " UNION SELECT p.pagamentoId, p.valor, p.data, p.funcionarioId, p.formaPagamento, pd.nome AS tipo FROM pagamento p JOIN pagamentoProduto pp ON p.pagamentoId = pp.pagamentoId JOIN produto pd ON pp.produtoId = pd.produtoId WHERE p.comandaId = ? AND p.comandaData = ?";

		PreparedStatement p = Valores.getConnection().prepareStatement(sql);
		p.setInt(1, id);
		p.setTimestamp(2, time);
		p.setInt(3, id);
		p.setTimestamp(4, time);

		ResultSet result = p.executeQuery();
		return result;
	}
}
