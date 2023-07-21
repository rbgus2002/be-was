package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static Logger logger = LoggerFactory.getLogger(HttpResponse.class);
	private HttpVersion version;
	private StatusCode statusCode;
	private byte[] body;
	private Map<String, String> header = new HashMap<>();

	public HttpResponse(HttpRequest httpRequest) {
		version = httpRequest.getVersion();
	}

	public void addFile(Path path) throws IOException, IllegalArgumentException {
		body = Files.readAllBytes(path);
		String fileName = path.getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		header.put("Content-Type", MimeType.typeOf(extension).extension + ";charset=utf-8");
		header.put("Content-Length", String.valueOf(body.length));
	}

	public void response(OutputStream out) {
		DataOutputStream dos = new DataOutputStream(out);
		responseHeader(dos);
		responseBody(dos, body);
	}

	private void responseHeader(DataOutputStream dos) {
		try {
			dos.writeBytes(version + " " + statusCode + " \r\n");
			for (String key : header.keySet()) {
				dos.writeBytes(key + ": " + header.get(key) + "\r\n");
			}
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

	public void setRedirect(final String redirectPath, StatusCode statusCode) {
		this.statusCode = statusCode;
		header.put("Location", redirectPath);
	}
}
