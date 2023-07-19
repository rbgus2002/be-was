package webserver.http.message;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaderFields {

	private final Map<String, String> headerFields;

	public HttpHeaderFields() {
		headerFields = new HashMap<>();
	}

	public void addHeaderField(String key, String value) {
		headerFields.put(key, value);
	}
}
