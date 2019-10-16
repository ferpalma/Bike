package br.unitins.bike.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.bike.model.Perfil;
import br.unitins.bike.model.Telefone;
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
			
			// para tratar a transacao manualmente (commit e rollback)
			conn.setAutoCommit(false);
			
			PreparedStatement stat = conn.prepareStatement(
					"INSERT INTO " +
				    "public.usuario " +
				    " (nome, login, senha, ativo, perfil) " +
					"VALUES " +
				    " (?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, usuario.getNome());
			stat.setString(2, usuario.getLogin());
			stat.setString(3, usuario.getSenha());
			stat.setBoolean(4, usuario.getAtivo());
			stat.setInt(5, usuario.getPerfil().getValue());
			
			stat.execute();
			
			// obtendo o id gerado pela tabela do banco de dados
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			usuario.getTelefone().setId(rs.getInt("id"));
			
			
			stat = conn.prepareStatement(
					"INSERT INTO " +
				    "public.telefone " +
				    " (id, codigoarea, numero) " +
					"VALUES " +
				    " (?, ?, ?) ");
			stat.setInt(1, usuario.getTelefone().getId());
			stat.setString(2, usuario.getTelefone().getCodigoArea());
			stat.setString(3, usuario.getTelefone().getNumero());
			
			stat.execute();
			
			conn.commit();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Usuario usuario) {
		Connection  conn = getConexao();
		if (conn == null) 
			return false;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"UPDATE public.usuario SET " +
				    " nome = ?, " +
				    " login = ?, " +
				    " senha = ?, " +
				    " ativo = ?, " +
				    " perfil = ? " +
					"WHERE " +
				    " id = ? ");
			stat.setString(1, usuario.getNome());
			stat.setString(2, usuario.getLogin());
			stat.setString(3, usuario.getSenha());
			stat.setBoolean(4, usuario.getAtivo());
			stat.setInt(5, usuario.getPerfil().getValue());
			stat.setInt(6, usuario.getId());
			
			stat.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {

		Connection  conn = getConexao();
		if (conn == null) 
			return false;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"DELETE FROM public.usuario WHERE id = ?");
			stat.setInt(1, id);
			
			stat.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					"  ativo, " +
					"  perfil " +					
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
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				
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

	public Usuario findId(Integer id) {
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
					"  ativo, " +
					"  perfil " +					
					"FROM " +
					"  public.usuario " +
					"WHERE id = ? ");
			
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			Usuario usuario = null;
			
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setTelefone(new Telefone());
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}
			
			return usuario;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
