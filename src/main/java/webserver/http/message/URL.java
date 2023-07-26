package webserver.http.message;

import java.util.Map;

public class URL {

	private final String path;
	private final Map<String, String> parameterMap;

	public URL(String path, Map<String, String> parameterMap) {
		this.path = path;
		this.parameterMap = parameterMap;
	}

	public String getPath() {
		return path;
	}

	public String getParameterValue(String key) {
		return parameterMap.get(key);
	}
}
