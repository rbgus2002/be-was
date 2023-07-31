package webserver.http;

import static webserver.http.statusline.ResponseLine.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.NoSuchElementException;

import webserver.http.header.Header;
import webserver.http.statusline.HttpMethod;
import webserver.http.statusline.HttpVersion;
import webserver.http.header.HeaderConst;

public class HttpRequest {
	private static final int KEY = 0;
	private static final int VALUE = 1;
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

	private void parseRequest() throws IOException {
		parseStatusLine();
		parseHeader();
		parseBody();
	}

	private void parseHeader() throws IOException {
		String line = HeaderConst.HEADER_DELIMITER;
		String[] tokens;
		while (!line.isBlank()) {
			line = reader.readLine();
			if (line.contains(HeaderConst.HEADER_DELIMITER)) {
				tokens = line.split(HeaderConst.HEADER_DELIMITER);
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
			path = pathSplit[0];
			endPoint = path.substring(path.indexOf("/"));
			parseParameter(pathSplit[1]);
		}
		version = HttpVersion.versionOf(arguments[2]);
	}

	public String getCookieValue(final String cookieName) throws NoSuchElementException {
		return header.getCookieValue(cookieName);
	}

	private void parseParameter(String path) {
		for (String line : path.split("\\&")) {
			if (line.contains("=") && !line.endsWith("=")) {
				String[] values = line.split("=");
				httpParameter.put(values[KEY], URLDecoder.decode(values[VALUE]));
			}
		}
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
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
