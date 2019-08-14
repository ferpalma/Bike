package br.unitins.bike.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.bike.application.Util;
import br.unitins.bike.model.Usuario;

@Named
@RequestScoped
public class LoginController {
	
	private Usuario usuario;

	
	public String logar() {
		if (getUsuario().getLogin().equals("teste")
				&& getUsuario().getSenha().equals("123")) {
			System.out.println(getUsuario().getLogin());
			System.out.println(getUsuario().getSenha());
			return "hello.xhtml?faces-redirect=true";
		}
		Util.addMessageError("Usuário ou Senha Inválido.");
		return null;
	}
	
	public void limpar() {
		setUsuario(new Usuario());
//		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
