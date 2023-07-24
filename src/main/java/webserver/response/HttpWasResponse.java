package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.session.Cookie;
import webserver.utils.HttpHeader;
import webserver.utils.HttpMimeType;
import webserver.utils.HttpStatus;

public class HttpWasResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpWasResponse.class);
	private static final String REQUEST_LINE = "HTTP/1.1 %s %s\r\n";
	private final DataOutputStream dos;
	private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	private final HttpResponseHeader header = new HttpResponseHeader();
	private byte[] body = new byte[0];
	private final List<Cookie> cookies = new ArrayList<>();
	private final HttpFileHandler httpFileHandler;

	public HttpWasResponse(OutputStream outputStream) {
		this.dos = new DataOutputStream(outputStream);
		httpFileHandler = new HttpFileHandler();
	}

	public void responseResource(String resourcePath) {
		try {
			Path path = httpFileHandler.getFilePath(resourcePath);
			final byte[] files = Files.readAllBytes(path);
			header.clearHeader();
			httpStatus = HttpStatus.OK;
			header.addHeader(HttpHeader.CONTENT_TYPE, HttpMimeType.valueOfResourcePath(resourcePath).getCharsetUtf8());
			this.body = files;
		} catch (Exception e) {
			logger.error(e.getMessage());
			response404();
		}
	}

	public void doResponse() {
		try {
			dos.writeBytes(getRequestLine(httpStatus));
			dos.writeBytes(header.getAllHeader());
			printCookie();
			if (body != null && body.length != 0) {
				dos.writeBytes("\r\n");
				dos.write(body, 0, body.length);
			}
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void printCookie() throws IOException {
		for (Cookie cookie : cookies) {
			dos.writeBytes(cookie.convertToHeader());
		}
	}

	public void response302Header(String location) {
		header.clearHeader();
		httpStatus = HttpStatus.FOUND;
		header.addHeader(HttpHeader.LOCATION, location);
	}

	public void response404() {
		String response = "404 Not Found";
		header.clearHeader();
		httpStatus = HttpStatus.NOT_FOUND;
		header.addHeader(HttpHeader.CONTENT_TYPE, HttpMimeType.PLAIN.getCharsetUtf8());
		header.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(response.getBytes().length));
		body = response.getBytes();
	}

	public void response405() {
		final String response = "405 Method Not Allowed";
		httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
		header.clearHeader();
		header.addHeader(HttpHeader.CONTENT_TYPE, HttpMimeType.PLAIN.getCharsetUtf8());
		header.addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(response.getBytes().length));
		body = response.getBytes();
	}

	private String getRequestLine(HttpStatus httpStatus) {
		return String.format(REQUEST_LINE, httpStatus.getStatusCode(), httpStatus.getName());
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setBody(String body, HttpMimeType type) {
		if (body != null) {
			this.body = body.getBytes();
			addHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length));
			addHeader(HttpHeader.CONTENT_TYPE, type.getCharsetUtf8());
		}
	}

	public void addHeader(HttpHeader headerType, String value) {
		header.addHeader(headerType, value);
	}

	public void addCookie(Cookie cookie) {
		cookies.add(cookie);
	}

	public boolean isExistResource(String resourcePath) {
		return httpFileHandler.isExistResource(resourcePath);
	}
}
