package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.client.ActiveClient;

public class ActiveClientTest {

	static final String USERNAME = "some_username";

	@Test
	public void loginTest() throws Exception {
		var client = new ActiveClient();
		var login = client.auth(USERNAME);
		
		assertFalse(login.isErr());
		assertTrue(login.isSome());
		
		var token = login.getToken();
		assertTrue(token.is(USERNAME));
		
		// Comprobar permanencia de la sesión.
		for(int i = 0; i < 3; i++) {
			var res = client.get(Endpoint.build("status"));
			assertTrue(res.getStatus() == 200);	
		}
				
		var logoutResult = client.logout();
		assertTrue(logoutResult);
	}
}