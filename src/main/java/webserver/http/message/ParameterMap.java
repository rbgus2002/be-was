package webserver.http.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParameterMap {

	private final Map<String, String> map = new HashMap<>();

	public void add(String key, String value) {
		map.put(key, value);
	}

	public String getValue(String key) {
		return map.get(key);
	}

	public Set<Map.Entry<String, String>> getEntrySet() {
		return map.entrySet();
	}
}
