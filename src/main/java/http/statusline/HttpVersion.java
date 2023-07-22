package http.statusline;

public enum HttpVersion {
	HTTP_1_1("HTTP/1.1"),
	HTTP_2("HTTP/2.0");
	public final String representation;
	HttpVersion(String representation) {
		this.representation = representation;
	}
	public static HttpVersion versionOf(String stringVersion) throws IllegalArgumentException {
		for (HttpVersion version : HttpVersion.values()) {
			if (version.representation.equals(stringVersion)) {
				return version;
			}
		}
		throw new IllegalArgumentException("지원되지 않는 버전의 HTTP 요청입니다.");
	}
}
