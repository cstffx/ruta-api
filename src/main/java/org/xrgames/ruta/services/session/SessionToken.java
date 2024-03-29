package org.xrgames.ruta.services.session;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SessionToken {

	@JsonIgnore
	public static final String ID_ATTRIBUTE = "userid";
	@JsonIgnore
	public static final String NAME_ATTRIBUTE = "username";

	public String id = null;
	public String username = null;
	public String juegoId = null;

	public SessionToken() {

	}

	public SessionToken(String id, String username) {
		this.id = id;
		this.username = username;
	}

	@JsonIgnore
	public static SessionToken empty() {
		return new SessionToken();
	}

	public boolean is(String username) {
		return username.equals(this.username);
	}

	@JsonIgnore
	public boolean isEmpty() {
		return id == null;
	}
}