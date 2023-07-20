package webserver.http.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

	private String httpVersion;
	private HttpStatus status;
	private HttpHeaderFields headerFields;
	private byte[] body;

	private HttpResponse() {
		headerFields = new HttpHeaderFields();
	}

	public static HttpResponse from(HttpRequest request) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.httpVersion = request.getHttpVersion();
		// TODO: 요청의 general header는 응답에도 바로 저장하기
		return httpResponse;
	}

	public Set<Map.Entry<String, String>> getHeaderFieldsEntry() {
		return headerFields.getEntrySet();
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

	public void writeHeaderField(String key, String value) {
		headerFields.addHeaderField(key, value);
	}

	public void writeBody(byte[] body, String contentType) {
		this.body = body;
		writeHeaderField("Content-Type", contentType);
		writeHeaderField("Content-Length", String.valueOf(body.length));
	}

}
