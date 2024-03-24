package org.xrgames.ruta.util;

import java.text.MessageFormat;

public class UsernameFactory {
	public static int ordinal = 0;
	public static String next() {
		return MessageFormat.format("user{0}", ordinal++);
	}
}
