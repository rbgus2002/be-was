package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import controller.Controller;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

public class FrontController {

	ControllerMapper controllerMapper = new ControllerMapper();
	ViewResolver viewResolver = new ViewResolver();

	public void service(HttpRequest request, HttpResponse response) {
		byte[] body = new byte[0];
		// 1. url을 받고 이에 따른 컨트롤러를 리턴
		Controller controller = controllerMapper.findController(request.getUrlPath());
		// 2. 컨트롤러를 실행
		try {
			if (controller == null) {
				throw new IOException();
			}
			String viewName = controller.process(request, response);
			// 3. 컨트롤러에서 뷰 네임을 리턴하면, 이를 물리 이름으로 변환하는 메소드
			String viewPath = viewResolver.resolveViewName(viewName);
			// 4. 파일을 읽고 dos로 출력(응답 전송)
			body = Files.readAllBytes(new File(viewPath).toPath());
			response.writeStatus(HttpStatus.OK);
		} catch (IOException exception) {
			response.writeStatus(HttpStatus.NOT_FOUND);
		}
		response.writeBody(body);
	}

}
