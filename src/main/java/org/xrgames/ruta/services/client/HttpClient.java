package org.xrgames.ruta.services.client;

import org.xrgames.ruta.services.Debug;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.Endpoint.Route;
import org.xrgames.ruta.services.LoginForm;
import org.xrgames.ruta.services.ResponseUtil;
import org.xrgames.ruta.util.Option;
import org.xrgames.ruta.util.UsernameFactory;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Utilidad para realizar peticiones http al servidor.
 */
public class HttpClient {

	public static final String SESSION_ID_KEY = "JSESSIONID";
	private String sessionId;
	private Client client;
	private boolean debug = false;

	/**
	 * Contructor por defecto.
	 */
	public HttpClient() {
	}

	/**
	 * @return
	 */
	public static HttpClient make() {
		return new HttpClient();
	}

	/**
	 * Establece si debe aplicarse el modo de depuración.
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Establece el id de sesion utilizado.
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Establece el id de sesion desde un respuesta.
	 * 
	 * @param response
	 */
	public void setSessionId(Response response) {
		var sessionId = response.getCookies().get(SESSION_ID_KEY);
		if (null != sessionId) {
			setSessionId(sessionId.getValue());
		}
	}

	/**
	 * Envia una peticion al servidor.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Response post(String url) throws Exception {
		return post(url, null);
	}
	
	/**
	 * Realiza una peticion POST a partir de un endpoint
	 * 
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	public Response post(Endpoint endpoint) throws Exception {
		return post(endpoint.toString());
	}
	
	/**
	 * Realiza una peticion POST a partir de un endpoint pasando valores 
	 * en formato JSON.
	 * 
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	public Response post(Endpoint endpoint, Object entity) throws Exception {
		return post(endpoint.toString(), entity);
	}

	/**
	 * Realiza una peticion POST a partir de una ruta pasando valores
	 * en formato JSON.
	 * 
	 * @param route
	 * @return
	 * @throws Exception
	 */
	public Response post(Route route, Object entity) throws Exception {
		return post(Endpoint.of(route), entity);
	}

	/**
	 * Envía una peticion al servidor con el método POST.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Response post(String url, Object entity) throws Exception {

		if (debug) {
			Debug.debug("POST: ", url, entity);
		}

		var client = this.getClient();
		WebTarget webTarget = client.target(url);

		Entity<Object> postData = null;
		if (null != entity) {
			postData = Entity.entity(entity, MediaType.APPLICATION_JSON);
		}

		Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);

		updateCookies(builder);

		// Recuperar y actualizar el id de sesion
		// para futuras peticiones.
		var response = builder.post(postData);
		setSessionId(response);

		return response;
	}

	/**
	 * Realiza una peticion GET a partir de un endpoint
	 * 
	 * @param endpoint
	 * @return
	 * @throws Exception
	 */
	public Response get(Endpoint endpoint) throws Exception {
		return get(endpoint.toString());
	}

	/**
	 * Realiza una peticion GET a partir de una ruta.
	 * 
	 * @param route
	 * @return
	 * @throws Exception
	 */
	public Response get(Route route) throws Exception {
		return get(Endpoint.of(route));
	}

	/**
	 * Envía una peticion al servidor con el método GET.
	 * 
	 * @param url
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Response get(String url) throws Exception {

		if (debug) {
			Debug.debug("GET: ", url);
		}

		var client = this.getClient();
		WebTarget webTarget = client.target(url);
		Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);

		updateCookies(builder);

		// Recuperar y actualizar el id de sesion
		// para futuras peticiones.
		var response = builder.get();
		setSessionId(response);

		return response;
	}

	/**
	 * Establece las cookies utilizadas en una petición.
	 * 
	 * @param builder
	 */
	public void updateCookies(Invocation.Builder builder) {
		if (sessionId != null) {
			builder.cookie(SESSION_ID_KEY, sessionId);
		}
	}

	/**
	 * @param username
	 * @return
	 */
	public static Option<HttpClient> forUser() {
		return forUser(UsernameFactory.next());
	}

	/**
	 * @param username
	 * @return
	 */
	public static Option<HttpClient> forUser(String username) {
		var http = HttpClient.make();
		var form = new LoginForm(username);
		try {
			var endpoint = Endpoint.of(Route.JUGADOR_LOGIN);
			var response = http.post(endpoint, form);
			if (ResponseUtil.isOk(response)) {
				return Option.of(http);
			}
		} catch (Exception e) {
			Debug.debug(e.getMessage());
		}
		return Option.none();
	}

	/**
	 * @return Resuelve un cliente para ser utilizado en las peticiones.
	 */
	private Client getClient() {
		if (null == client) {
			client = ClientBuilder.newClient();
		}
		return client;
	}
}