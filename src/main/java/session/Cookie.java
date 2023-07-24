package session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookie {
	private Map<String, String> cookies = new HashMap<>();
	public final String COOKIE_DELIMITER = "; ";

	private Cookie() {
	}

	public static Cookie newCookie() {
		return new Cookie();
	}

	public void add(final String cookieName, final String cookieValue) {
		cookies.put(cookieName, cookieValue);
	}

	public String toHeaderValue() {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> headerValues = new ArrayList<>();
		for (String cookieName : cookies.keySet()) {
			headerValues.add(stringBuilder.append(cookieName).append("=").append(cookies.get(cookieName)).toString());
			stringBuilder.setLength(0);
		}
		return String.join(COOKIE_DELIMITER, headerValues);
	}
}
