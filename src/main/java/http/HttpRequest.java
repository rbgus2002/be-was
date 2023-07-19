package http;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;

public class HttpRequest {
	private HttpMethod method;
	private String path;
	private HttpVersion version;
	private String body;

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
		version = HttpVersion.versionOf(arguments[2]);
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
}
