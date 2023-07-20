package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

public class FrontController {

	FileMapper fileMapper = new FileMapper();

	public void service(HttpRequest request, HttpResponse response) {
		// 파일 찾기
		String path = request.getUrlPath();
		File file = fileMapper.findFile(path);
		try {
			if (file == null) {
				throw new IllegalArgumentException("Wrong path.");
			}
			Path filePath = file.toPath();
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

}
