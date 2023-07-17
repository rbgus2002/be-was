package webserver.utils;

public enum HttpMethod {
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	PATCH("PATCH"),
	DELETE("DELETE"),
	EMPTY("EMPTY");

	private final String method;

	HttpMethod(String method) {
		this.method = method;
	}


}
