package http.header;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import session.Cookie;

public class Header {
	public static final String HEADER_DELIMITER = ": ";
	private static final String CRLF = "\r\n";
	private Map<String, String> header = new HashMap<>();

	public String getHeaderLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : header.keySet()) {
			stringBuilder.append(key + HEADER_DELIMITER + header.get(key) + CRLF);
		}
		stringBuilder.append(CRLF);
		return stringBuilder.toString();
	}

	public void addHeader(String key, String value) {
		header.put(key, value);
	}

	public boolean containsCookie() {
		return header.containsKey("Cookie");
	}

	public String getCookieValue(String cookieName) throws NoSuchElementException {
		String cookieLines = header.get("Cookie");
		for (String cookieLine : cookieLines.split("; ")) {
			if (cookieLine.split("=")[0].equals(cookieName)) {
				return cookieLine.split("=")[1];
			}
		}
		throw new NoSuchElementException("쿠키 이름에 해당하는 쿠키가 존재하지 않습니다.");
	}

	public boolean containsLength() {
		return header.containsKey("Content-Length");
	}

	public int getContentLength() {
		return Integer.parseInt(header.get("Content-Length"));
	}
}
