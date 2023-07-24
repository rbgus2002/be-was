package webserver.container;

import java.util.Objects;

import webserver.http.message.HttpMethod;

public class UrlHttpMethodPair {

	private final String url;
	private final HttpMethod httpMethod;

	private UrlHttpMethodPair(String url, HttpMethod httpMethod) {
		this.url = url;
		this.httpMethod = httpMethod;
	}

	public static UrlHttpMethodPair of(String path, HttpMethod httpMethod) {
		return new UrlHttpMethodPair(path, httpMethod);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UrlHttpMethodPair that = (UrlHttpMethodPair)o;
		return Objects.equals(url, that.url) && httpMethod == that.httpMethod;
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, httpMethod);
	}
}
