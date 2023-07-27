package webserver.http.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaderFields {

	private final Map<String, String> headerFields = new HashMap<>();

	public void setHeaderField(String key, String value) {
		headerFields.put(key, value);
	}

	public String getValue(String key) {
		return headerFields.get(key);
	}

	public void removeHeaderField(String key) {
		headerFields.remove(key);
	}

	public Set<Map.Entry<String, String>> getAllFields() {
		return headerFields.entrySet();
	}

	public String getContentLength() {
		return getValue("Content-Length");
	}
}
