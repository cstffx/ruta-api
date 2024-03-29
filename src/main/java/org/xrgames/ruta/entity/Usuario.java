package org.xrgames.ruta.entity;

import java.util.UUID;

import org.xrgames.logic.Juego;
import org.xrgames.logic.Jugador;
import org.xrgames.ruta.util.Option;

import jakarta.validation.constraints.NotNull;

/**
 * Representa un usuario del sistema.
 */
public class Usuario {

	@NotNull
	public String id;

	@NotNull
	public String username;

	/**
	 * Referencia al posible juego actual del usuario.
	 */
	public Option<Juego> juego = Option.none();

	public Usuario() {

	}

	public Usuario(String username) {
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

	/**
	 * @return
	 */
	public Option<Juego> getJuego() {
		return juego;
	}

	/**
	 * @param juego
	 */
	public void setJuego(Option<Juego> juego) {
		this.juego = juego;
	}

	public Jugador toJugador() {
		return new Jugador(this);
	}
}
