package br.unitins.bike.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.bike.application.Session;
import br.unitins.bike.application.Util;
import br.unitins.bike.dao.UsuarioDAO;
import br.unitins.bike.model.Usuario;

@Named
@RequestScoped
public class LoginController {
	
	private Usuario usuario;

	
	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hashSenha = Util.hashSHA256(getUsuario().getSenha());
		Usuario usuario = 
			dao.login(getUsuario().getLogin(), hashSenha);
		
		if (usuario != null) {
			// armazenando um usuario na sessao
			Session.getInstance().setAttribute("usuarioLogado", usuario);
			return "template.xhtml?faces-redirect=true";
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
