package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.net.SocketConnector;

public class HttpWasResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpWasResponse.class);
	private static final String TEMPLATES_PATH = "src/main/resources/templates";

	private final OutputStream outputStream;

	public HttpWasResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void responseResource(String resourcePath) throws IOException {
		final byte[] files = getFiles(resourcePath);

		final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		response200Header(dataOutputStream, files.length);
		responseBody(dataOutputStream, files);
	}

	private byte[] getFiles(String resourcePath) throws IOException {
		final Path path = new File(TEMPLATES_PATH + resourcePath).toPath();
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

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
}
