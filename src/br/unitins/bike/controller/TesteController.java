package br.unitins.bike.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class TesteController {
	

	private String nome = "";
	
	private List<String> lista = null;
	
	public void add() {
		getLista().add(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<String> getLista() {
		if (lista == null) {
			lista = new ArrayList<String>();
			lista.add("Fredson");
			lista.add("Marco");
			lista.add("Silvano");
		}
		return lista;
	}
	
}
