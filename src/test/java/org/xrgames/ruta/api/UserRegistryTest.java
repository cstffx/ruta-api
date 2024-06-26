package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.UserRegistry;

public class UserRegistryTest {

	@Test
	public void addTest() {
		var reg = new UserRegistry();
		assertTrue(reg.add("username1").isOk());
		assertTrue(reg.add("username2").isOk());

		// Agregar un usuario duplicado debe fallar
		assertTrue(reg.add("username1").isErr());
	}
}