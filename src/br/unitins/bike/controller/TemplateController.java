package br.unitins.bike.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Session;
import br.unitins.bike.model.Usuario;

@Named
@ViewScoped
public class TemplateController implements Serializable{
	
	private static final long serialVersionUID = -1590312194477481960L;
	
	private Usuario usuarioLogado;

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			// buscando o usuario da sessao
			usuarioLogado = (Usuario)Session.getInstance().getAttribute("usuarioLogado");
			if (usuarioLogado == null)
				usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public String encerrarSessao() {
		Session.getInstance().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}
	
}
