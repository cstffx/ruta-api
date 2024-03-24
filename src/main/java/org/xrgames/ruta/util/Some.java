package org.xrgames.ruta.util;

public class Some<T> {
	
	private final T value;	
	
	public Some(T value){
		this.value = value;
	}
	
	public static <T> Some<T> of(T value) {
		return new Some<T>(value);
	}
	
	public T unbox() {
		return value;
	}
}
