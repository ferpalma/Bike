package br.unitins.bike.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.bike.application.Util;
import br.unitins.bike.model.Usuario;

public class UsuarioDAO implements DAO {
	
	private Connection getConexao() {
		Connection  conn = null;
		try {
			// registrando o drive do prostgres
			Class.forName("org.postgresql.Driver");
			// estabelecendo uma conexao com o banco de dados
			conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/bikedb", 
							"topicos", "123456");
			System.out.println("Conexao realizada com sucesso.");
		} catch (SQLException e) {
			System.out.println("Falha ao conectar ao banco de dados.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Fala ao resgistrar o Driver do banco");
			e.printStackTrace();
		}
		
		return conn;
	}

	@Override
	public boolean create(Usuario usuario) {
		
		Connection  conn = getConexao();
		if (conn == null) 
			return false;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"INSERT INTO " +
				    "public.usuario " +
				    " (nome, login, senha, ativo) " +
					"VALUES " +
				    " (?, ?, ?, ?) ");
			stat.setString(1, usuario.getNome());
			stat.setString(2, usuario.getLogin());
			stat.setString(3, usuario.getSenha());
			stat.setBoolean(4, usuario.getAtivo());
			
			stat.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuario> findAll() {
		Connection conn = getConexao();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  id, " +
					"  nome, " +
					"  login, " +
					"  senha, " +
					"  ativo " +
					"FROM " +
					"  public.usuario ");
			ResultSet rs = stat.executeQuery();
			
			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				
				listaUsuario.add(usuario);
			}
			
			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
