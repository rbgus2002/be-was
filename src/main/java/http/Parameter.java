package http;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private Map<String, String> parameters = new HashMap<>();

	public void put(String key, String value) {
		parameters.put(key, value);
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}
}
