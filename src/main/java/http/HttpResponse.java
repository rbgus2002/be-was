package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

import static util.FileUtils.getExtension;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final String path;
    private final int httpStatus;
    private final Mime contentType;

    public static HttpResponse ok(String path, Mime mime) {
        return new HttpResponse(path, mime, 200);
    }

    public static HttpResponse redirect(String path) {
        return new HttpResponse(path, null, 302);
    }

    private HttpResponse(String path, Mime mime, int httpStatus) {
        this.path = path;
        this.contentType = mime;
        this.httpStatus = httpStatus;
    }

    public void response(Socket connection) {
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            if (this.httpStatus == 200) {
                // 정적 파일을 읽는데 문제가 발생하면 /error.html 반환
                response200(dos);
            }
            if (this.httpStatus == 302) {
                response302Header(dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200(DataOutputStream dos) throws IOException {
        byte[] body;
        try {
            InputStream fileInputStream = getResourceAsStream(this.path);
            body = fileInputStream.readAllBytes();

        } catch (IOException e) {
            InputStream fileInputStream = getResourceAsStream("/error.html");
            body = fileInputStream.readAllBytes();
        }
        response200Header(dos, body);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + this.contentType.getType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: " + path + "\r\n");
            dos.writeBytes("Cache-Control: no-cache, no-store, must-revalidate\r\n");
            dos.writeBytes("Pragma: no-cache\r\n");
            dos.writeBytes("Expires: 0\r\n");
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

    private InputStream getResourceAsStream(String path) throws FileNotFoundException {
        String ext = getExtension(path);
        InputStream fileInputStream = FileUtils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates" : "/static") + path);
        if (fileInputStream == null) {
            throw new FileNotFoundException(path + "에 파일이 존재하지 않습니다.");
        }
        return fileInputStream;
    }
}
