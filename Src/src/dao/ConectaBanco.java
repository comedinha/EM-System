package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Crypto;
import util.Valores;

/**
 * Classe referente a conexão com o banco de dados.
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi.
 * @version 1.0
 */
public class ConectaBanco {
	/**
	 * Realiza a conexão com o banco de dados, e salva o objeto conexão como global.
	 * @param type tipo da conexão
	 * @param addr endereço de rede do banco de dados
	 * @param port porta do banco de dados
	 * @param usr usuario do banco de dados
	 * @param pass senha do banco de dados
	 * @param checkFunc verifica se é necessario configurar o banco de dados
	 * @throws Exception
	 */
	public static void conectaBanco(String type, String addr, String port, String usr, String pass, Boolean checkFunc) throws Exception {
		String url = "jdbc:" +  type + "://"+ addr +":"+ port +"/EMSystem";
		Crypto cr = new Crypto();
		Connection con = DriverManager.getConnection(url, usr, cr.decrypt(pass));
		if (checkFunc && !Funcionario.verificaGerente(con))
			throw new Exception("Nenhum funcionário existente.");
		Valores.setBanco(con);
	}
	
	/**
	 * Desconecta a aplicação do banco de dados
	 * @throws SQLException
	 */
	public static void desconectaBanco() throws SQLException {
		Valores.getConnection().close();
	}
}