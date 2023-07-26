package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.controller.FrontController;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream inputStream = connection.getInputStream();
			 DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
			// Request 로그 출력
			// Request & Response 객체 생성
			HttpRequest request = HttpRequest.from(inputStream);
			HttpResponse response = HttpResponse.from(request, outputStream);
			// 요청 처리
			FrontController frontController = new FrontController();
			frontController.service(request, response);
			// 응답 전송
			response.sendResponse(outputStream);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	// private void preProcess(InputStream inputStream) throws IOException {
	// 	// TODO: 전체 로그 출력
	// }
}
