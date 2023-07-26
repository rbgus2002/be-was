package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

public class StaticFileResolver {

	private final List<String> resourcePathList;

	public StaticFileResolver() {
		resourcePathList = new ArrayList<>();
		resourcePathList.add("src/main/resources/static");
		resourcePathList.add("src/main/resources/templates");
	}

	public File findFile(String url) {
		File file;
		for (String resourcePath : resourcePathList) {
			file = new File(resourcePath + url);
			if (file.exists() && file.isFile()) {
				return file;
			}
		}
		return null;
	}

	public HttpResponse resolve(String url) throws IOException {
		File file = findFile(url);
		if (file == null) {
			return null;
		}
		return HttpResponse.builder()
			.status(HttpStatus.OK)
			.body(file.toPath())
			.build();
	}

}
