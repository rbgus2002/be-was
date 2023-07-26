package webserver.http.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaderFields {

	private final Map<String, String> headerFields = new HashMap<>();

	public void add(String key, String value) {
		headerFields.put(key, value);
	}

	public String getValue(String key) {
		return headerFields.get(key);
	}

	public Set<Map.Entry<String, String>> getAllFields() {
		return headerFields.entrySet();
	}

	public String getContentType() {
		return getValue("Content-Type");
	}

	public String getContentLength() {
		return getValue("Content-Length");
	}
}
