package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWasRequest {

	private static final Logger logger = LoggerFactory.getLogger(HttpWasRequest.class);
	private Map<String, String> map = new ConcurrentHashMap<>();

	public HttpWasRequest(InputStream inputStream) throws IOException {
		parseHttpRequestToMap(inputStream);
	}

	private void parseHttpRequestToMap(InputStream inputStream) throws IOException {
		final BufferedReader bufferedReader = convertToBufferedReader(inputStream);

		boolean isFirstRead = false;
		String input = bufferedReader.readLine();

		while (input != null && !input.isBlank()) {
			if (!isFirstRead) {
				firstRequestHeader(input);
				isFirstRead = true;
			}
			logger.debug("request : {}", input);
			input = bufferedReader.readLine();
		}
	}

	private BufferedReader convertToBufferedReader(final InputStream inputStream) {
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		return new BufferedReader(inputStreamReader);
	}

	private void firstRequestHeader(String input) {
		final String[] split = input.split(" ");
		map.put("HttpMethod", split[0]);
		map.put("ResourcePath", split[1]);
		map.put("ProtocolVersion", split[2]);
	}

	public String getResourcePath() {
		return map.get("ResourcePath");
	}
}