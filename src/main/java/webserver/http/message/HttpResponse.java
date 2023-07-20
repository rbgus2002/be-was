package webserver.http.message;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

	private String httpVersion;
	private HttpStatus status;
	private byte[] body;

	private HttpResponse() {
	}

	public static HttpResponse from(HttpRequest request) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.httpVersion = request.getHttpVersion();
		return httpResponse;
	}

	public List<String> getStatusLineTokens() {
		List<String> tokens = new ArrayList<>();
		tokens.add(httpVersion);
		tokens.add(status.getCode());
		tokens.add(status.getMessage());
		return tokens;
	}

	public byte[] getBody() {
		return body;
	}

	public void writeStatus(HttpStatus status) {
		this.status = status;
	}

	public void writeBody(byte[] body) {
		this.body = body;
	}

}
