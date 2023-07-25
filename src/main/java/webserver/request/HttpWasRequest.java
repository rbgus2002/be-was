package webserver.request;

import static webserver.utils.HttpContentType.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import webserver.utils.HttpHeader;

public class HttpWasRequest {

	private static final String RESOURCE_PATH = "ResourcePath";
	private static final String PROTOCOL_VERSION = "ProtocolVersion";
	private static final String HTTP_METHOD = "HttpMethod";

	private final Map<String, String> map = new HashMap<>();
	private final Map<String, String> requestParam = new HashMap<>();

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
		final String bodyData = getBodyData(bufferedReader);
		final String contentType = map.get(HttpHeader.CONTENT_TYPE.getType());
		if (StringUtils.contains(contentType, X_WWW_FORM_URLENCODED)) {
			saveRequestParam(bodyData);
		}
		map.put("body", bodyData);
	}

	private String getBodyData(BufferedReader bufferedReader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int data;
		while (bufferedReader.ready() && (data = bufferedReader.read()) != -1) {
			sb.append((char)data);
		}
		return sb.toString();
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

		saveRequestParam(token[1]);
	}

	private void saveRequestParam(String data) {
		final String[] params = data.split("&");
		for (String param : params) {
			final String[] keyValue = param.split("=");
			if (keyValue.length == 2) {
				final String key = keyValue[0];
				final String value = base64Decoder(keyValue[1]);
				requestParam.put(key, value);
			}
		}
	}

	public String getParameter(String key) {
		return requestParam.get(key);
	}

	public String getAttribute(String key) {
		return map.get(key);
	}

	private String base64Decoder(String value) {
		return URLDecoder.decode(value, StandardCharsets.UTF_8);
	}

}
