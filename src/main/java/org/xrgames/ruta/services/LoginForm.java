package org.xrgames.ruta.services;

import jakarta.validation.constraints.NotEmpty;

public class LoginForm {
	
	@NotEmpty
	public String username = "";
	
	public LoginForm() {
		
	}
	
	public LoginForm(String username){
		this.username = username;
	}
}