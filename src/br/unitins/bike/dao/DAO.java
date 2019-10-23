package br.unitins.bike.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import br.unitins.bike.model.Usuario;

public abstract class DAO<T>{
	
	// CRUD
	public abstract void create(T entity) throws SQLException;
	public abstract boolean update(T entity);
	public abstract boolean delete(int id);
	public abstract List<T> findAll();
	private Connection conn = null;
	
	public DAO(Connection conn) {
		this.conn = conn;
	}
	
	public Connection getConnection() {
		if (isClosedConnection()) {
			try {
				// registrando o drive do prostgres
				Class.forName("org.postgresql.Driver");
				// estabelecendo uma conexao com o banco de dados
				conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/bikedb", 
								"topicos", "123456");
				// obriga a trabalhar com commit e rollback
				conn.setAutoCommit(false);
				System.out.println("Conexao realizada com sucesso.");
			} catch (SQLException e) {
				System.out.println("Falha ao conectar ao banco de dados.");
				conn = null;
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Fala ao resgistrar o Driver do banco");
				conn = null;
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	private boolean isClosedConnection() {
		try {
			if (conn == null || conn.isClosed()) 
				return true;
		} catch (SQLException e) {
			return true;
		}
		return false;
	}
	
	public void closeConnection() {
		try {
			getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollbackConnection() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
