package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpParameter {
	private Map<String, String> parameters = new HashMap<>();

	public void put(String key, String value) {
		parameters.put(key, value);
	}

	public String getParameter(String key) throws IllegalArgumentException {
		if (parameters.containsKey(key)) {
			return parameters.get(key);
		}
		throw new IllegalArgumentException("존재하지 않는 Parameter의 key입니다.");
	}
}
