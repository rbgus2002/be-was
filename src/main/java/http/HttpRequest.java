package http;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.statusline.HttpMethod;
import http.statusline.HttpVersion;

public class HttpRequest {
	private Logger logger = LoggerFactory.getLogger(HttpRequest.class);
	private HttpMethod method;
	private String path;
	private String endPoint;
	private HttpVersion version;
	private String body;
	private Parameter parameter = new Parameter();

	public boolean isGet() {
		return method.equals(HttpMethod.GET);
	}

	public HttpRequest(BufferedReader reader) throws IOException {
		parseRequest(reader);
	}

	private void parseRequest(BufferedReader reader) throws IOException {
		parseStatusLine(reader);
		parseHost(reader);
		parseConnection(reader);
		parseAccept(reader);
		parseUserAgent(reader);
		parseAcceptEndcoding(reader);
		parseAcceptLanguage(reader);
		parseBody(reader);
	}

	private void parseBody(BufferedReader reader) throws IOException {
		body = reader.readLine();
	}

	private void parseAcceptLanguage(BufferedReader reader) throws IOException {
		reader.readLine();
	}

	private void parseAcceptEndcoding(BufferedReader reader) throws IOException {
		reader.readLine();
	}

	private void parseUserAgent(BufferedReader reader) throws IOException {
		reader.readLine();
	}

	private void parseAccept(BufferedReader reader) throws IOException {
		reader.readLine();
	}

	private void parseConnection(BufferedReader reader) throws IOException {
		reader.readLine();
	}

	private void parseHost(BufferedReader reader) throws IOException {
		reader.readLine();
	}

	private void parseStatusLine(BufferedReader reader) throws IOException {
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
				parameter.put(values[0], values[1]);
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

	public Parameter getParameter() {
		return parameter;
	}
}
