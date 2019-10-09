package br.unitins.bike.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Util;
import br.unitins.bike.dao.UsuarioDAO;
import br.unitins.bike.model.Perfil;
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
		if (validarDados()) {
			UsuarioDAO dao = new UsuarioDAO();
			// faz a inclusao no banco de dados
			if (dao.create(getUsuario())) {
				Util.addMessageInfo("Inclusão realizada com sucesso.");
				limpar();
				listaUsuario = null;
			} else 
				Util.addMessageInfo("Erro ao incluir o Usuário no Banco de Dados.");
		}
	}
	
	public void alterar() {
		if (validarDados()) {
			UsuarioDAO dao = new UsuarioDAO();
			// faz a alteracao no banco de dados
			if (dao.update(getUsuario())) {
				Util.addMessageInfo("Alteração realizada com sucesso.");
				limpar();
				listaUsuario = null;
			} else 
				Util.addMessageInfo("Erro ao alterar o Usuário no Banco de Dados.");
		}
	}
	
	public void excluir() {
		if (excluir(getUsuario()))
			limpar();
	}
	
	public boolean excluir(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		// faz a exclusao no banco de dados
		if (dao.delete(usuario.getId())) {
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			listaUsuario = null;
			return true;
		} else {
			Util.addMessageInfo("Erro ao excluir o Usuário no Banco de Dados.");
			return false;
		}
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
		UsuarioDAO dao = new UsuarioDAO();
		// buscando um usuario pelo id
		Usuario usu = dao.findId(usuario.getId());
		setUsuario(usu);
//		setUsuario(dao.findId(usuario.getId()));
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
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
}
