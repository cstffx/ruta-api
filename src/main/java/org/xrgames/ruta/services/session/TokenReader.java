package org.xrgames.ruta.services.session;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@RequestScoped
public class TokenReader extends TokenHandler {

	@Context
	private HttpServletRequest request;

	/**
	 * Recupera un token de la sesion actual
	 * 
	 * @return
	 */
	SessionToken read() {
		HttpSession session = request.getSession();
		Object id = session.getAttribute(ID_ATTRIBUTE);
		Object username = session.getAttribute(NAME_ATTRIBUTE);
		if (id == null) {
			return new SessionToken();
		}
		return new SessionToken((String) id, (String) username);
	}
}
