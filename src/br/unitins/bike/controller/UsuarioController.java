package br.unitins.bike.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Util;
import br.unitins.bike.dao.UsuarioDAO;
import br.unitins.bike.model.Telefone;
import br.unitins.bike.model.Usuario;

@Named
@ViewScoped
//dontpad.com/sisunitins_topicos1_20192
public class UsuarioController implements Serializable {
	
	private static final long serialVersionUID = -6998638931332554108L;

	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.findAll();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
		} 
		return listaUsuario;
	}

	public void incluir() {
		UsuarioDAO dao = new UsuarioDAO();
		// faz a inclusao no banco de dados
		if (dao.create(getUsuario())) {
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
			listaUsuario = null;
		} else {
			Util.addMessageInfo("Erro ao incluir o Usuário no Banco de Dados.");
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
