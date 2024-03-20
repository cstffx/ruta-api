package org.xrgames.ruta.api;

import org.xrgames.ruta.services.Endpoint;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestTest {
	
	WebTarget target(String path){
		Client client = ClientBuilder.newClient();
		return client.target(Endpoint.build(path));
	}
	
	WebTarget target(String path, Client client){
		return client.target(Endpoint.build(path));
	}
	
	Invocation.Builder builder(String path){
		return target(path).request(MediaType.APPLICATION_JSON);
	}
	
	Response get(String path) {
		return builder(path).get(Response.class);
	}
}