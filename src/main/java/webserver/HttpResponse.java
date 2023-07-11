package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {


    final String DEFAULT_URL = "./src/main/resources/templates";
    private final DataOutputStream dos;
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String contextType = "text/plain";

    public HttpResponse() {
        logger.info("HttpServletResponse Create");
        this.dos = null;
    }
    public HttpResponse(DataOutputStream dos) {
        logger.info("HttpServletResponse Create with dos");
        this.dos = dos;
    }

    public void sendRedirect(String redirectUrl) {

        logger.info("HttpResponse redirect");
        //response302Header();
        //responseBody();
    }

    private byte[] getContent(String url) {
        byte[] body;
        if(url.endsWith(".html")) {
            contextType = "text/html";
        } else if(url.endsWith(".css")) {
            contextType = "text/css";
        } else if(url.endsWith(".text/javascript")) {
            contextType = "text/javascript";
        }

        try {
            body = Files.readAllBytes(new File(DEFAULT_URL + url).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body;

    }

    public void forward(String url) {
        byte[] body = getContent(url);
        logger.info("HttpResponse forward");
        response200Header(body.length);
        responseBody(body);
    }

    private void response200Header(int bodyOfLength) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + bodyOfLength + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
