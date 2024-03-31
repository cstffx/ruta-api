package org.xrgames.ruta.services.session;

import org.xrgames.ruta.entity.Usuario;
import org.xrgames.ruta.services.AlreadyExistsException;
import org.xrgames.ruta.services.UserRegistry;
import org.xrgames.ruta.util.Option;
import org.xrgames.ruta.util.Result;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Encargado de manejar la sesion del usuario.
 */
@RequestScoped
public class UserSession {

	@Inject
	private UserRegistry userRegistry;

	@Inject
	private TokenReader reader;

	@Inject
	private TokenWriter writer;

	/**
	 * Retorna true si el usuario no ha iniciado session.
	 * 
	 * @return
	 */
	public boolean isAnonimous() {
		return getToken().isEmpty();
	}

	/**
	 * Agrega un usuario al registro
	 * 
	 * @param username
	 * @return
	 * @throws AlreadyExistsException
	 */
	public Result<SessionToken, AlreadyExistsException> login(String username) throws AlreadyExistsException {
		var token = getToken();
		if (!token.isEmpty()) {
			return Result.of(token);
		}

		// No se puede autenticar un usuario existente.
		var addResult = userRegistry.add(username);
		if (addResult.isErr()) {
			return Result.of(addResult.getError());
		}

		var usuario = addResult.unwrap();

		// Construir el token.
		token = new SessionToken(usuario.id, usuario.username);

		// Escribirlo en la sesion
		writer.write(token);

		return Result.of(token);
	}

	/**
	 * Finaliza la sesion
	 */
	public void logout() {
		var token = getToken();
		if (token.isEmpty()) {
			return;
		}
		var id = token.id;
		userRegistry.remove(id);
		writer.erase();
		
	}

	/**
	 * Recupera el token actual de la sesion.
	 * 
	 * @return
	 */
	public SessionToken getToken() {
		return reader.read();
	}

	/**
	 * Devuelve el id actual de usuario.
	 * 
	 * @return
	 */
	public Option<String> getId() {
		var token = reader.read();
		if (token.isEmpty()) {
			return Option.none();
		}
		return Option.of(token.id);
	}

	/**
	 * Retorna el usuario de la sesión actual.
	 * 
	 * @return
	 */
	public Option<Usuario> getUser() {
		var token = this.getToken();
		return userRegistry.fromToken(token);
	}
}