package webserver.http.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import webserver.http.utils.FileMapper;

public class HttpResponse {

	private final HttpStatus status;
	private final HttpHeaderFields headerFields;
	private final byte[] body;

	private HttpResponse(HttpResponseBuilder builder) {
		this.status = builder.status;
		this.headerFields = builder.headerFields;
		this.body = builder.body;
	}

	public static HttpResponseBuilder builder() {
		return new HttpResponseBuilder();
	}

	public String getStatusMessage() {
		return status.getMessage();
	}

	public Set<Map.Entry<String, String>> getHeaderFields() {
		return headerFields.getAllFields();
	}

	public byte[] getBody() {
		return body;
	}

	public static class HttpResponseBuilder {

		private final HttpHeaderFields headerFields;
		private HttpStatus status;
		private byte[] body;

		private HttpResponseBuilder() {
			status = HttpStatus.OK;
			headerFields = new HttpHeaderFields();
			body = null;
			// TODO : 요청의 general header는 응답에도 바로 저장하기
		}

		public HttpResponseBuilder status(HttpStatus status) {
			if (this.status == HttpStatus.OK) {
				this.status = status;
			}
			return this;
		}

		public HttpResponseBuilder headerField(String key, String value) {
			if (headerFields.getValue(value) == null) {
				this.headerFields.add(key, value);
			}
			return this;
		}

		public HttpResponseBuilder body(Path resourcePath) {
			if (body != null) {
				return this;
			}
			try {
				this.body = Files.readAllBytes(resourcePath);
				String contentType = Files.probeContentType(resourcePath);
				if (contentType.startsWith("text")) {
					contentType = contentType.concat(";charset=UTF-8");
				}
				this.headerFields.add("Content-Type", contentType);
				this.headerFields.add("Content-Length", String.valueOf(body.length));
				return this;
			} catch (IOException e) {
				this.status = HttpStatus.SERVER_ERROR;
				return this;
			}
		}

		public HttpResponseBuilder view(String viewName) {
			try {
				File view = FileMapper.findFile(viewName + ".html");
				return body(view.toPath());
			} catch (FileNotFoundException e) {
				this.status = HttpStatus.SERVER_ERROR;
				return this;
			}
		}

		public HttpResponse build() {
			return new HttpResponse(this);
		}

	}

}
