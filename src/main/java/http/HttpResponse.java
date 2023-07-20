package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static Logger logger = LoggerFactory.getLogger(HttpResponse.class);
	private MimeType mimeType;
	private HttpVersion version;
	private byte[] body;
	private StatusCode statusCode;

	public HttpResponse(HttpRequest httpRequest) {
		version = httpRequest.getVersion();
	}

	public void addFile(Path path) throws IOException, IllegalArgumentException {
		body = Files.readAllBytes(path);
		String fileName = path.getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		mimeType = MimeType.typeOf(extension);
	}

	public void setStatus(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public void response(OutputStream out) {
		DataOutputStream dos = new DataOutputStream(out);
		responseHeader(dos, body.length);
		responseBody(dos, body);
	}

	private void responseHeader(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes(version + " " + statusCode + " \r\n");
			dos.writeBytes("Content-Type: " + mimeType.extension + ";charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
