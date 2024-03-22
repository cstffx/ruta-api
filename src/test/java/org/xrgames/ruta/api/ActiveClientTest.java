package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.client.ActiveClient;

public class ActiveClientTest extends RestTest {

	static final String USERNAME = "some_username";

	@Test
	public void loginTest() {
		var client = new ActiveClient(USERNAME);
		var login = client.auth();
		
		assertFalse(login.isErr());
		
		var token = login.getToken();
		
		assertTrue(token.is(USERNAME));
		
		var logoutResult = client.logout();
		
		assertTrue(logoutResult);
	}
}