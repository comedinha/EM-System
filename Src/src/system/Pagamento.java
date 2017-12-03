package system;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class Pagamento {
	
	public static void pagamentoComanda(int id, Timestamp time, Float valor, int funcionarioId, boolean desconto) throws Exception {
		dao.Pagamento.pagamentoComanda(id, time, valor, funcionarioId, desconto);
	}
	
	public static void pagamentoProduto(int id, int quantidade, int comandaId, Timestamp comandaTime, Float valor, int funcionarioId) throws Exception {		
		dao.Pagamento.pagamentoProduto(id, quantidade, comandaId, comandaTime, valor, funcionarioId);
	}

	public static float getAllValor(int id, Timestamp time, boolean desconto) throws Exception {
		ResultSet result = dao.Pagamento.getAll(id, time, desconto);
		float valor = 0;
		while (result.next())
			valor += result.getFloat("valor");

		return valor;
	}
}
