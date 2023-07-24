package webserver.session;

public enum CookieType {
	PATH("Path"),
	MAX_AGE("Max-Age"),
	DOMAIN("Domain"),
	HTTP_ONLY("HttpOnly"),
	SAME_SITE("SameSite");

	private final String name;
	CookieType(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
