package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static util.Parser.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";

    private String path;
    private MIME mime;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;
    private HttpStatusCode statusCode;

    public HttpResponse() {
    }

    public void ok(String path) throws IOException {
        setResponse(path, HttpStatusCode.OK);
    }

    public void redirect(String path) throws IOException {
        setResponse(path, HttpStatusCode.FOUND);
    }

    // TODO: 적절한 이름으로 바꾸기
    private void setResponse(String path, HttpStatusCode statusCode) throws IOException {
        this.path = path;
        this.statusCode = statusCode;
        this.mime = convertExtensionToMime(getExtension(path));

        if (mime == MIME.HTML) {
            this.body = Files.readAllBytes(new File(TEMPLATE_PATH + path).toPath());
        } else {
            this.body = Files.readAllBytes(new File(STATIC_PATH + path).toPath());
        }
    }

    public void response(DataOutputStream dos) {
        if (this.statusCode == HttpStatusCode.OK) {
            response200Header(dos, body.length);
        } else if (this.statusCode == HttpStatusCode.FOUND) {
            response302Header(dos, path);
        }
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mime.getContentType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            writeHeaders(dos);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders(DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            dos.writeBytes(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            writeHeaders(dos);
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

    public void setCookie(String sessionId) {
        this.headers.put("Set-Cookie", "sid=" + sessionId + "; Path=/");
    }
}