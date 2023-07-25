package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;
import webserver.http.Cookie;
import webserver.http.HttpHeaders;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final HttpStatusLine httpStatusLine;
    private final HttpHeaders httpHeaders;
    private final byte[] body;

    private HttpResponse(HttpStatusLine httpStatusLine, HttpHeaders httpHeaders, byte[] body) {
        this.httpStatusLine = httpStatusLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public void responseStatic(DataOutputStream dos) {
        response200Header(dos);
        responseBody(dos);
    }

    public void responseDynamic(DataOutputStream dos) {
        response302Header(dos);
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + httpHeaders.getContentType() +  "\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: 0" + "\r\n");
            dos.writeBytes("Location: " + httpHeaders.getLocation() + "\r\n");
            dos.writeBytes("Set-Cookie: " + httpHeaders.getCookie() + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static HttpResponse createStatic(String requestUri) throws IOException {
        byte[] body = Files.readAllBytes(new File(Parser.parsePath(requestUri)).toPath());
        return new HttpResponse(
                HttpStatusLine.createStaticStatusLine(),
                HttpHeaders.createStaticStatusHeaders(body.length, requestUri),
                body
        );
    }

    public static HttpResponse createRedirect(String viewPath, Cookie cookie) {
        byte[] emptyBody = new byte[0];
        return new HttpResponse(
                HttpStatusLine.createRedirectStatusLine(),
                HttpHeaders.createRedirectStatusHeaders(viewPath, cookie),
                emptyBody
        );
    }

}
