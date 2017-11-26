package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnector {
	public void atualizar() throws SQLException;
	public void get(int i) throws SQLException;
	public ResultSet getAll() throws SQLException;
}
