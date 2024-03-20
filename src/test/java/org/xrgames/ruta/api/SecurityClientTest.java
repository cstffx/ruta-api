package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.SecurityClient;

public class SecurityClientTest extends RestTest {

	static final String USERNAME = "some_username";

	@Test
	public void loginTest() {
		SecurityClient sc = new SecurityClient();
		var login = sc.login(USERNAME);
		assertTrue(login.isSome());
		assertTrue(login.token.is(USERNAME));
	}
}