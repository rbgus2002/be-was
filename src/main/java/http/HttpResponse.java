package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;


public class HttpResponse {
	private StatusLine statusLine = new StatusLine();
	private Header header = new Header();
	private byte[] body = new byte[0];

	public HttpResponse(HttpRequest httpRequest) {
		statusLine.setVersion(httpRequest.getVersion());
		statusLine.setStatusCode(StatusCode.OK);
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
		header.addHeader("Content-Type", MimeType.typeOf(extension).extension + ";charset=utf-8");
		header.addHeader("Content-Length", String.valueOf(body.length));
	}

	public void setRedirect(final String redirectPath, StatusCode statusCode) {
		statusLine.setStatusCode(statusCode);
		header.addHeader("Location", redirectPath);
	}

	private void responseStatusLine(DataOutputStream dos) throws IOException {
		dos.writeBytes(statusLine.getStatusLineForHeader() + "\r\n");
	}

	private void responseHeader(DataOutputStream dos) throws IOException {
		dos.writeBytes(header.getHeaderLines());
	}

	private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
		dos.write(body, 0, body.length);
		dos.flush();
	}
}
