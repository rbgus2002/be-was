package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utils.StringUtils.NEW_LINE;
import static webserver.request.HttpRequestParser.parseRequest;

public class RequestHandler implements Runnable {
    public final String RESOURCE_PATH = "src/main/resources/templates";
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
//
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()
        ) {
            HttpRequest httpRequest = parseRequest(in);
            printLog(httpRequest);

            // 수행 로직 구현해서 분리
            HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, Files.readAllBytes(Paths.get(RESOURCE_PATH + httpRequest.getUrl().getPath())));

            response(out, httpResponse);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private void printLog(HttpRequest httpRequest) {
        logger.debug(httpRequest.getMethod());
        logger.debug(httpRequest.getVersion());
        logger.debug(httpRequest.getUrl().toString());
        logger.debug(httpRequest.getBody());
    }

    private void response(OutputStream out, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(httpResponse.getResponseHeader());
        dos.writeBytes(NEW_LINE);
        dos.write(httpResponse.getResponseBody());
        dos.flush();
    }
}
