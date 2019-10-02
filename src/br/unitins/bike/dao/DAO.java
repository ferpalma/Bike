package br.unitins.bike.dao;

import java.util.List;

import br.unitins.bike.model.Usuario;

public interface DAO {
	
	// CRUD
	
	public boolean create(Usuario usuario);
	public boolean update(Usuario usuario);
	public boolean delete(int id);
	public List<Usuario> findAll();

}
