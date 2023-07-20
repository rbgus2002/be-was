package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";

    private String path;
    private Map<String, String> headers;
    private byte[] body;
    private HttpStatusCode statusCode;

    public HttpResponse(String path, HttpStatusCode statusCode) throws IOException {
        this.path = path;
        this.statusCode = statusCode;
        this.body = Files.readAllBytes(new File(TEMPLATE_PATH + path).toPath());
    }

    public static HttpResponse ok(String path) throws IOException {
        return new HttpResponse(path, HttpStatusCode.OK);
    }

    public static HttpResponse redirect(String path) throws IOException {
        return new HttpResponse(path, HttpStatusCode.FOUND);
    }

    public void response(DataOutputStream dos) {
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
