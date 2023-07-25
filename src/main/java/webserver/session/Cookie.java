package webserver.session;

import java.util.EnumMap;
import java.util.Map;

public class Cookie {

	private final Map<CookieType, String> attributes = new EnumMap<>(CookieType.class);
	private final String name;
	private final String value;

	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}

	private Cookie(Builder builder) {
		this.name = builder.name;
		this.value = builder.value;

		if (!builder.path.isBlank()) {
			attributes.put(CookieType.PATH, builder.path);
		}
		if (builder.httpOnly != null) {
			attributes.put(CookieType.HTTP_ONLY, builder.httpOnly.toString());
		}
		if (builder.maxAge != null) {
			attributes.put(CookieType.MAX_AGE, builder.maxAge.toString());
		}
		if (!builder.domain.isBlank()) {
			attributes.put(CookieType.DOMAIN, builder.domain);
		}

	}

	public String convertToHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("Set-Cookie: ").append(this.name)
			.append("=")
			.append(this.value)
			.append("; ");

		for (CookieType cookieType : attributes.keySet()) {
			if (cookieType == CookieType.HTTP_ONLY)
				sb.append(CookieType.HTTP_ONLY.getName()).append("; ");
			else
				sb.append(cookieType.getName()).append("=").append(attributes.get(cookieType))
					.append("; ");
		}

		return sb.toString();
	}

	public static class Builder {
		private final String name;
		private final String value;

		private String path = "";
		private Long maxAge = null;
		private String domain = "";
		private Boolean httpOnly = null;

		public Builder(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public Builder path(String val) {
			path = val;
			return this;
		}

		public Builder maxAge(Long val) {
			maxAge = val;
			return this;
		}

		public Builder domain(String val) {
			domain = val;
			return this;
		}

		public Builder httpOnly(Boolean val) {
			httpOnly = val;
			return this;
		}

		public Cookie build() {
			return new Cookie(this);
		}
	}
}
