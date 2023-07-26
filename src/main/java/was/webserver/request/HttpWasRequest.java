package was.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpWasRequest {

	private final Map<String, String> map = new HashMap<>();
	private final Map<String, String> requestParam = new HashMap<>();
	private final List<String> cookies = new ArrayList<>();

	public HttpWasRequest(InputStream inputStream) throws IOException {
		final BufferedReader bufferedReader = convertToBufferedReader(inputStream);
		final HttpParser httpParser = HttpParser.getInstance();
		httpParser.parseHttpRequestToMap(bufferedReader, map, requestParam, cookies);
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
		if (cookies.isEmpty())
			return "";

		final HttpParser httpParser = HttpParser.getInstance();
		for (String cookie : cookies) {
			final String sessionValue = httpParser.parseSessionId(cookie);
			if (!sessionValue.isBlank()) {
				final String[] split = sessionValue.split("=");
				return split[1];
			}
		}

		return "";
	}
}
