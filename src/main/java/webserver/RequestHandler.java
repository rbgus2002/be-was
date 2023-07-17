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

public class RequestHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	RequestHandler() {
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
			final Method[] declaredMethods = RequestHandler.class.getDeclaredMethods();
			Constructor<RequestHandler> constructor = RequestHandler.class.getDeclaredConstructor();
			Object o = constructor.newInstance();
			List<Method> mappedMethods = Arrays.stream(declaredMethods)
				.filter(method -> method.isAnnotationPresent(GetMapping.class) && method.getAnnotation(GetMapping.class)
					.value()
					.equals(arguments[1]))
				.collect(Collectors.toList());
			if (mappedMethods.isEmpty()) {
				sendStaticResponse(arguments[1], out);
				return;
			}
			for (Method mappedMethod : mappedMethods) {
				sendTemplateResponse((String)mappedMethod.invoke(o), out);
			}
		}
	}

	private void sendTemplateResponse(String fileName, OutputStream out) throws IOException {
		DataOutputStream dos = new DataOutputStream(out);
		Path path = new File("src/main/resources/templates/" + fileName).toPath();
		byte[] body = Files.readAllBytes(path);
		String mimeType = Files.probeContentType(path);
		response200Header(dos, body.length, mimeType);
		responseBody(dos, body);
	}

	private void sendStaticResponse(String fileName, OutputStream out) throws IOException {
		DataOutputStream dos = new DataOutputStream(out);
		Path path = new File("src/main/resources/static" + fileName).toPath();
		byte[] body = Files.readAllBytes(path);
		String mimeType = Files.probeContentType(path);
		response200Header(dos, body.length, mimeType);
		responseBody(dos, body);
	}

	@GetMapping("/")
	private String getFile() {
		return "index.html";
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
