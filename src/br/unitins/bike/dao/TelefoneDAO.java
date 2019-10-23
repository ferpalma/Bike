package br.unitins.bike.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.unitins.bike.model.Telefone;

public class TelefoneDAO extends DAO<Telefone> {

	public TelefoneDAO(Connection conn) {
		super(conn);
	}

	@Override
	public void create(Telefone entity) throws SQLException {
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " +
			    "public.telefone " +
			    " (id, codigoarea, numero) " +
				"VALUES " +
			    " (?, ?, ?) ");
		stat.setInt(1, entity.getId());
		stat.setString(2, entity.getCodigoArea());
		stat.setString(3, entity.getNumero());
		
		stat.execute();
		stat.close();
			
	}

	@Override
	public boolean update(Telefone entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Telefone> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
