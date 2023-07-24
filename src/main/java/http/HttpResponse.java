package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import http.header.Header;
import http.header.MimeType;
import http.statusline.StatusCode;
import http.statusline.ResponseLine;
import session.Cookie;
import session.SessionConst;

public class HttpResponse {
	private ResponseLine responseLine = new ResponseLine();
	private Header header = new Header();
	private byte[] body = new byte[0];

	public HttpResponse(HttpRequest httpRequest) {
		responseLine.setVersion(httpRequest.getVersion());
		responseLine.setStatusCode(StatusCode.OK);
	}

	public HttpResponse() {

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
		responseLine.setStatusCode(statusCode);
		header.addHeader("Location", redirectPath);
	}

	public void addCookie(String cookieName, String sessionId) {
		Cookie cookie = Cookie.newCookie();
		cookie.add(cookieName, sessionId);
		header.addHeader("Set-Cookie", cookie.toHeaderValue());
	}

	private void responseStatusLine(DataOutputStream dos) throws IOException {
		dos.writeBytes(responseLine.getStatusLineForHeader() + "\r\n");
	}

	private void responseHeader(DataOutputStream dos) throws IOException {
		dos.writeBytes(header.getHeaderLines());
	}

	private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
		dos.write(body, 0, body.length);
		dos.flush();
	}
}
