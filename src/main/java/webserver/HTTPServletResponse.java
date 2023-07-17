package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Path;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static util.Path.*;

public class HTTPServletResponse {
    private static final Logger logger = LoggerFactory.getLogger(HTTPServletResponse.class);
    private final DataOutputStream dos;


    public HTTPServletResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void forward(String uri) throws IOException {
        String extension = uri.substring(uri.lastIndexOf("."), uri.length());
        byte[] body = getBody(extension, uri);
        response200Header(body.length, Mime.findByExtension(extension));
        responseBody(dos, body);
    }

    public void redirect() {
        response302Header();
    }


    private void response200Header(int lengthOfBodyContent, Mime mime) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
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

    private byte[] getBody(String extension, String uri) throws IOException {
        if (extension.equals(".html")) {
            return Files.readAllBytes(new File(TEMPLATE_PATH.getPath() + uri).toPath());
        }
        return Files.readAllBytes(new File(STATIC_PATH.getPath()+ uri).toPath());
    }
}
