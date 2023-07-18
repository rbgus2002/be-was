package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.AnnotationMap;
import annotations.GetMapping;
import controllers.Controller;

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((line = reader.readLine()) != null) {
				logger.debug(line);
				try {
					if (line.startsWith("GET")) {
						handleGetRequest(line, out);
						return;
					}
				} catch (ReflectiveOperationException exception) {
					logger.warn("ReflectiveOperationException");
					logger.warn(exception.getMessage());
				}
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void handleGetRequest(String line, OutputStream out) throws ReflectiveOperationException, IOException {
		String[] arguments;
		if (line.startsWith("GET")) {
			arguments = line.split(" ");
			if (AnnotationMap.exists(MethodType.GET, arguments[1])) {
				String path = AnnotationMap.run(MethodType.GET, arguments[1]);
				sendResourceResponse(path, out);
				return;
			}
			sendResourceResponse(arguments[1], out);
		}
	}

	private void sendResourceResponse(String fileName, OutputStream out) throws IOException {
		Path templatePath = new File("src/main/resources/templates/" + fileName).toPath();
		Path staticPath = new File("src/main/resources/static" + fileName).toPath();
		if (Files.exists(templatePath)) {
			responseForPath(out, templatePath);
			return;
		}
		responseForPath(out, staticPath);
	}

	private void responseForPath(OutputStream out, Path path) throws IOException {
		DataOutputStream dos = new DataOutputStream(out);
		byte[] body = Files.readAllBytes(path);
		String mimeType = Files.probeContentType(path);
		response200Header(dos, body.length, mimeType);
		responseBody(dos, body);
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String mimeType) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: " + mimeType + ";charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
