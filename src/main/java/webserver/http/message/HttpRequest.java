package webserver.http.message;

import static webserver.http.utils.HttpConstant.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
	private final HttpHeaderFields headerFields;
	private HttpMethod httpMethod;
	private URL url;
	private String httpVersion;

	private HttpRequest() {
		headerFields = new HttpHeaderFields();
	}

	public static HttpRequest from(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.parseAndSetRequestLine(br);
		httpRequest.parseAndSetHeaderFields(br);
		return httpRequest;
	}

	private void parseAndSetRequestLine(BufferedReader bufferedReader) throws IOException {
		String startLine = bufferedReader.readLine();
		// 로그
		logger.debug(startLine);
		if (startLine == null) {
			return;
		}

		String[] requestLineTokens = startLine.split(SINGLE_SPACE);
		httpMethod = HttpMethod.from(requestLineTokens[0]);
		url = URL.from(requestLineTokens[1]);
		httpVersion = requestLineTokens[2];
	}

	private void parseAndSetHeaderFields(BufferedReader bufferedReader) throws IOException {
		String line = bufferedReader.readLine();
		while (!"".equals(line)) {
			String[] headerFieldTokens = line.split(SEPARATOR_REGEX, 2);
			headerFields.addHeaderField(headerFieldTokens[0], headerFieldTokens[1]);
			line = bufferedReader.readLine();
		}
	}

	public String getUrlPath() {
		return url.getPath();
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public String getParam(String key) {
		return url.getQueryValue(key);
	}
}
