package webserver.response;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.utils.HttpHeader;
import webserver.utils.HttpMimeType;
import webserver.utils.HttpStatus;

public class HttpWasResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpWasResponse.class);
	private static final String TEMPLATES_PATH = "src/main/resources/templates";
	private static final String STATIC_PATH = "src/main/resources/static";
	private static final String SPLIT_DOT = "\\.";
	private static final String REQUEST_LINE = "HTTP/1.1 %s %s\r\n";
	private final DataOutputStream dos;
	private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	private final HttpResponseHeader header = new HttpResponseHeader();
	private byte[] body = new byte[0];


	public HttpWasResponse(OutputStream outputStream) {
		this.dos = new DataOutputStream(outputStream);
	}

	public void responseResource(String resourcePath) {
		try {
			final byte[] files = getFiles(resourcePath);
			header.clearHeader();
			httpStatus = HttpStatus.OK;
			header.addHeader(HttpHeader.CONTENT_TYPE, HttpMimeType.valueOfResourcePath(resourcePath).getCharsetUtf8());
			this.body = files;
		} catch (Exception e) {
			logger.error(e.getMessage());
			response404();
		}
	}

	private byte[] getFiles(String resourcePath) throws IOException {
		final Path path = new File(getResourcePath(resourcePath)+ resourcePath).toPath();
		return Files.readAllBytes(path);
	}


	public void doResponse() {
		try {
			dos.writeBytes(getRequestLine(httpStatus));
			dos.writeBytes(header.getAllHeader());
			if (body != null && body.length != 0) {
				dos.writeBytes("\r\n");
				dos.write(body, 0, body.length);
			}
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
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

	private String getResourcePath(String resourcePath) {
		final String[] split = resourcePath.split(SPLIT_DOT);
		final String type = split[split.length - 1].trim();
		if (type.equals("html")) {
			return TEMPLATES_PATH;
		}
		return STATIC_PATH;
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

	public void setBody(String body) {
		this.body = body.getBytes();
	}
}
