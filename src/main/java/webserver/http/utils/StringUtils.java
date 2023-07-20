package webserver.http.utils;

import java.util.List;

public class StringUtils {

	public static String joinStatusLine(List<String> tokens) {
		return String.join(" ", tokens) + "\r\n";
	}

	public static String joinHeaderFields(String key, String value) {
		return key + ": " + value + "\r\n";
	}
}
