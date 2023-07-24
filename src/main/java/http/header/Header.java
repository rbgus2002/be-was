package http.header;

import java.util.HashMap;
import java.util.Map;

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

	public boolean containsLength() {
		return header.containsKey("Content-Length");
	}

	public int getContentLength() {
		return Integer.parseInt(header.get("Content-Length"));
	}
}
