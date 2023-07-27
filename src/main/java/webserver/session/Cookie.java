package webserver.session;

public class Cookie {

	private final String key;
	private final String value;
	private final Integer maxAge;

	private Cookie(String key, String value, Integer maxAge) {
		this.key = key;
		this.value = value;
		this.maxAge = maxAge;
	}

	public static Cookie of(String key, String value, Integer maxAge) {
		return new Cookie(key, value, maxAge);
	}

	public String toCookieString() {
		StringBuilder sb = new StringBuilder();
		sb.append(key).append("=").append(value)
			.append("; Path=/");
		if (maxAge != null) {
			sb.append("; Max-Age=").append(maxAge);
		}
		return sb.toString();
	}
}
