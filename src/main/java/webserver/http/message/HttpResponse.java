package webserver.http.message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpResponse {

	private HttpStatus status;
	private HttpHeaderFields headerFields;
	private byte[] body;

	private HttpResponse(Builder builder) {
		this.status = builder.status;
		this.headerFields = builder.headerFields;
		this.body = builder.body;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private HttpStatus status;
		private HttpHeaderFields headerFields;
		private byte[] body;

		private Builder() {
			this.status = HttpStatus.OK;
			headerFields = new HttpHeaderFields();
			// TODO : 요청의 general header는 응답에도 바로 저장하기
		}

		public Builder status(HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder headerFields(HttpHeaderFields headerFields) {
			this.headerFields = headerFields;
			return this;
		}

		public Builder headerField(String key, String value) {
			this.headerFields.add(key, value);
			return this;
		}

		public Builder body(Path resourcePath) throws IOException {
			byte[] body = Files.readAllBytes(resourcePath);
			this.body = body;
			this.headerFields.add("Content-Type", Files.probeContentType(resourcePath));
			this.headerFields.add("Content-Length", String.valueOf(body.length));
			return this;
		}

		public HttpResponse build() {
			return new HttpResponse(this);
		}

	}

}
