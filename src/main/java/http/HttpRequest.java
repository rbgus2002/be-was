package http;

import static http.header.HeaderConst.*;
import static http.statusline.ResponseLine.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.header.Header;
import http.statusline.HttpMethod;
import http.statusline.HttpVersion;

public class HttpRequest {
	private static final int KEY = 0;
	private static final int VALUE = 1;
	private Logger logger = LoggerFactory.getLogger(HttpRequest.class);
	private BufferedReader reader;
	private HttpMethod method;
	private String path;
	private String endPoint;
	private HttpVersion version;
	private String body;
	private Header header = new Header();
	private HttpParameter httpParameter = new HttpParameter();

	public HttpRequest(BufferedReader reader) throws IOException {
		this.reader = reader;
		parseRequest();
	}

	public HttpRequest(HttpParameter httpParameter) {
		this.httpParameter = httpParameter;
	}

	public boolean isGet() {
		return method.equals(HttpMethod.GET);
	}

	public boolean isPost() {
		return method.equals(HttpMethod.POST);
	}

	private void parseRequest() throws IOException {
		parseStatusLine();
		parseHeader();
		parseBody();
	}

	private void parseHeader() throws IOException {
		String line = HEADER_DELIMITER;
		String[] tokens;
		while (!line.isBlank()) {
			line = reader.readLine();
			if (line.contains(HEADER_DELIMITER)) {
				tokens = line.split(HEADER_DELIMITER);
				header.addHeader(tokens[KEY], tokens[VALUE]);
			}
		}
	}

	private void parseBody() throws IOException {
		if ((method == HttpMethod.POST) && header.containsLength()) {
			char[] charArray = new char[header.getContentLength()];
			reader.read(charArray);
			body = String.valueOf(charArray);
			parseParameter(body);
		}
	}

	private void parseStatusLine() throws IOException {
		String statusLine = reader.readLine();
		String[] arguments = statusLine.split(STATUS_LINE_DELIMETER);
		method = HttpMethod.typeOf(arguments[0]);
		path = arguments[1];
		endPoint = path;
		if ((method == HttpMethod.GET) && path.contains("?")) {
			String[] pathSplit = path.split("\\?");
			String parameterLine = pathSplit[1].substring(0, pathSplit[1].indexOf("."));
			path = pathSplit[0];
			endPoint = path.substring(path.lastIndexOf("/"));
			parseParameter(parameterLine);
		}
		version = HttpVersion.versionOf(arguments[2]);
	}

	public String getCookieValue(final String cookieName) throws NoSuchElementException {
		return header.getCookieValue(cookieName);
	}

	private void parseParameter(String path) {
		for (String line : path.split("\\&")) {
			if (line.contains("=") && !line.endsWith("=")) {
				logger.debug(line);
				String[] values = line.split("=");
				httpParameter.put(values[KEY], values[VALUE]);
			}
		}
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public HttpVersion getVersion() {
		return version;
	}

	public String getBody() {
		return body;
	}

	public String getEndpoint() {
		return endPoint;
	}

	public HttpParameter getParameter() {
		return httpParameter;
	}
}
