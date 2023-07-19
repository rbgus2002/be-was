package webserver.response;

import java.io.File;
import java.nio.file.Path;

public class HttpFileHandler {

	private static final String TEMPLATES_PATH = "src/main/resources/templates";
	private static final String STATIC_PATH = "src/main/resources/static";

	public Path getFilePath(String resourcePath) {
		return new File(getResourcePath(resourcePath) + resourcePath).toPath();
	}

	private String getResourcePath(String resourcePath) {
		File file = new File(TEMPLATES_PATH + resourcePath);
		if (file.isFile()) {
			return TEMPLATES_PATH;
		}
		return STATIC_PATH;
	}

	public boolean isExistResource(String resourcePath) {
		File file = new File(TEMPLATES_PATH + resourcePath);
		if (file.isFile())
			return true;

		file = new File(STATIC_PATH + resourcePath);
		return file.isFile();
	}
}
