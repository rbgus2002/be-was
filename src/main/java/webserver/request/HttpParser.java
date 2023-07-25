package webserver.request;

import static webserver.utils.HttpContentType.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import webserver.utils.HttpHeader;

public class HttpParser {

	private static final HttpParser instance = new HttpParser();
	public static final String RESOURCE_PATH = "ResourcePath";
	public static final String PROTOCOL_VERSION = "ProtocolVersion";
	public static final String HTTP_METHOD = "HttpMethod";

	private Map<String, String> map;
	private Map<String, String> requestParam;

	public static HttpParser getInstance() {
		return instance;
	}
	public void parseHttpRequestToMap(BufferedReader bufferedReader, Map<String, String> map, Map<String, String> requestParam) throws IOException {
		this.map = map;
		this.requestParam = requestParam;

		String input = bufferedReader.readLine();
		firstRequestHeader(input);
		saveAnotherHeader(bufferedReader);
		bodyParser(bufferedReader);
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

	private void firstRequestHeader(String input) {
		final String[] split = input.split(" ");
		map.put(HTTP_METHOD, split[0]);
		saveResourcePath(split[1]);
		map.put(PROTOCOL_VERSION, split[2]);
	}

	private void saveResourcePath(String path) {
		final String[] token = path.split("\\?");

		map.put(RESOURCE_PATH, token[0]);
		if (token.length == 1)
			return;

		saveRequestParam(token[1]);
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

	private String base64Decoder(String value) {
		return URLDecoder.decode(value, StandardCharsets.UTF_8);
	}

}
