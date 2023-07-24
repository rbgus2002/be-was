package http.statusline;

public enum HttpMethod {
	GET, POST, PUT, DELETE, CONNECT, HEAD, OPTIONS, TRACE, NOT_ANNOUNCED;

	public static HttpMethod typeOf(String stringType) {
		for (HttpMethod type : HttpMethod.values()) {
			if (type.name().equals(stringType)) {
				return type;
			}
		}
		return NOT_ANNOUNCED;
	}
}
