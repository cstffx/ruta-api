package org.xrgames.ruta.util;

public class Result<T, E extends Throwable> {

	private T object = null;
	private E err;

	public Result(T object, E err) {
		this.object = object;
		this.err = err;
	}

	public Result(T object) {
		this.object = object;
		this.err = null;
	}

	public Result(E err) {
		this.object = null;
		this.err = err;
	}

	public static <T> Result<T, Exception> err(String err) {
		return new Result<T, Exception>(null, new Exception(err));
	}

	public static <T, E extends Throwable> Result<T, E> of(E err) {
		return new Result<T, E>(err);
	}

	public static <T, E extends Throwable> Result<T, E> of(T object) {
		return new Result<T, E>(object);
	}

	public boolean isErr() {
		return null != err;
	}

	public boolean isOk() {
		return !isErr();
	}

	public T unwrap() throws E {
		if (isErr()) {
			throw err;
		}
		return object;
	}

	public E getError() {
		return err;
	}
}
