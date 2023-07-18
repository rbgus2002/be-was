package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import http.HttpResponse;
import http.HttpRequest;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    private final Controller controller = new Controller();

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
            HttpResponse.ResponseBuilder responseBuilder = controller.loadFileByRequest(httpRequest);

            HttpResponse httpResponse = responseBuilder.build(new DataOutputStream(out));
            httpResponse.send();
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

            Map<String, String> header = new HashMap<>();
            line = br.readLine();
            while (!line.equals("")) {
                logger.debug(line);
                String[] requestHeader = line.split(": ");
                header.put(requestHeader[0], requestHeader[1]);
                line = br.readLine();
            }

            return new HttpRequest.RequestBuilder(requestLine[0], requestLine[1], requestLine[2])
                    .setHeader(header)
                    .build();

            // TODO setBody()
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
        return null;
    }
}
