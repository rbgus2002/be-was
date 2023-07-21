package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class HttpResponse {
	private StatusLine statusLine = new StatusLine();
	private byte[] body = new byte[0];
	private Map<String, String> header = new HashMap<>();

	public HttpResponse(HttpRequest httpRequest) {
		statusLine.setVersion(httpRequest.getVersion());
	}

	public void response(OutputStream out) throws IOException {
		DataOutputStream dos = new DataOutputStream(out);
		responseStatusLine(dos);
		responseHeader(dos);
		responseBody(dos, body);
	}

	public void addFile(Path path) throws IOException, IllegalArgumentException {
		body = Files.readAllBytes(path);
		String fileName = path.getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		header.put("Content-Type", MimeType.typeOf(extension).extension + ";charset=utf-8");
		header.put("Content-Length", String.valueOf(body.length));
	}

	public void setRedirect(final String redirectPath, StatusCode statusCode) {
		statusLine.setStatusCode(statusCode);
		header.put("Location", redirectPath);
	}

	private void responseStatusLine(DataOutputStream dos) throws IOException {
		dos.writeBytes(statusLine.getStatusLineForHeader());
	}

	private void responseHeader(DataOutputStream dos) throws IOException {
		for (String key : header.keySet()) {
			dos.writeBytes(key + ": " + header.get(key) + "\r\n");
		}
		dos.writeBytes("\r\n");
	}

	private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
		dos.write(body, 0, body.length);
		dos.flush();
	}
}
