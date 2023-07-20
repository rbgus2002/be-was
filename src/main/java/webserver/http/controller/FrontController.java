package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

public class FrontController {

	private final List<String> resourceDirectoryPathList;
	ControllerMapper controllerMapper = new ControllerMapper();
	ViewResolver viewResolver = new ViewResolver();

	public FrontController() {
		resourceDirectoryPathList = new ArrayList<>();
		resourceDirectoryPathList.add("src/main/resources/static");
		resourceDirectoryPathList.add("src/main/resources/templates");
	}

	public void service(HttpRequest request, HttpResponse response) {
		// 파일 찾기
		String path = request.getUrlPath();
		Path filePath = findFilePath(path);
		try {
			if (filePath == null) {
				throw new IllegalArgumentException("Wrong path.");
			}
			byte[] body = Files.readAllBytes(filePath);
			// 응답 메세지 작성하기
			response.writeStatus(HttpStatus.OK);
			response.writeBody(body, Files.probeContentType(filePath));
		} catch (IllegalArgumentException | IOException exception) {
			byte[] body = exception.getMessage().getBytes();
			response.writeStatus(HttpStatus.NOT_FOUND);
			response.writeBody(body, "text/plain");
		}
	}

	private Path findFilePath(String path) {
		File file;
		for (String resourceDirectoryPath : resourceDirectoryPathList) {
			file = new File(resourceDirectoryPath + path);
			if (file.exists() && file.isFile()) {
				return file.toPath();
			}
		}
		return null;
	}

}
