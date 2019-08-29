package br.unitins.bike.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Util;
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
			listaUsuario = new ArrayList<Usuario>();
			listaUsuario.add(new Usuario(1, "Pedro", "pedro", "123", true));
			listaUsuario.add(new Usuario(2, "Maria", "maria", "321", false));
		}
		return listaUsuario;
	}
	
	public void incluir() {
		if (validarDados()) {
			getUsuario().setId(ultimoId() + 1);
			getListaUsuario().add(getUsuario());
			limpar();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
		}
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
		setUsuario(usuario.clone());
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void limpar() {
		usuario = null;
	}
	
}
