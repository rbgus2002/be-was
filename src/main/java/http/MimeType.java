package http;

import java.util.Set;

public enum MimeType {
	CSS("text/css", Set.of(".css")),
	HTML("text/html", Set.of(".html")),
	JS("text/javascript", Set.of(".js")),
	TXT("text/plain", Set.of(".txt", ".text")),
	JPG("image/jpg", Set.of(".jpg", ".jpe", ".jpeg")),
	ICO("image/vnd.microsoft.icon", Set.of(".ico")),
	WOFF("font/woff", Set.of(".woff")),
	TTF("font/ttf", Set.of(".ttf"));

	public final String extension;
	private final Set fileType;

	MimeType(final String extension, final Set fileTypes) {
		this.extension = extension;
		this.fileType = fileTypes;
	}

	public static MimeType typeOf(String extension) throws IllegalArgumentException {
		for (MimeType type : MimeType.values()) {
			if (type.fileType.contains(extension)) {
				return type;
			}
		}
		throw new IllegalArgumentException("지원되지 않는 형식의 파일입니다");
	}
}
