package org.xrgames.ruta.api;

import org.xrgames.ruta.services.client.HttpClient;

public class CreateJuegoResult {
	public String juegoId;
	public HttpClient http;

	public CreateJuegoResult() {

	}

	public CreateJuegoResult(String juegoId, HttpClient http) {
		this.juegoId = juegoId;
		this.http = http;
	}
}
