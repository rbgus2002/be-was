package webserver.utils;

import java.util.Arrays;

public enum HttpMimeType {
	HTML("text/html", "html"),
	CSS("text/css", "css"),
	JAVASCRIPT("application/javascript", "js"),
	PNG("image/png", "png"),
	JPEG("image/jpeg", "jpg"),
	ICON("image/x-icon", "ico"),
	PLAIN("text/plain", "txt"),
	NOTING("*/*", "nothing");

	public static final String CHARSET_UTF8 = ";charset=utf-8";
	private final String contentType;
	private final String name;

	HttpMimeType(final String contentType, final String name) {
		this.contentType = contentType;
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public String getName() {
		return name;
	}

	public String getCharsetUtf8() {
		return contentType + CHARSET_UTF8;
	}

	public static HttpMimeType valueOfResourcePath(String findPath) {
		final String[] token = findPath.split("\\.");
		final String path = token[token.length - 1];
		return Arrays.stream(values())
			.filter(type -> type.name.equals(path))
			.findAny()
			.orElse(HttpMimeType.NOTING);
	}
}
