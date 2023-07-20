package webserver.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;
import webserver.exception.InvalidRequestException;
import webserver.http.util.FileUtil;

public class HttpResponse {

	private static final Integer STATUS_OK = 200;
	private static final Integer STATUS_REDIRECT = 303;
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private final int status;
	private byte[] body;
	private String contentType;
	private String redirectUrl;
	private Map<String, String> model;

	public HttpResponse(final int status, final byte[] body, final String contentType,
		final Map<String, String> model) {
		this.status = status;
		this.body = body;
		this.contentType = contentType;
		this.model = model;
	}

	public HttpResponse(final int status, final String redirectUrl, final Map<String, String> model) {
		this.status = status;
		this.redirectUrl = redirectUrl;
		this.model = model;
	}

	public static HttpResponse createRedirectResponse(final String resourceUrl, final Map<String, String> model) {
		final String redirectUrl = resourceUrl.split(":")[1];
		return new HttpResponse(STATUS_REDIRECT, redirectUrl, model);
	}

	public static HttpResponse createDefaultResponse(
		final String body,
		final String contentType,
		final Map<String, String> model) {

		return new HttpResponse(STATUS_OK, body.getBytes(), contentType, model);
	}

	public static HttpResponse createResourceResponse(
		final String path,
		final String contentType,
		final Map<String, String> model) throws IOException {

		byte[] body = getResourceBytes(path);

		return new HttpResponse(STATUS_OK, body, contentType, model);
	}

	private static byte[] getResourceBytes(final String url) throws IOException {
		if (FileUtil.isFileRequest(url)) {
			String filePath = FileUtil.getFilePath(url);
			return Files.readAllBytes(new File(filePath).toPath());
		}

		throw InvalidRequestException.Exception;
	}

	public void doResponse(final DataOutputStream dos) {
		if (status == STATUS_OK) {
			response200Header(dos);
		}

		if (status == STATUS_REDIRECT) {
			response303Header(dos, redirectUrl);
		}

		responseBody(dos, body);
	}

	private void response200Header(DataOutputStream dos) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + body.length + "\r\n");

			setCookie(dos);

			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void response303Header(DataOutputStream dos, String redirectUrl) {
		try {
			dos.writeBytes("HTTP/1.1 303 See Other\r\n");
			dos.writeBytes("Location: " + redirectUrl + "\r\n");
			dos.writeBytes("Cache-Control: no-cache, no-store, must-revalidate\r\n");
			dos.writeBytes("Pragma: no-cache\r\n");
			dos.writeBytes("Expires: 0\r\n");

			setCookie(dos);

			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void setCookie(DataOutputStream dos) throws IOException {
		String cookie = model.get("Cookie");
		if (Objects.nonNull(cookie)) {
			dos.writeBytes("Set-Cookie: sid=" + cookie + "; Path=/");
		}
	}

	private void responseBody(final DataOutputStream dos, final byte[] body) {
		try {
			if(Objects.nonNull(body)) {
				dos.write(body, 0, body.length);
			}
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
