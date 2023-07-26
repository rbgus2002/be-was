package webserver.http.message;

public class URL {

	private final String path;
	private final ParameterMap parameterMap;

	public URL(String path, ParameterMap parameterMap) {
		this.path = path;
		this.parameterMap = parameterMap;
	}

	public String getPath() {
		return path;
	}

	public String getParameterValue(String key) {
		return parameterMap.getValue(key);
	}
}
