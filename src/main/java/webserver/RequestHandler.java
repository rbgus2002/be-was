package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import handler.ControllerMappingHandler;
import http.HttpResponse;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static http.HttpMethod.POST;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;
    private final ControllerMappingHandler controllerMappingHandler = ControllerMappingHandler.getInstance();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // 사용자 요청에 대한 처리 구현
            HttpRequest httpRequest = createRequest(in);

            assert httpRequest != null;
            Controller controller = controllerMappingHandler.mappingController(httpRequest);
            HttpResponse.ResponseBuilder responseBuilder = controller.loadFileByRequest(httpRequest);

            HttpResponse httpResponse = responseBuilder.build();
            httpResponse.send(new DataOutputStream(out));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest createRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            logger.debug(line);
            String[] requestLine = line.split(" ");

            Map<String, String> headers = new HashMap<>();
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                logger.debug(line);
                String[] headerParts = line.split(": ");
                headers.put(headerParts[0], headerParts[1]);
            }

            String body = "";
            if (POST.equals(requestLine[0]) && headers.containsKey("Content-Length")) {
                int contentLength = Integer.parseInt(headers.get("Content-Length"));
                char[] bodyChars = new char[contentLength];
                br.read(bodyChars, 0, contentLength);
                body = new String(bodyChars);
            }
            logger.debug(body);

            return new HttpRequest.RequestBuilder(requestLine[0], requestLine[1], requestLine[2])
                    .setHeader(headers)
                    .setBody(body)
                    .build();
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
        return null;
    }
}
