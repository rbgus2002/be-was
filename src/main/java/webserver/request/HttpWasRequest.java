package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpWasRequest {

	private static final Logger logger = LoggerFactory.getLogger(HttpWasRequest.class);
	private static final String BASE64_PATTERN = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
	private static final String RESOURCE_PATH = "ResourcePath";
	private static final String PROTOCOL_VERSION = "ProtocolVersion";
	private static final String HTTP_METHOD = "HttpMethod";

	private final Map<String, String> map = new ConcurrentHashMap<>();
	private final Map<String, String> requestParam = new ConcurrentHashMap<>();

	public HttpWasRequest(InputStream inputStream) throws IOException {
		parseHttpRequestToMap(inputStream);
	}

	private void parseHttpRequestToMap(InputStream inputStream) throws IOException {
		final BufferedReader bufferedReader = convertToBufferedReader(inputStream);
		String input = bufferedReader.readLine();
		firstRequestHeader(input);
		saveAnotherHeader(bufferedReader);
		bodyParser(bufferedReader);
	}

	private void bodyParser(BufferedReader bufferedReader) throws IOException {
		if (!bufferedReader.ready()) {
			return;
		}
		StringBuilder sb = new StringBuilder();

		int data;
		while(bufferedReader.ready() && (data = bufferedReader.read()) != -1) {
			sb.append((char) data);
		}
		map.put("body", sb.toString());
	}

	private void saveAnotherHeader(final BufferedReader bufferedReader) throws IOException {
		String input = bufferedReader.readLine();
		while (input != null && !input.isBlank()) {
			final String[] keyValue = input.split(":", 2);
			final String key = keyValue[0];
			final String value = keyValue[1].trim();
			map.put(key, value);
			input = bufferedReader.readLine();
		}
	}

	private BufferedReader convertToBufferedReader(final InputStream inputStream) {
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		return new BufferedReader(inputStreamReader);
	}

	private void firstRequestHeader(String input) {
		final String[] split = input.split(" ");
		map.put(HTTP_METHOD, split[0]);
		saveResourcePath(split[1]);
		map.put(PROTOCOL_VERSION, split[2]);
	}

	public String getResourcePath() {
		return map.get(RESOURCE_PATH);
	}

	public String getHttpMethod() {
		return map.get(HTTP_METHOD);
	}

	private void saveResourcePath(String path) {
		final String[] token = path.split("\\?");

		map.put(RESOURCE_PATH, token[0]);
		if (token.length == 1)
			return;

		final String[] params = token[1].split("&");
		saveRequestParam(params);
	}

	private void saveRequestParam(String[] params) {
		for (String param : params) {
			final String[] keyValue = param.split("=");
			final String key = keyValue[0];
			final String value = base64Decoder(keyValue[1]);
			requestParam.put(key, value);
		}
	}

	public String getParameter(String key) {
		return requestParam.get(key);
	}

	public String getAttribute(String key) {
		return map.get(key);
	}

	private String base64Decoder(String value) {
		if (!Pattern.matches(BASE64_PATTERN, value)) {
			return URLDecoder.decode(value, StandardCharsets.UTF_8);
		}
		return value;
	}
}
