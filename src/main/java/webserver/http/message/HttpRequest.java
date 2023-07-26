package webserver.http.message;

public class HttpRequest {

	private final HttpMethod httpMethod;
	private final URL url;
	private final ParameterMap headerFields;
	private final ParameterMap bodyParameters;

	public HttpRequest(HttpMethod httpMethod, URL url, ParameterMap headerFields, ParameterMap bodyParameters) {
		this.httpMethod = httpMethod;
		this.url = url;
		this.headerFields = headerFields;
		this.bodyParameters = bodyParameters;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getUrlPath() {
		return url.getPath();
	}

	public String getHttpVersion() {
		return "HTTP/1.1";
	}

	public ParameterMap getHeaderFields() {
		return headerFields;
	}

	public ParameterMap getBodyParameters() {
		return bodyParameters;
	}

	public String getParameterValue(String key) {
		return url.getParameterValue(key);
	}

}
