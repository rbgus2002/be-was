package webserver.http.message;

import java.util.Map;

public class HttpRequestBody {

	private final Map<String, String> map;

	public HttpRequestBody(Map<String, String> map) {
		this.map = map;
	}

	public void add(String key, String value) {
		map.put(key, value);
	}

	public String getValue(String key) {
		return map.get(key);
	}

}
