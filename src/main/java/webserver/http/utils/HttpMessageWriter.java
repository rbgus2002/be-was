package webserver.http.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.message.HttpResponse;

public class HttpMessageWriter {

	private static final Logger logger = LoggerFactory.getLogger(HttpMessageWriter.class);

	private static final String CRLF = "\r\n";

	private HttpMessageWriter() {
	}

	public static void sendResponse(DataOutputStream outputStream, HttpResponse response) {
		writeResponseHeader(outputStream, response);
		writeResponseBody(outputStream, response);
	}

	private static void writeResponseHeader(DataOutputStream outputStream, HttpResponse response) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("HTTP/1.1 ").append(response.getStatusMessage()).append(CRLF);
			for (Map.Entry<String, String> headerField : response.getHeaderFields()) {
				sb.append(headerField.getKey()).append(": ").append(headerField.getValue()).append(CRLF);
			}
			sb.append(CRLF);
			outputStream.writeBytes(sb.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private static void writeResponseBody(DataOutputStream outputStream, HttpResponse response) {
		try {
			byte[] body = response.getBody();
			if (body != null) {
				outputStream.write(body);
			}
			outputStream.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
