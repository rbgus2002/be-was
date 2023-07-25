package webserver.http.message;

import static webserver.http.utils.HttpConstant.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.utils.StringUtils;

public class HttpResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private DataOutputStream outputStream;
	private String httpVersion;
	private HttpStatus status;
	private HttpHeaderFields headerFields;
	private byte[] body;

	private HttpResponse() {
		headerFields = new HttpHeaderFields();
	}

	public static HttpResponse from(HttpRequest request, DataOutputStream outputStream) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.httpVersion = request.getHttpVersion();
		httpResponse.outputStream = outputStream;
		// TODO : 요청의 general header는 응답에도 바로 저장하기
		return httpResponse;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setHeader(String key, String value) {
		headerFields.addHeaderField(key, value);
	}

	public void setBody(byte[] body, String contentType) {
		this.body = body;
		setHeader("Content-Type", contentType);
		setHeader("Content-Length", String.valueOf(body.length));
	}

	public void sendResponse(DataOutputStream outputStream) {
		writeResponseHeader(outputStream);
		writeResponseBody(outputStream);
	}

	private void writeResponseHeader(DataOutputStream outputStream) {
		try {
			List<String> tokens = new ArrayList<>();
			tokens.add(httpVersion);
			tokens.add(status.getCode());
			tokens.add(status.getMessage());
			outputStream.writeBytes(StringUtils.joinStatusLine(tokens));
			for (Map.Entry<String, String> headerField : headerFields.getEntrySet()) {
				outputStream.writeBytes(StringUtils.joinHeaderFields(headerField.getKey(), headerField.getValue()));
			}
			outputStream.writeBytes(CRLF);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void writeResponseBody(DataOutputStream outputStream) {
		try {
			outputStream.write(body, 0, body.length);
			outputStream.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
