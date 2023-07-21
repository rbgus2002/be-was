package http;

import java.util.HashMap;
import java.util.Map;

public class Header {
	private Map<String, String> header = new HashMap<>();

	public String getHeaderLines() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : header.keySet()) {
			stringBuilder.append(key + ": " + header.get(key) + "\r\n");
		}
		stringBuilder.append("\r\n");
		return stringBuilder.toString();
	}

	public void addHeader(String key, String value) {
		header.put(key, value);
	}
}
