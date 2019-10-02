package br.unitins.bike.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Util;
import br.unitins.bike.model.Telefone;
import br.unitins.bike.model.Usuario;

@Named
@ViewScoped
//dontpad.com/sisunitins_topicos1_20192
public class UsuarioController implements Serializable {
	
	private static final long serialVersionUID = -6998638931332554108L;

	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	
	public static void main(String[] args) {
		
		
	}
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			Connection  conn = null;
			try {
				// registrando o drive do prostgres
				Class.forName("org.postgresql.Driver");
				// estabelecendo uma conexao com o banco de dados
				conn = 
						DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/bikedb", 
								"topicos", "123456");
			} catch (SQLException e) {
				System.out.println("Falha ao conectar ao banco de dados.");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Fala ao resgistrar o Driver do banco");
				e.printStackTrace();
			}
			System.out.println("Conexao realizada com sucesso.");
			
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
				
				listaUsuario = new ArrayList<Usuario>();
				
				while(rs.next()) {
					Usuario usuario = new Usuario();
					usuario.setId(rs.getInt("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setAtivo(rs.getBoolean("ativo"));
					
					listaUsuario.add(usuario);
				}
			
			} catch (SQLException e) {
				Util.addMessageInfo("Erro ao executar um SQL");
				e.printStackTrace();
			}
			
		} // if
		return listaUsuario;
	}
	
	public void incluir() {
		
		Connection  conn = null;
		try {
			// registrando o drive do prostgres
			Class.forName("org.postgresql.Driver");
			// estabelecendo uma conexao com o banco de dados
			conn = 
					DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/bikedb", 
							"topicos", "123456");
		} catch (SQLException e) {
			System.out.println("Falha ao conectar ao banco de dados.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Fala ao resgistrar o Driver do banco");
			e.printStackTrace();
		}
		System.out.println("Conexao realizada com sucesso.");
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"INSERT INTO " +
				    "public.usuario " +
				    " (nome, login, senha, ativo) " +
					"VALUES " +
				    " (?, ?, ?, ?) ");
			stat.setString(1, getUsuario().getNome());
			stat.setString(2, getUsuario().getLogin());
			stat.setString(3, getUsuario().getSenha());
			stat.setBoolean(4, getUsuario().getAtivo());
			
			stat.execute();
			
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
			listaUsuario = null;
			
		} catch (SQLException e) {
			Util.addMessageInfo("Erro ao executar um Insert");
			e.printStackTrace();
		}
		
		
//		if (validarDados()) {
//			getUsuario().setId(ultimoId() + 1);
//			getListaUsuario().add(getUsuario());
//			limpar();
//			Util.addMessageInfo("Inclusão realizada com sucesso.");
//		}
	}
	
	public void alterar() {
		if (validarDados()) {
			int index = getListaUsuario().indexOf(getUsuario());
			if (index == -1) {
				Util.addMessageError("Objeto não encontrado na lista.");
			} else {
				getListaUsuario().set(index, getUsuario());
				limpar();
				Util.addMessageInfo("Alteração realizada com sucesso.");
			}
		}
	}
	
	public void excluir() {
		getListaUsuario().remove(getUsuario());
		limpar();
		Util.addMessageInfo("Exclusão realizada com sucesso.");
	}
	
	public void excluir(Usuario usuario) {
		System.out.println("entrou");
		getListaUsuario().remove(usuario);
		limpar();
		Util.addMessageInfo("Exclusão realizada com sucesso.");
	}

	private boolean validarDados() {
		if (getUsuario().getSenha().isBlank()) {
			Util.addMessageWarn("O campo senha deve ser informado.");
			return false;
		}
//		if (getUsuario().getSenha() == null || 
//				getUsuario().getSenha().trim().equals("") ) {
//			Util.addMessageError("O campo senha deve ser informado.");
//			return false;
//		}
		return true;
	}
	
	private int ultimoId() {
		int maior = 0;
		for (Usuario usuario : listaUsuario) {
			if (usuario.getId() > maior)
				maior = usuario.getId();
		}
		return maior;
	}
	
	public void editar(Usuario usuario) {
		System.out.println("dfsgfgsdf");
		setUsuario(usuario.clone());
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setTelefone(new Telefone());
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void limpar() {
		usuario = null;
	}
	
}
