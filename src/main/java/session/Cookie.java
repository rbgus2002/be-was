package session;

public class Cookie {
	private String cookieName;
	private String cookieValue;

	private Cookie(String cookieName, String cookieValue) {
		this.cookieName = cookieName;
		this.cookieValue = cookieValue;
	}

	public static Cookie newCookie(String cookieName, String cookieValue) {
		return new Cookie(cookieName, cookieValue);
	}

	public String toHeaderValue() {
		return new StringBuilder().append(cookieName).append("=").append(cookieValue).toString();
	}
}
