package webserver.view;

import java.util.Map;

import com.google.common.collect.Maps;

public class Model {
	private Map<String, Object> attributes = Maps.newHashMap();

	public void addAttribute(final String key, final Object value) {
		attributes.put(key, value);
	}

	public Object getAttribute(final String key) {
		return attributes.get(key);
	}

	public boolean containsAttribute(final String key) {
		return attributes.containsKey(key);
	}
}
