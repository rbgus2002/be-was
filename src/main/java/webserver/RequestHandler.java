package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.AnnotationMap;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private static final String TEMPLATES_PATH = "src/main/resources/templates/";
	private static final String STATIC_PATH = "src/main/resources/static";

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			HttpRequest httpRequest = new HttpRequest(reader);
			logger.debug("{} httpRequest created", httpRequest.getMethod());
			if (httpRequest.isGet()) {
				HttpResponse httpResponse = handleGetRequest(httpRequest);
				logger.debug("httpResponse created");
				httpResponse.response(out);
				logger.debug("httpResponse sended");
			}
		} catch (IOException | ReflectiveOperationException e) {
			logger.error(e.getMessage());
		}
	}

	private HttpResponse handleGetRequest(HttpRequest httpRequest) throws ReflectiveOperationException, IOException {
		String path = httpRequest.getPath();
		if (AnnotationMap.exists(HttpMethod.GET, httpRequest.getEndpoint())) {
			path = AnnotationMap.run(HttpMethod.GET, httpRequest.getEndpoint());
		}
		HttpResponse httpResponse = new HttpResponse(httpRequest);
		Path templatePath = new File(TEMPLATES_PATH + path).toPath();
		Path staticPath = new File(STATIC_PATH + path).toPath();
		logger.debug("{} path created", path);
		httpResponse.addFile(Files.exists(templatePath) ? templatePath : staticPath);
		logger.debug("{} added", path);
		return httpResponse;
	}
}
