package webserver.utils;

public enum HttpMimeType {
	HTML("text/html", "html"),
	CSS("text/css", "css"),
	JAVASCRIPT("application/javascript", "js"),
	PNG("image/png", "png"),
	JPEG("image/jpeg", "jpg"),
	ICON("image/x-icon", "ico"),
	NOTING("*/*", "nothing");

	private final String contentType;
	private final String name;

	HttpMimeType(final String contentType, final String name) {
		this.contentType = contentType;
		this.name = name;
	}
}
