package org.xrgames.ruta.services.session;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@RequestScoped
public class TokenWriter extends TokenHandler {
	
	@Context
	private HttpServletRequest request;
	
	/**
	 * Escribe un token en la sesion actual.
	 * @param token
	 */
	void write(SessionToken token) {
		HttpSession session = request.getSession();
		session.setAttribute(ID_ATTRIBUTE, token.id);
		session.setAttribute(NAME_ATTRIBUTE, token.username);
	}
	
	/**
	 * Borra un token de la sesion actual.
	 */
	void erase() {
		HttpSession session = request.getSession();
		session.removeAttribute(ID_ATTRIBUTE);
		session.removeAttribute(NAME_ATTRIBUTE);
	}
}
