package org.xrgames.ruta.services;

import jakarta.ws.rs.client.Invocation;

public class LoginResult {
	public SessionToken token = new SessionToken();
	public Invocation.Builder builder;
	
	public boolean isSome() {
		return !isErr();
	}
	
	public boolean isErr() {
		return token.isEmpty();
	}
}