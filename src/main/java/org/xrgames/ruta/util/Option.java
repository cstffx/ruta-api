package org.xrgames.ruta.util;

public class Option<T> {
	private Some<T> some = null;
	private None none = null;

	public Option(Some<T> some) {
		this.some = some;
	}

	public Option(None none) {
		this.none = none;
		this.some = null;
	}

	public static <T> Option<T> of(T value) {
		return new Option<T>(new Some<T>(value));
	}

	public static <T> Option<T> none() {
		return new Option<T>(new None());
	}

	public T unwrap() throws Exception {
		if (isNone()) {
			throw new Exception("Intento de obtener un valor vacio.");
		}
		return some.unbox();
	}

	public boolean isSome() {
		return !isNone();
	}

	public boolean isNone() {
		return none != null;
	}
}
