package http;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.header.Header;
import http.statusline.HttpMethod;
import http.statusline.HttpVersion;

public class HttpRequest {
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

	public boolean isGet() {
		return method.equals(HttpMethod.GET);
	}

	private void parseRequest() throws IOException {
		parseStatusLine();
		parseHeader();
		parseBody();
	}

	private void parseHeader() throws IOException {
		String line = "";
		String[] tokens;
		while (!line.isBlank()) {
			line = reader.readLine();
			if (line.contains(": ")) {
				tokens = line.split(": ");
				header.addHeader(tokens[0], tokens[1]);
			}
		}
	}

	private void parseBody() throws IOException {
		body = reader.readLine();
	}

	private void parseStatusLine() throws IOException {
		String statusLine = reader.readLine();
		String[] arguments = statusLine.split(" ");
		method = HttpMethod.typeOf(arguments[0]);
		path = arguments[1];
		endPoint = path;
		logger.debug("[Whole Path] : {}", path);
		if (path.contains("?")) {
			String[] pathSplit = path.split("\\?");
			String parameterLine = pathSplit[1].substring(0, pathSplit[1].indexOf("."));
			path = pathSplit[0];
			endPoint = path.substring(path.lastIndexOf("/"));
			logger.debug("[Clear Path] : {}", path);
			logger.debug("[End Point] : {}", endPoint);
			logger.debug("[Params] : {}", parameterLine);
			parseParameter(parameterLine);
		}
		version = HttpVersion.versionOf(arguments[2]);
	}

	private void parseParameter(String path) {
		for (String line : path.split("\\&")) {
			if (line.contains("=")) {
				logger.debug(line);
				String[] values = line.split("=");
				httpParameter.put(values[0], values[1]);
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
