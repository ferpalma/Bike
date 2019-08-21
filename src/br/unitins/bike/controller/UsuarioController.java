package br.unitins.bike.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.bike.model.Usuario;

@Named
@RequestScoped
//dontpad.com/sisunitins_topicos1_20192
public class UsuarioController {
	
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
		System.out.println(getUsuario().getNome());
		System.out.println(getUsuario().getLogin());
		System.out.println(getUsuario().getSenha());
		System.out.println(getUsuario().getAtivo());
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
