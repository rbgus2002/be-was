package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

import static util.FileUtils.getResourceAsStream;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final byte[] body;

    public HttpResponse(String path) throws FileNotFoundException {
        InputStream fileInputStream = getResourceAsStream(path);

        try {
            this.body = fileInputStream.readAllBytes();
        } catch (Exception e) {
            throw new FileNotFoundException("파일을 찾을 수 없습니다.");
        }
    }

    public void response(Socket connection) {
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos);
            responseBody(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public byte[] getBody() {
        return this.body.clone();
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + this.body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            dos.write(this.body, 0, this.body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
