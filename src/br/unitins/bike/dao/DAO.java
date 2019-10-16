package br.unitins.bike.dao;

import java.util.List;

import br.unitins.bike.model.Usuario;

public interface DAO<T>{
	
	// CRUD
	
	public boolean create(T entity);
	public boolean update(T entity);
	public boolean delete(int id);
	public List<T> findAll();

}
