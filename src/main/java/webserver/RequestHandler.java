package webserver;

import static webserver.http.utils.HttpConstant.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.controller.FrontController;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.utils.StringUtils;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String RESOURCES_PATH = "src/main/resources/templates";

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
			preProcess(inputStream);
			// Request & Response 객체 생성
			HttpRequest request = HttpRequest.from(inputStream);
			HttpResponse response = HttpResponse.from(request);
			// 요청 처리
			FrontController frontController = new FrontController();
			frontController.service(request, response);
			// 응답 전송
			sendResponse(outputStream, response);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void sendResponse(DataOutputStream outputStream, HttpResponse response) {
		writeResponseHeader(outputStream, response);
		writeResponseBody(outputStream, response);
	}

	private void writeResponseHeader(DataOutputStream outputStream, HttpResponse response) {
		try {
			outputStream.writeBytes(StringUtils.joinStatusLine(response.getStatusLineTokens()));
			for (Map.Entry<String, String> headerField : response.getHeaderFieldsEntry()) {
				outputStream.writeBytes(StringUtils.joinHeaderFields(headerField.getKey(), headerField.getValue()));
			}
			outputStream.writeBytes(CRLF);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void writeResponseBody(DataOutputStream outputStream, HttpResponse response) {
		try {
			byte[] body = response.getBody();
			outputStream.write(body, 0, body.length);
			outputStream.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void preProcess(InputStream inputStream) throws IOException {
		// TODO: 전체 로그 출력
	}
}
