package webserver.http.message;

import static webserver.http.utils.Constant.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

	private final String httpMethod;
	private final String uri;
	private final String httpVersion;
	private final Map<String, String> headers;

	private HttpRequest(String httpMethod, String uri, String httpVersion, Map<String, String> headers) {
		this.httpMethod = httpMethod;
		this.uri = uri;
		this.httpVersion = httpVersion;
		this.headers = headers;
	}

	public static HttpRequest from(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String[] tokens = parseStartLine(br);
		return new HttpRequest(tokens[0], tokens[1], tokens[2], parseHeaderFields(br));
	}

	private static String[] parseStartLine(BufferedReader bufferedReader) throws IOException {
		String startLine = bufferedReader.readLine();
		// 로그
		logger.debug(startLine);

		return startLine.split(SINGLE_SPACE);
	}

	private static Map<String, String> parseHeaderFields(BufferedReader bufferedReader) throws IOException {
		Map<String, String> headers = new HashMap<>();
		String line = bufferedReader.readLine();
		while (line != null && !line.equals("")) {
			// 로그
			logger.debug(line);

			String[] values = line.split(SEPARATOR_REGEX, 2);
			headers.put(values[0], values[1]);
			line = bufferedReader.readLine();
		}
		return headers;
	}

	public String getUri() {
		return uri;
	}

}
