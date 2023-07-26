package webserver.http.message;

import java.util.Arrays;

public enum HttpMethod {
	GET("GET"),
	POST("POST");

	private final String name;

	HttpMethod(String name) {
		this.name = name;
	}

	public static HttpMethod from(String methodString) {
		return Arrays.stream(HttpMethod.values())
			.filter(value -> value.name.equals(methodString))
			.findAny()
			.orElseThrow(() -> new RuntimeException(HttpStatus.METHOD_NOT_ALLOWED.getMessage()));
	}

	public String getName() {
		return name;
	}
}

