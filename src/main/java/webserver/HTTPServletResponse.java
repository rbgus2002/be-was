package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Path;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static util.Path.*;
import static webserver.Mime.*;

public class HTTPServletResponse {
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletResponse.class);
    private final DataOutputStream dos;

    private final byte[] body;

    public HTTPServletResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void forward(HTTPServletRequest request) throws IOException {
        String url = request.getUrl();
        String method = request.getMethod();
        String version = request.getVersion();
        logger.debug("url = {}, method = {}, version = {}", url, method, version);
        String extension = url.substring(url.lastIndexOf("."), url.length());
        byte[] body = getBody(extension, url);
        response200Header(body.length, findByExtension(extension), version);
        responseBody(dos, body);
    }

    public void redirect() {
        response302Header();
    }


    private void response200Header(int lengthOfBodyContent, Mime mime, String version) {
        try {
            dos.writeBytes(version + " 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mime.getMimeType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header() {
        try {
            dos.writeBytes("HTTP/1.1 302 \r\n");
            dos.writeBytes("Location: " + HOME_PATH.getPath() + "\r\n");
            dos.writeBytes("Content-Length: 0" + "\r\n");
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

    private byte[] getBody(String extension, String url) throws IOException {
        if (extension.equals(HTML.getExtension())) {
            return Files.readAllBytes(new File(TEMPLATE_PATH.getPath() + url).toPath());
        }
        return Files.readAllBytes(new File(STATIC_PATH.getPath() + url).toPath());
    }
}
