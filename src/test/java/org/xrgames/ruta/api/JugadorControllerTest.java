package org.xrgames.ruta.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.xrgames.ruta.services.Endpoint;
import org.xrgames.ruta.services.LoginFormData;
import org.xrgames.ruta.services.ResponseUtil;
import org.xrgames.ruta.services.client.ActiveClient;

public class JugadorControllerTest {
	
	@Test
	public void loginTest() {
		var client = new ActiveClient();
		var formData = new LoginFormData("test");
		var res = client.post(Endpoint.build(Endpoint.ENDPOINT_LOGIN), formData);
		
		assertTrue(ResponseUtil.isOk(res));
		var cookies = res.getCookies();
		
		assertTrue(cookies.containsKey(ActiveClient.SESSION_ID_KEY));
		
		var sessionIdValue = cookies.get(ActiveClient.SESSION_ID_KEY).getValue();
		assertNotNull(sessionIdValue);
	}
	
	@Test
	public void logoutTest() {
		
	}
	
	@Test
	public void joinTest() {
	}
	
	
}