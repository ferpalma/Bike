package br.unitins.bike.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.bike.application.Session;
import br.unitins.bike.application.Util;
import br.unitins.bike.dao.ProdutoDAO;
import br.unitins.bike.model.ItemVenda;
import br.unitins.bike.model.Produto;

@Named
@ViewScoped
public class VendaProdutoController implements Serializable {

	private static final long serialVersionUID = -8413311224021825448L;

	private String nome;
	private List<Produto> listaProduto = null;
	
	public void pesquisar() {
		listaProduto = null;
	}
	
	public void adicionar(int idProduto) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.findById(idProduto);
		// verifica se existe um carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona um carrinho (de itens de venda) na sessao
			Session.getInstance().setAttribute("carrinho", 
					new ArrayList<ItemVenda>());
		}
		
		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = 
				(ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		
		// criando um item de venda para adicionar no carrinho
		ItemVenda item = new ItemVenda();
		item.setProduto(produto);
		item.setValor(produto.getValor());
		
		// adicionando o item no carrinho (variavel local) 
		carrinho.add(item);
		
		// atualizando o carrinho na sessao
		Session.getInstance().setAttribute("carrinho", carrinho);
		
		Util.addMessageInfo("Produto adicionado no carrinho. "
				+ "Quantidade de Itens: " + carrinho.size());
		
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findByNome(getNome());
			if (listaProduto == null)
				listaProduto = new ArrayList<Produto>();
			dao.closeConnection();
		}
		return listaProduto;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
