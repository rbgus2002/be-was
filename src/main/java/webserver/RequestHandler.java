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

import annotations.DeclaredControllers;
import controllers.ErrorHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.header.MimeType;
import webserver.http.statusline.StatusCode;
import webserver.view.ModelView;
import webserver.view.ViewResolver;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final String REDIRECT = "redirect:";
	private static final String TEMPLATES_PATH = "src/main/resources/templates/";
	private static final String STATIC_PATH = "src/main/resources/static";
	private static final String ERROR_PAGE = "error.html";

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

		HttpResponse httpResponse = new HttpResponse();
		String path = httpRequest.getPath();
		ModelView modelView = ModelView.from(path);

		if (controllerExist(httpRequest)) {
			modelView = runController(httpRequest, httpResponse, modelView);
		}

		// 컨트롤러의 반환에 redirect:가 추가되어 있으면 리다이렉트 응답
		if (modelView.getPath().contains(REDIRECT)) {
			return redirectHttpResponse(httpResponse, modelView.getPath());
		}

		byte[] body;
		try {
			// path로부터 body를 읽어온다
			body = Files.readAllBytes(getValidPath(modelView.getPath()));
		} catch (IllegalArgumentException e) {
			// path에 파일이 존재하지 않는 경우, 404 NOT FOUND 에러 페이지 출력
			modelView = ErrorHandler.buildErrorPage(httpRequest, httpResponse, modelView);
			body = Files.readAllBytes(getValidPath(modelView.getPath()));
			body = ViewResolver.resolve(body, modelView);
		}

		// body에 viewResolver를 적용시킨다
		if (controllerExist(httpRequest)) {
			body = ViewResolver.resolve(body, modelView);
		}

		// Response에 body를 추가한다
		httpResponse.addBody(body);

		// path로부터 MimeType을 구하고 Response에 적용한다
		String fileName = getValidPath(modelView.getPath()).getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		httpResponse.addMimeType(MimeType.typeOf(extension));

		// 컨트롤러의 반환에 대한 파일 추가
		return httpResponse;
	}

	private HttpResponse redirectHttpResponse(final HttpResponse httpResponse, final String path) {
		httpResponse.setRedirect(path.replace("redirect:", ""), StatusCode.FOUND);
		return httpResponse;
	}

	private boolean controllerExist(final HttpRequest httpRequest) {
		return DeclaredControllers.exists(httpRequest.getMethod(), httpRequest.getEndpoint());
	}

	private ModelView runController(final HttpRequest httpRequest, final HttpResponse httpResponse,
		ModelView modelView) throws InvocationTargetException, IllegalAccessException {
		if (DeclaredControllers.exists(httpRequest.getMethod(), httpRequest.getEndpoint())) {
			modelView = DeclaredControllers.runController(httpRequest.getMethod(), httpRequest.getEndpoint(),
				httpRequest, httpResponse, modelView);
		}
		return modelView;
	}

	private Path getValidPath(final String path) throws IllegalArgumentException {
		Path templatePath = new File(TEMPLATES_PATH + path).toPath();
		Path staticPath = new File(STATIC_PATH + path).toPath();

		if (Files.exists(templatePath)) {
			return templatePath;
		}
		if (Files.exists(staticPath)) {
			return staticPath;
		}

		throw new IllegalArgumentException("지원하지 않는 경로를 입력했습니다.");
	}
}
