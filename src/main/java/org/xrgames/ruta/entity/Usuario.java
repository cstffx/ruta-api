package org.xrgames.ruta.entity;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Representa un usuario del sistema.
 */
public class Usuario {
	
	@NotNull
	public String id;
	
	@NotNull
	public String username;
	
	public Usuario() {
		
	}
	
	public Usuario(String username){
		this.id = UUID.randomUUID().toString();
		this.username = username;
	}
	
	public Usuario(String id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public static Usuario of(String username) {
		return new Usuario(username);
	}
		
	public static Usuario of(String id, String username) {
		return new Usuario(id, username);
	}
	
	/**
	 * @return El id del usuario.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return El nombre de usuario.
	 */
	public String getUsername() {
		return username;
	}
}	
