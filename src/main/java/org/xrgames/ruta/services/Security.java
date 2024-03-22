package org.xrgames.ruta.services;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@ApplicationScoped
public class Security {
	
	public static final String SESSION_ID_ATTRIBUTE = "userid";
	public static final String SESSION_NAME_ATTRIBUTE = "username";

	@Context
	private HttpServletRequest request;

	/**
	 * Retorna true si el usuario no ha iniciado session.
	 * 
	 * @return
	 */
	public boolean isAnonimous() {
		return getToken().isEmpty();
	}

	/**
	 * Inicia la sesion
	 * 
	 * @param username
	 * @return
	 */
	public SessionToken login(String username) {
		var token = getToken();
		if(false == token.isEmpty()) {
			return token;
		}
		
		var sessionId = UUID.randomUUID().toString();

		HttpSession session = request.getSession();
		session.setAttribute(Security.SESSION_ID_ATTRIBUTE, sessionId);
		session.setAttribute(Security.SESSION_NAME_ATTRIBUTE, username);

		return new SessionToken(sessionId, username);
	}

	/**
	 * Finaliza la sesion
	 */
	public boolean logout() {
		HttpSession session = request.getSession();
		session.removeAttribute(Security.SESSION_ID_ATTRIBUTE);
		session.removeAttribute(Security.SESSION_NAME_ATTRIBUTE);
		return true;
	}

	/**
	 * Recupera el token actual de la sesion.
	 * 
	 * @return
	 */
	public SessionToken getToken() {
		HttpSession session = request.getSession();
		Object id = session.getAttribute(Security.SESSION_ID_ATTRIBUTE);
		Object username = session.getAttribute(Security.SESSION_NAME_ATTRIBUTE);
		if (id == null) {
			return new SessionToken();
		}
		return new SessionToken((String) id, (String) username);
	}

	/**
	 * Devuelve el id actual de usuario
	 * 
	 * @return
	 */
	public String getId() {
		Object id = request.getSession().getAttribute(Security.SESSION_ID_ATTRIBUTE);
		return id != null ? id.toString() : null;
	}
}