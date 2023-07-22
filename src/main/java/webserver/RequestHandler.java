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
import http.statusline.HttpMethod;
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
			HttpRequest httpRequest = new HttpRequest(reader);
			logger.debug("{} httpRequest created : {}", httpRequest.getMethod(), httpRequest.getPath());
			if (httpRequest.isGet()) {
				HttpResponse httpResponse = handleGetRequest(httpRequest);
				logger.debug("httpResponse created");
				httpResponse.response(out);
				logger.debug("httpResponse sent");
			}
		} catch (IOException | ReflectiveOperationException | IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}

	private HttpResponse handleGetRequest(HttpRequest httpRequest) throws ReflectiveOperationException, IOException, IllegalArgumentException {
		String path = runController(httpRequest);
		if (path.contains(REDIRECT)) {
			return redirectHttpResponse(httpRequest, path);
		}
		return getHttpResponse(httpRequest, path);
	}

	private HttpResponse redirectHttpResponse(final HttpRequest httpRequest, final String path) {
		HttpResponse httpResponse = new HttpResponse(httpRequest);
		httpResponse.setRedirect(path.replace("redirect:", ""), StatusCode.FOUND);
		return httpResponse;
	}

	private String runController(final HttpRequest httpRequest) throws InvocationTargetException, IllegalAccessException {
		String path = httpRequest.getPath();
		if (AnnotationMap.exists(HttpMethod.GET, httpRequest.getEndpoint())) {
			path = AnnotationMap.run(HttpMethod.GET, httpRequest.getEndpoint(), httpRequest.getParameter());
		}
		return path;
	}

	private HttpResponse getHttpResponse(final HttpRequest httpRequest, final String path) throws IOException, IllegalArgumentException {
		HttpResponse httpResponse = new HttpResponse(httpRequest);
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
