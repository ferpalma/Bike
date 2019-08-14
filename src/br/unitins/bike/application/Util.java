package br.unitins.bike.application;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Util {

	public static void addMessageError(String message) {
		FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage(message));
	}
	
}
