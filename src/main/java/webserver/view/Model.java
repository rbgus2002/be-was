package webserver.view;

import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.Maps;

public class Model {
	private Map<String, String> attributes = Maps.newHashMap();

	public void addAttribute(final String key, final String value) {
		attributes.put(key, value);
	}

	public String getAttribute(final String key) {
		return attributes.get(key);
	}

	public boolean containsAttribute(final String key) {
		return attributes.containsKey(key);
	}
}
