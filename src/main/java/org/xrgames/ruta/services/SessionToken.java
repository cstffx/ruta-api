package org.xrgames.ruta.services;

public class SessionToken {
	
	public boolean empty = true;
	public String id = null; 
	public String username = null;
	
	public SessionToken() {
		
	}
	
	public SessionToken(String id, String username){
		this.id = id;
		this.username = username;
	}
	
	public boolean is(String username) {
		return username.equals(this.username);
	}
	
	public boolean isEmpty() {
		return id == null;
	}
}