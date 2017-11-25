package dao;

import java.sql.SQLException;

public interface IConnector {
	public void inserir() throws SQLException;
	public void atualizar() throws SQLException;
	public void get(int i) throws SQLException;
	public void getAll() throws SQLException;
}
