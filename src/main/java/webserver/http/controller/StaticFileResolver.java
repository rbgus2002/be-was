package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

public class StaticFileResolver {

	public void resolve(HttpResponse response, File file) {
		Path filePath = file.toPath();
		try {
			byte[] body = Files.readAllBytes(filePath);
			// 응답 메세지 작성하기
			response.setStatus(HttpStatus.OK);
			response.setBody(body, Files.probeContentType(filePath));
		} catch (IOException exception) {
			byte[] body = exception.getMessage().getBytes();
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setBody(body, "text/plain");
		}
	}

}
