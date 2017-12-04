package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import util.Valores;

/**
 * Classe que todas as operações de CRUD referente a Comanda.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class Comanda {
	/**
	 * Cria uma nova comanda no BD.
	 * @param id ID desejado da comanda a ser crida. Caso seja 0, o id será atribuido automaticamente
	 * @param funcionarioId ID do funcionario que está crianco a comanda 
	 * @return retorna o ID da comanda que foi criada
	 * @throws Exception
	 */
	public static ResultSet novaComanda(int id, int funcionarioId) throws Exception {
		String sql = "INSERT INTO comanda (funcionarioId, comandaId) VALUES (?, ?)";
		if (id == 0) {
			sql = "INSERT INTO comanda (funcionarioId) VALUES (?)";
		}

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, funcionarioId);

		if (id != 0)
			ps.setInt(2, id);

		ps.executeUpdate();

		ResultSet getId = ps.getGeneratedKeys();
		getId.next();
		return getId;
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
		String sql = "SELECT * FROM produtoComanda WHERE produtoId = ? AND comandaId = ? AND comandaData = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		ps.setTimestamp(3, data);
		return ps.executeQuery().next();
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
		String sql = "UPDATE produtoComanda SET quantidade = ? WHERE produtoId = ? AND comandaId = ? AND comandaData = ?";
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, qtde);
		ps.setInt(2, idProduto);
		ps.setInt(3, idComanda);
		ps.setTimestamp(4, data);
		ps.executeUpdate();
	}
	
	/**
	 * Pega a quantidade de um determinado produto existente em uma comanda 
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @return Retorna a pesquisa com a quantidade de produto
	 * @throws Exception
	 */
	public static ResultSet getQtdeProdutoComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		String sql = "SELECT quantidade FROM produtoComanda WHERE produtoId = ? AND comandaId = ? AND comandaData = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		ps.setTimestamp(3, data);
		return ps.executeQuery();
	}
	
	/**
	 * Remove um produto da comanda
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @throws Exception
	 */
	public static void removeProdutoComanda(int idComanda, Timestamp data, int idProduto) throws Exception {
		String sql = "DELETE FROM produtoComanda WHERE produtoId = ? AND comandaId = ? AND comandaData = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, idProduto);
		ps.setInt(2, idComanda);
		ps.setTimestamp(3, data);
		ps.executeUpdate();
	}
	
	/**
	 * Adiciona produto em uma comanda
	 * @param idComanda ID da comanda
	 * @param data Data da criação da comanda
	 * @param idProduto ID do Produto
	 * @param qtde Quantidade do produto que vai ser adicionado
	 * @throws Exception
	 */
	public static void addProduto(int comandaId, Timestamp data, int produtoId, int qtde) throws Exception {
		String sql = "INSERT INTO produtoComanda (comandaId, comandaData, produtoId, quantidade) VALUES (?, ?, ?, ?)";

		PreparedStatement ps;
		ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, comandaId);
		ps.setTimestamp(2, data);
		ps.setInt(3, produtoId);
		ps.setInt(4, qtde);
		ps.executeUpdate();
	}
	
	/**
	 * Pega uma determinada comanda do BD e retorna para o sistema
	 * @param id ID da comanda
	 * @param data Data da criação da comanda
	 * @return Retorna a pesquisa contendo a comanda desejada
	 * @throws Exception
	 */
	public static ResultSet getComanda(int id, Timestamp data) throws Exception {
		String sql = "SELECT * FROM comanda WHERE comandaId = ? AND data = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.setTimestamp(2, data);
		return ps.executeQuery();
	}
	
	/**
	 * Pesquisa e retorna todas a comandas salvas no BD
	 * @return Retorna a pesquisa contendo as comandas
	 * @throws Exception
	 */
	public static ResultSet getAllComandas() throws Exception {
		String sql = "SELECT * FROM comanda WHERE pago = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setBoolean(1, false);
		
		return ps.executeQuery();
	}
	
	/**
	 * Pesquisa e retorna todos os produtos de uma determinada comanda
	 * @param id ID da comanda
	 * @param data Data da criação da comanda 
	 * @return Retorna o resultado da pesquisa
	 * @throws Exception
	 */
	public static ResultSet getAllProduto(int id, Timestamp data) throws Exception {		
		String sql = "SELECT pc.produtoId, p.nome, pc.quantidade, p.valor, SUM(pg.valor) FROM produtoComanda pc LEFT JOIN pagamentoProduto pp ON pp.produtoId = pc.produtoId LEFT JOIN pagamento pg ON pg.pagamentoId = pp.pagamentoId AND pc.comandaId = pg.comandaId AND pc.comandaData = pg.comandaData JOIN produto p ON p.produtoId = pc.produtoId WHERE pc.comandaId = ? AND pc.comandaData = ? GROUP BY 1, 2, 3, 4";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ps.setTimestamp(2, data);
		return ps.executeQuery();		
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
	public static boolean update(int id, Timestamp data, String mesa, int funcionario, boolean pago) throws Exception {
		String sql = "UPDATE comanda SET mesa = ?, funcionarioId = ?, pago = ? WHERE comandaId = ? AND data = ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setString(1, mesa);
		ps.setInt(2, funcionario);
		ps.setBoolean(3, pago);
		ps.setInt(4, id);
		ps.setTimestamp(5, data);
		ps.executeUpdate();
		return true;
	}
	
	/**
	 * Retorna o valor total da comanda
	 * @param comandaId ID da comanda
	 * @param date Data de criação da comanda
	 * @return Retorna pesquisa
	 * @throws Exception
	 */
	public static float getPrecoComanda(int comandaId, Timestamp date) throws Exception {
		String sql = "SELECT cp.data, cp.quantidade, p.valor, pa.data, pa.valor FROM comanda c JOIN produtoComanda cp ON c.comandaId = cp.comandaId AND c.data = cp.comandaData JOIN produto p ON p.produtoId = cp.produtoId LEFT JOIN produtoAlterado pa ON p.produtoId = pa.produtoId WHERE c.data = ? AND c.comandaId = ?";

		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setTimestamp(1, date);
		ps.setInt(2, comandaId);

		float valor = 0;
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			valor += result.getFloat(3) * result.getInt(2);
		}

		return valor;
	}
	
	/**
	 * Retorna todas as comandas que já foram pagas em determinado período de tempo.
	 * @param dataDe Data inicial da pesquisa
	 * @param dataAte Data final da pesquisa
	 * @return Retorna o resultado da pesquisa
	 * @throws Exception
	 */
	public static ResultSet getAllComandasPagas(Date dataDe, Date dataAte) throws Exception {
		String sql = "SELECT * FROM comanda WHERE pago = ? AND data >= ? AND data <= ?";
		
		PreparedStatement ps = Valores.getConnection().prepareStatement(sql);
		ps.setBoolean(1, true);
		ps.setTimestamp(2, new Timestamp(dataDe.getTime()));

		Calendar cal = Calendar.getInstance();
		cal.setTime(dataAte);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		ps.setTimestamp(3, new Timestamp(cal.getTimeInMillis()));
		
		return ps.executeQuery();
	}
}


