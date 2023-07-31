package webserver.http.header;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Header {
	private Map<String, String> header = new HashMap<>();

	public String getHeaderLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : header.keySet()) {
			stringBuilder.append(key + HeaderConst.HEADER_DELIMITER + header.get(key) + HeaderConst.CRLF);
		}
		stringBuilder.append(HeaderConst.CRLF);
		return stringBuilder.toString();
	}

	public void addHeader(String key, String value) {
		header.put(key, value);
	}

	public String getCookieValue(String cookieName) throws NoSuchElementException {
		if (!header.containsKey(HeaderConst.COOKIE)) {
			throw new NoSuchElementException("쿠키가 존재하지 않습니다.");
		}
		String cookieLines = header.get(HeaderConst.COOKIE);
		for (String cookieLine : cookieLines.split("; ")) {
			if (cookieLine.split("=")[0].equals(cookieName)) {
				return cookieLine.split("=")[1];
			}
		}
		System.out.println(HeaderConst.COOKIE);
		System.out.println(cookieLines);
		throw new NoSuchElementException("쿠키 이름에 해당하는 쿠키가 존재하지 않습니다.");
	}

	public boolean containsLength() {
		return header.containsKey(HeaderConst.CONTENT_LENGTH);
	}

	public int getContentLength() {
		return Integer.parseInt(header.get(HeaderConst.CONTENT_LENGTH));
	}
}
