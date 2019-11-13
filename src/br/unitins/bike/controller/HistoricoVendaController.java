package br.unitins.bike.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Session;
import br.unitins.bike.dao.VendaDAO;
import br.unitins.bike.model.Produto;
import br.unitins.bike.model.Usuario;
import br.unitins.bike.model.Venda;

@Named
@ViewScoped
public class HistoricoVendaController implements Serializable {

	private static final long serialVersionUID = -8585030860902916284L;
	
	private List<Venda> listaVenda = null;
	

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			listaVenda = dao.findByUsuario(usuario.getId());
			if (listaVenda == null)
				listaVenda = new ArrayList<Venda>();
			dao.closeConnection();
		}
		return listaVenda;
	}
	
	public String detalhes(Venda venda) {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.put("detalheVenda", venda);
		
		return "detalhesvenda.xhtml?faces-redirect=true";
	}
	
}
