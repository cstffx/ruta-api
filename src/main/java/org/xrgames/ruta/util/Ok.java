package org.xrgames.ruta.util;

public class Ok<T> {
	private T object = null;
	
	Ok(T object){
		this.object = object;
	}
	
	public Ok<T> of(T object) {
		return new Ok<T>(object);
	}
	
	public T unbox() {
		return object;
	}
}
