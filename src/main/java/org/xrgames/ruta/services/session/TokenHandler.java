package org.xrgames.ruta.services.session;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Context;

@ApplicationScoped
public class TokenHandler {
	public static final String ID_ATTRIBUTE = "userid";
	public static final String NAME_ATTRIBUTE = "username";
}
