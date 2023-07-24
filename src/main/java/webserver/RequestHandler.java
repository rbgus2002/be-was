package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.AnnotationMap;
import http.HttpRequest;
import http.HttpResponse;
import http.statusline.StatusCode;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final String REDIRECT = "redirect:";
	private static final String TEMPLATES_PATH = "src/main/resources/templates/";
	private static final String STATIC_PATH = "src/main/resources/static";

	private final Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			// BufferedReader 파싱해 HttpRequest를 생성
			HttpRequest httpRequest = new HttpRequest(reader);
			logger.debug("{} httpRequest created : {}", httpRequest.getMethod(), httpRequest.getPath());

			//
			HttpResponse httpResponse = handleRequest(httpRequest);
			httpResponse.response(out);

		} catch (IOException | ReflectiveOperationException | IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}

	private HttpResponse handleRequest(final HttpRequest httpRequest) throws
		ReflectiveOperationException,
		IOException,
		IllegalArgumentException {
		HttpResponse httpResponse = new HttpResponse(httpRequest);
		String path = runController(httpRequest, httpResponse);
		if (path.contains(REDIRECT)) {
			return redirectHttpResponse(httpResponse, path);
		}
		return addFile(httpResponse, path);
	}

	private HttpResponse redirectHttpResponse(final HttpResponse httpResponse, final String path) {
		httpResponse.setRedirect(path.replace("redirect:", ""), StatusCode.FOUND);
		return httpResponse;
	}

	private String runController(final HttpRequest httpRequest, final HttpResponse httpResponse) throws
		InvocationTargetException,
		IllegalAccessException {
		String path = httpRequest.getPath();
		if (AnnotationMap.exists(httpRequest.getMethod(), httpRequest.getEndpoint())) {
			path = AnnotationMap.run(httpRequest.getMethod(), httpRequest.getEndpoint(), httpRequest, httpResponse);
		}
		return path;
	}

	private HttpResponse addFile(final HttpResponse httpResponse, final String path) throws
		IOException,
		IllegalArgumentException {
		httpResponse.addFile(getValidPath(path));
		logger.debug("{} added", path);
		return httpResponse;
	}

	private Path getValidPath(final String path) throws IllegalArgumentException {
		Path templatePath = new File(TEMPLATES_PATH + path).toPath();
		Path staticPath = new File(STATIC_PATH + path).toPath();
		logger.debug("{} path created", path);

		if (Files.exists(templatePath)) {
			return templatePath;
		}
		if (Files.exists(staticPath)) {
			return staticPath;
		}

		throw new IllegalArgumentException("지원하지 않는 경로를 입력했습니다.");
	}
}
