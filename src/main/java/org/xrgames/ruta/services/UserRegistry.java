package org.xrgames.ruta.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.xrgames.ruta.dto.UsuarioInfo;
import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.services.session.SessionToken;
import org.xrgames.ruta.services.session.UserSession;
import org.xrgames.ruta.util.Option;
import org.xrgames.ruta.util.Result;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Mantiene una lista de los usuarios que se encuentran activos en el sistema.
 */
@ApplicationScoped
public class UserRegistry {

	@Inject
	UserSession session;

	/**
	 * Lista de usuarios registrados.
	 */
	private HashMap<String, Usuario> usuarios = new HashMap<>();

	public ArrayList<UsuarioInfo> getAll() {
		var result = new ArrayList<UsuarioInfo>(usuarios.size());
		for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
			result.add(new UsuarioInfo(entry.getValue().username));
		}
		return result;
	}

	/**
	 * Inserta un usuario en el registro si no se encuentra registrado con
	 * anterioridad.
	 * 
	 * @param username
	 * @return
	 */
	public Result<Usuario, AlreadyExistsException> add(String username) {
		if (this.existsByUsername(username)) {
			return Result.of(new AlreadyExistsException());
		}

		var key = UUID.randomUUID().toString();
		var usuario = new Usuario(key, username);
		usuarios.put(key, usuario);

		return Result.of(usuario);
	}

	/**
	 * Remueve un usuario del registro.
	 * 
	 * @param id
	 */
	public void remove(String id) {
		usuarios.remove(id);
	}

	/**
	 * Encuentra un usuario por su nombre.
	 * 
	 * @param username
	 * @return
	 */
	public Option<Usuario> findByUsername(String username) {
		for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
			var usuario = entry.getValue();
			if (usuario.username.equals(username)) {
				return Option.of(usuario);
			}
		}
		return Option.none();
	}

	/**
	 * Retorna true si un usuario ya se encuentra registrado.
	 * 
	 * @param username
	 * @return True si el usuario se encuentra registrado.
	 */
	public boolean existsByUsername(String username) {
		return findByUsername(username).isSome();
	}

	/**
	 * Devuelve un usuario utilizando un token de sesión.
	 * 
	 * @param token
	 * @return
	 */
	public Option<Usuario> fromToken(SessionToken token) {
		var key = token.id;
		var usuario = this.usuarios.get(key);
		if (null == usuario) {
			return Option.none();
		}
		return Option.of(usuario);
	}

	/**
	 * Devuelve un usuario de la sesion actual si se encuentra registrado.
	 * 
	 * @param token
	 * @return
	 */
	public Option<Usuario> currentUser() {
		var token = session.getToken();

		if (token.isEmpty()) {
			return Option.none();
		}

		var usuario = this.usuarios.get(token.id);
		if (null == usuario) {
			return Option.none();
		}

		return Option.of(usuario);
	}

	/**
	 * Vacia el registro de usuarios.
	 */
	public void clear() {
		this.usuarios.clear();
	}
}