package webserver.session;

public class Cookie {

	private String name;
	private String value;
	private String path;
	private Long maxAge;
	private String domain;
	private Boolean httpOnly;

	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
		this.path = "";
		this.maxAge = 0L;
		this.domain = "";
		this.httpOnly = false;
	}

	private Cookie(Builder builder) {
		this.name = builder.name;
		this.value = builder.value;
		this.path = builder.path;
		this.maxAge = builder.maxAge;
		this.domain = builder.domain;
		this.httpOnly = builder.httpOnly;
	}

	public static class Builder {
		private final String name;
		private final String value;

		private String path;
		private Long maxAge;
		private String domain;
		private Boolean httpOnly;

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
