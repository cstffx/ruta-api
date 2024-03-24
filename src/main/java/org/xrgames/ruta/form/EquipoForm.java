package org.xrgames.ruta.form;

import jakarta.validation.constraints.NotNull;

public class EquipoForm {
	
	@NotNull
	public String nombre;
	
	public EquipoForm() {
		
	}
	
	public EquipoForm(String nombre){
		this.nombre = nombre;
	}
}