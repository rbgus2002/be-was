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
import webserver.http.utils.HttpMessageParser;
import webserver.http.utils.HttpMessageWriter;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private final Socket connection;
	private final FrontController frontController = FrontController.getInstance();

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream inputStream = connection.getInputStream();
			 DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
			// Request & Response 객체 생성
			HttpRequest request = HttpMessageParser.parseHttpRequest(inputStream);
			if (request == null) {
				return;
			}
			// 요청 처리
			HttpResponse response = frontController.service(request);
			// 응답 전송
			HttpMessageWriter.sendResponse(outputStream, response);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
