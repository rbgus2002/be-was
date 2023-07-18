package webserver.response;

import java.util.HashMap;
import java.util.Map;

import webserver.utils.HttpHeader;

public class HttpResponseHeader {

	private final Map<HttpHeader, String> headers = new HashMap<>();

	public void addHeader(HttpHeader header, String value) {
		headers.put(header, value);
	}

	public String getAllHeader() {
		StringBuilder sb = new StringBuilder();

		for (HttpHeader key : headers.keySet()) {
			final String value = headers.get(key);

			sb.append(key.getType())
				.append(": ")
				.append(value)
				.append("\r\n");
		}

		return sb.toString();
	}
}
