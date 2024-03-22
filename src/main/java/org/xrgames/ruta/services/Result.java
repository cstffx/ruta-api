package org.xrgames.ruta.services;

import jakarta.ws.rs.client.Client;

/**
 * Mantiene un Invocation.Builder asociado a un usuario. Cualquier petición
 * generada con este Invocation.Builder utiliza el id y nombre de usuario
 * asociado.
 */
public class Result {
	private SessionToken token = new SessionToken();

	public boolean isSome() {
		return !isErr();
	}

	public boolean isErr() {
		return token.isEmpty();
	}

	public SessionToken getToken() {
		return token;
	}

	public void setToken(SessionToken token) {
		this.token = token;
	}
}