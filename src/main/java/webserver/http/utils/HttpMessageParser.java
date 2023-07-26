package webserver.http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.exception.BadRequestException;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.ParameterMap;
import webserver.http.message.URL;

public class HttpMessageParser {

	public static final String SINGLE_SPACE = " ";
	public static final String SEPARATOR_REGEX = ": ?";

	private static final String PATH_QUERY_SEPARATOR = "\\?";
	private static final String PARAM_SEPARATOR = "&";
	private static final String KEY_VALUE_SEPARATOR = "=";
	private static final String EMPTY_VALUE = "";

	private static final Logger logger = LoggerFactory.getLogger(HttpMessageParser.class);

	public static HttpRequest parseHttpRequest(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String startLine = br.readLine();
		if (startLine == null) {
			return null;
		}

		String[] requestLineTokens = startLine.split(SINGLE_SPACE);
		if (requestLineTokens.length != 3) {
			throw new BadRequestException();
		}

		HttpMethod httpMethod = HttpMethod.from(requestLineTokens[0]);
		URL url = parseURL(requestLineTokens[1]);
		ParameterMap headerFields = parseHeaderFields(br);
		ParameterMap bodyParameters = parseBody(br, headerFields);
		return new HttpRequest(httpMethod, url, headerFields, bodyParameters);
	}

	private static URL parseURL(String urlString) {
		ParameterMap parameterMap = new ParameterMap();
		String[] tokens = urlString.split(PATH_QUERY_SEPARATOR);
		if (tokens.length >= 2) {
			parameterMap = parseParameter(tokens[1]);
		}
		return new URL(tokens[0], parameterMap);
	}

	private static ParameterMap parseParameter(String data) {
		ParameterMap parameterMap = new ParameterMap();
		String[] paramList = data.split(PARAM_SEPARATOR);
		for (String param : paramList) {
			String[] tokens = param.split(KEY_VALUE_SEPARATOR);
			String key = tokens[0];
			String value = EMPTY_VALUE;
			if (tokens.length >= 2) {
				value = tokens[1];
			}
			parameterMap.add(key, value);
		}
		return parameterMap;
	}

	private static ParameterMap parseHeaderFields(BufferedReader br) throws IOException {
		ParameterMap headerFields = new ParameterMap();
		String line = br.readLine();
		while (!EMPTY_VALUE.equals(line)) {
			String[] tokens = line.split(SEPARATOR_REGEX, 2);
			if (tokens.length != 2) {
				throw new BadRequestException();
			}
			headerFields.add(tokens[0], tokens[1]);
			line = br.readLine();
		}
		return headerFields;
	}

	private static ParameterMap parseBody(BufferedReader br, ParameterMap headerFields) throws
		IOException {
		String contentLength = headerFields.getValue("Content-Length");
		if (contentLength == null) {
			return new ParameterMap();
		}

		int length = Integer.parseInt(contentLength);
		char[] body = new char[length];
		br.read(body, 0, length);
		String bodyString = String.valueOf(body);

		return parseParameter(bodyString);
	}

	public static void log(HttpRequest request) {
		logger.debug("{} {} {}", request.getHttpMethod().getName(), request.getUrlPath(), request.getHttpVersion());
		for (Map.Entry<String, String> field : request.getHeaderFields().getEntrySet()) {
			logger.debug("{}: {}", field.getKey(), field.getValue());
		}
		logger.debug(EMPTY_VALUE);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> bodyParam : request.getBodyParameters().getEntrySet()) {
			sb.append("&").append(bodyParam.getKey()).append("=").append(bodyParam.getValue());
		}
		if (sb.length() > 0) {
			String str = sb.substring(1);
			logger.debug(str);
		}
	}

}
