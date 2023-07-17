package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWasResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpWasResponse.class);
	private static final String TEMPLATES_PATH = "src/main/resources/templates";
	private static final String STATIC_PATH = "src/main/resources/static";
	private static final String CONTENT_TYPE_CSS = "text/css";
	private static final String CONTENT_TYPE_HTML = "text/html";
	private static final String CONTENT_TYPE_JS = "application/javascript";
	private static final String SPLIT_DOT = "\\.";
	private final OutputStream outputStream;

	public HttpWasResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void responseResource(String resourcePath) throws IOException {
		final byte[] files = getFiles(resourcePath);

		final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		response200Header(dataOutputStream, files.length, resourcePath);
		responseBody(dataOutputStream, files);
	}

	private byte[] getFiles(String resourcePath) throws IOException {
		final Path path = new File(getResourcePath(resourcePath)+ resourcePath).toPath();
		return Files.readAllBytes(path);
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent, final String resourcePath) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: "+ getContentType(resourcePath) + ";charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void response404Header() {
		final byte[] response = "404 Not Found".getBytes();
		try {
			DataOutputStream dos = new DataOutputStream(outputStream);
			dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + response.length + "\r\n");
			dos.writeBytes("\r\n");
			dos.write(response, 0, response.length);
			dos.flush();
		} catch (IOException e){
			logger.error(e.getMessage());
		}
	}

	private String getContentType(String resourcePath) {
		final String[] split = resourcePath.split(SPLIT_DOT);
		final String type = split[split.length - 1].trim();

		if (type.equals("js"))
			return CONTENT_TYPE_JS;
		else if (type.equals("css"))
			return CONTENT_TYPE_CSS;
		return CONTENT_TYPE_HTML;
	}

	private String getResourcePath(String resourcePath) {
		final String[] split = resourcePath.split(SPLIT_DOT);
		final String type = split[split.length - 1].trim();
		if (type.equals("html")) {
			return TEMPLATES_PATH;
		}
		return STATIC_PATH;
	}
}
