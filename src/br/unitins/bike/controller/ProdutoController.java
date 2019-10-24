package br.unitins.bike.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Util;
import br.unitins.bike.dao.DAO;
import br.unitins.bike.dao.ProdutoDAO;
import br.unitins.bike.model.Perfil;
import br.unitins.bike.model.Telefone;
import br.unitins.bike.model.Produto;

@Named
@ViewScoped
public class ProdutoController implements Serializable {
	
	private static final long serialVersionUID = -6521198943457165212L;

	private Produto produto;
	
	public ProdutoController() {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("produtoFlash");
		produto = (Produto) flash.get("produtoFlash");
	}
	
	public void incluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a inclusao no banco de dados
		try {
			dao.create(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o Produto no Banco de Dados.");
			e.printStackTrace();
		}
	}
	
	public void alterar() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a alteracao no banco de dados
		try {
			dao.update(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Alteração realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao alterar o Usuário no Banco de Dados.");
			e.printStackTrace();
		}
	}
	
	
	public boolean excluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a exclusao no banco de dados
		if (dao.delete(getProduto().getId())) {
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			limpar();
			return true;
		} else {
			Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
			return false;
		}
	}


	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void limpar() {
		produto = null;
	}
	
}
