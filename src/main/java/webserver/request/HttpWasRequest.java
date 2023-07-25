package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpWasRequest {

	private final Map<String, String> map = new HashMap<>();
	private final Map<String, String> requestParam = new HashMap<>();

	public HttpWasRequest(InputStream inputStream) throws IOException {
		final BufferedReader bufferedReader = convertToBufferedReader(inputStream);
		final HttpParser httpParser = HttpParser.getInstance();
		httpParser.parseHttpRequestToMap(bufferedReader, map, requestParam);
	}

	private BufferedReader convertToBufferedReader(final InputStream inputStream) {
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		return new BufferedReader(inputStreamReader);
	}

	public String getResourcePath() {
		return map.get(HttpParser.RESOURCE_PATH);
	}

	public String getHttpMethod() {
		return map.get(HttpParser.HTTP_METHOD);
	}

	public String getParameter(String key) {
		return requestParam.get(key);
	}

	public String getAttribute(String key) {
		return map.get(key);
	}

	public String getSessionId() {
		final String value = map.get("Cookie");
		if (value == null || value.isBlank())
			return "";

		final String[] token = value.split(";");
		final String sessionValue = Arrays.stream(token)
			.filter(attribute -> {
				final String[] split = attribute.split("=");
				return Objects.equals(split[0].trim(), "SID");
			}).findFirst()
			.orElse("");

		if (sessionValue.isBlank())
			return sessionValue;

		final String[] split = sessionValue.split("=");
		return split[1];
	}
}
