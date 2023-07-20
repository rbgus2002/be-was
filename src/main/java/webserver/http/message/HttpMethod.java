package webserver.http.message;

public enum HttpMethod {
	GET("GET"),
	POST("POST");

	private final String method;

	HttpMethod(String method) {
		this.method = method;
	}

	public static HttpMethod from(String methodString) {
		for (HttpMethod value : HttpMethod.values()) {
			if (value.method.equals(methodString)) {
				return value;
			}
		}
		throw new IllegalArgumentException();
	}
}

