package webserver.http.utils;

import static webserver.http.utils.HttpConstant.*;

import java.util.List;

public class StringUtils {

	public static String joinStatusLine(List<String> tokens) {
		return String.join(SINGLE_SPACE, tokens) + CRLF;
	}

	public static String joinHeaderFields(String key, String value) {
		return key + COLON + value + CRLF;
	}
}
